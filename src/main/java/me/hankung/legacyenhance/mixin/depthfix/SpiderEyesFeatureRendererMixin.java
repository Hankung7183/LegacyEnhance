package me.hankung.legacyenhance.mixin.depthfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.render.entity.feature.SpiderEyesFeatureRenderer;

@Mixin(SpiderEyesFeatureRenderer.class)
public class SpiderEyesFeatureRendererMixin {
    @Inject(method = "render(Lnet/minecraft/entity/mob/SpiderEntity;FFFFFFF)V", at = @At("TAIL"))
    private void legacy$fixDepth(CallbackInfo ci) {
        GlStateManager.depthMask(true);
    }
}
