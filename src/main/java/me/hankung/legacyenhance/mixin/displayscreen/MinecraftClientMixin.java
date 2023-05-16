package me.hankung.legacyenhance.mixin.displayscreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import javafx.stage.Screen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ProgressScreen;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @ModifyArg(method = "startIntegratedServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V", ordinal = 1))
    private ProgressScreen legacy$displayWorkingScreen(Screen original) {
        return new ProgressScreen();
    }
}
