package me.hankung.legacyenhance.mixin.mousebindfix;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {
    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void legacy$checkCloseClick(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mouseButton - 100 == mc.options.inventoryKey.getCode()) {
            mc.player.closeScreen();
            ci.cancel();
        }
    }
}
