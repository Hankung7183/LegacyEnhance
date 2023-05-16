package me.hankung.legacyenhance.mixin.depthfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.render.entity.feature.WitherArmorFeatureRenderer;

@Mixin(WitherArmorFeatureRenderer.class)
public class WitherArmorFeatureRendererMixin {
    @Inject(method = "render(Lnet/minecraft/entity/boss/WitherEntity;FFFFFFF)V", at = @At("TAIL"))
    private void legacy$fixDepth(CallbackInfo ci) {
        GlStateManager.depthMask(true);
    }
}
