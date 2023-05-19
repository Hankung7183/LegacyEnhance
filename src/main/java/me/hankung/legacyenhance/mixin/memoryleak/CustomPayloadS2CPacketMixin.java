package me.hankung.legacyenhance.mixin.memoryleak;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.PacketByteBuf;

@Mixin(CustomPayloadS2CPacket.class)
public class CustomPayloadS2CPacketMixin {

    @Shadow
    private PacketByteBuf payload;

    @Inject(method = "apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V", at = @At("TAIL"))
    private void legacy$releaseData(ClientPlayPacketListener handler, CallbackInfo ci) {
        if (this.payload != null) {
            this.payload.release();
        }
    }

}
