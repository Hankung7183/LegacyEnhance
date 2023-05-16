package me.hankung.legacyenhance.mixin.oldanimation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    private MinecraftClient client;

    @Unique
    private float partialTicks;

    @Inject(method = "transformCamera", at = @At(value = "HEAD"))
    public void legacy$transformCamera(float partialTicks, CallbackInfo ci) {
        this.partialTicks = partialTicks;
    }

    @ModifyVariable(method = "transformCamera", at = @At(value = "STORE", ordinal = 0), ordinal = 1)
    public float legacy$modifyEyeHeight_transformCamera(float eyeHeight) {
        if (this.client.getCameraEntity() != this.client.player) {
            return eyeHeight;
        }
        return LegacyEnhance.oldAnimation.sneakHandler.getEyeHeight(this.partialTicks);
    }

    @Redirect(method = "renderDebugCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
    public float legacy$modifyEyeHeight_renderDebugCrosshair(Entity entity) {
        if (this.client.getCameraEntity() != this.client.player) {
            return entity.getEyeHeight();
        }
        return LegacyEnhance.oldAnimation.sneakHandler.getEyeHeight(this.partialTicks);
    }

}
