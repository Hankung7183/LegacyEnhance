package me.hankung.legacyenhance.mixin.depthfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

import net.minecraft.client.render.entity.feature.CreeperLightningFeatureRenderer;

@Mixin(CreeperLightningFeatureRenderer.class)
public class CreeperLightningFeatureRendererMixin {
    @ModifyArg(method = "render(Lnet/minecraft/entity/mob/CreeperEntity;FFFFFFF)V", slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/CreeperEntityModel;render(Lnet/minecraft/entity/Entity;FFFFFF)V")), at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;depthMask(Z)V"))
    private boolean legacy$fixDepth(boolean original) {
        return true;
    }
}
