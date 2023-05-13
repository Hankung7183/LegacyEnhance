package me.hankung.legacyenhance.mixin.betterchat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Redirect(method = "setScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;clear()V"))
    private void keepChatMessages(ChatHud instance) {
    }
}
