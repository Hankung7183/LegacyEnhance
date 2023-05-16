package me.hankung.legacyenhance.mixin.oldanimation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.util.math.Vec3d;

@Mixin(FishingBobberEntityRenderer.class)
public class FishingBobberEntityRendererMixin {

    @Redirect(method = "render", at = @At(value = "NEW", target = "net/minecraft/util/math/Vec3d", ordinal = 0))
    private Vec3d legacy$oldFishingLine(double x, double y, double z) {
        return !LegacyEnhance.CONFIG.oldanimateOldRod.get() ? new Vec3d(x, y, z) : legacy$getOffset();
    }

    private Vec3d legacy$getOffset() {
        double fov = MinecraftClient.getInstance().options.fov;
        double decimalFov = fov / 110.0;
        return new Vec3d(-decimalFov + decimalFov / 2.5 - decimalFov / 8.0 + 0.16, 0.0, 0.4);
    }

}
