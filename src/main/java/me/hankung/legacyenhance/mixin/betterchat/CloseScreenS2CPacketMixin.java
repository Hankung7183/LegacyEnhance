package me.hankung.legacyenhance.mixin.betterchat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;

@Mixin(CloseScreenS2CPacket.class)
public class CloseScreenS2CPacketMixin {
    @Inject(method = "apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V", at = @At("HEAD"), cancellable = true)
    private void noCloseMyChat(ClientPlayPacketListener handler, CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen) {
            ci.cancel();
        }
    }
}
