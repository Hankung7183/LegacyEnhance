package me.hankung.legacyenhance.mixin.oldanimation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.util.math.Box;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Redirect(method = "renderHitbox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;drawBox(Lnet/minecraft/util/math/Box;IIII)V", ordinal = 1))
    private void legacy$cancelRenderGlobalCall(Box boundingBox, int red, int green, int blue, int alpha) {
        if (!LegacyEnhance.CONFIG.oldanimateOldDebugHitbox.get()) {
            WorldRenderer.drawBox(boundingBox, red, green, blue, alpha);
        }
    }

    @Redirect(method = "renderHitbox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Tessellator;draw()V"))
    private void legacy$cancelTessellatorDraw(Tessellator tessellator) {
        if (!LegacyEnhance.CONFIG.oldanimateOldDebugHitbox.get()) {
            tessellator.draw();
        } else {
            tessellator.getBuffer().end();
        }
    }

}
