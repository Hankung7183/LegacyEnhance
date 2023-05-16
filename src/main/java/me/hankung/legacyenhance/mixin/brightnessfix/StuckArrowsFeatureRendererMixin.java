package me.hankung.legacyenhance.mixin.brightnessfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.render.entity.feature.StuckArrowsFeatureRenderer;

@Mixin(StuckArrowsFeatureRenderer.class)
public class StuckArrowsFeatureRendererMixin {
    
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/DiffuseLighting;disable()V"))
    private void legacy$removeDisable() {
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/DiffuseLighting;enableNormally()V"))
    private void legacy$removeEnable() {
    }

}
