package me.hankung.legacyenhance.mixin.bettercamera;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Redirect(method = "transformCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;rayTrace(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/hit/BlockHitResult;"))
    private BlockHitResult legacy$changeBlockingType(ClientWorld instance, Vec3d from, Vec3d to) {
        return LegacyEnhance.CONFIG.generalBetterCam.get()
                ? instance.rayTrace(from, to, false, true, true)
                : instance.rayTrace(from, to);
    }
}
