package me.hankung.legacyenhance.mixin.skiptwitch;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.GameRenderer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "renderStreamIndicator", at = @At("HEAD"), cancellable = true)
    private void legacy$cancelStreamIndicator(CallbackInfo ci) {
        ci.cancel();
    }
}
