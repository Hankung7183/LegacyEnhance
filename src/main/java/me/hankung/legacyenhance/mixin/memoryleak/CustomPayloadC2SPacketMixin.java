package me.hankung.legacyenhance.mixin.memoryleak;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.PacketByteBuf;

@Mixin(CustomPayloadC2SPacket.class)
public class CustomPayloadC2SPacketMixin {

    @Shadow
    private PacketByteBuf payload;

    @Inject(method = "apply(Lnet/minecraft/network/listener/ServerPlayPacketListener;)V", at = @At("TAIL"))
    private void legacy$releaseData(ServerPlayPacketListener handler, CallbackInfo ci) {
        if (this.payload != null) {
            this.payload.release();
        }
    }

}
