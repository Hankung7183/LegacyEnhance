package me.hankung.legacyenhance.mixin.oldanimation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;

@Mixin(ArmorFeatureRenderer.class)
public class ArmorFeatureRendererMixin {
    @Inject(method = "combineTextures", at = @At("HEAD"), cancellable = true)
    private void legacy$applyRedArmor(CallbackInfoReturnable<Boolean> cir) {
        if (LegacyEnhance.CONFIG.oldanimateRedArmor.get())
            cir.setReturnValue(true);
    }
}
