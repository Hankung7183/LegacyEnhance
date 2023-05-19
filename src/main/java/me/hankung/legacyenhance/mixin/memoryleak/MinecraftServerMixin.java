package me.hankung.legacyenhance.mixin.memoryleak;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @ModifyVariable(method = "setServerMeta", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/ServerMetadata;setFavicon(Ljava/lang/String;)V", shift = At.Shift.AFTER), ordinal = 1)
    private ByteBuf legacy$releaseByteBuf(ByteBuf buf1) {
        buf1.release();
        return buf1;
    }
}
