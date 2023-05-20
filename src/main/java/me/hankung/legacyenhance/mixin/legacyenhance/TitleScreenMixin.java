package me.hankung.legacyenhance.mixin.legacyenhance;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.client.gui.screen.TitleScreen;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    // https://github.com/Legacy-Fabric/legacy-fixes/blob/master/src/main/java/net/legacyfabric/legacyfixes/mixin/TitleScreenMixin.java
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"))
    private String fixFabricBranding(String text) {
        if (text.contains("Minecraft")) {
            text = text + "/Fabric";
        }
        return text;
    }
}
