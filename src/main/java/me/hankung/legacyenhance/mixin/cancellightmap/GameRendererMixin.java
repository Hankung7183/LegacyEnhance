package me.hankung.legacyenhance.mixin.cancellightmap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.render.GameRenderer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Unique
    private boolean legacy$createdLightmap;

    @Inject(method = "updateLightmap", at = @At("HEAD"), cancellable = true)
    private void legacy$cancelLightmapBuild(CallbackInfo ci) {
        if (LegacyEnhance.CONFIG.generalFullBright.get() && this.legacy$createdLightmap) {
            ci.cancel();
        }
    }

    @Inject(method = "updateLightmap", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;pop()V"))
    private void legacy$setCreatedLightmap(CallbackInfo ci) {
        this.legacy$createdLightmap = true;
    }

}
