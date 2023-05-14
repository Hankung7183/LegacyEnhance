package me.hankung.legacyenhance.mixin.fastworldswapping;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.render.LoadingScreenRenderer;

@Mixin(LoadingScreenRenderer.class)
public class LoadingScreenRendererMixin {
    @Inject(method = "setProgressPercentage", at = @At("HEAD"), cancellable = true)
    private void legacy$skipProgress(int progress, CallbackInfo ci) {
        if (progress < 0 || LegacyEnhance.CONFIG.performanceFastWorldSwapping.get()) {
            ci.cancel();
        }
    }
}
