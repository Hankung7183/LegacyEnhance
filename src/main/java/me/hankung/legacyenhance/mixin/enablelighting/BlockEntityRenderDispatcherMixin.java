package me.hankung.legacyenhance.mixin.enablelighting;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {
    @Inject(method = "renderEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getLight(Lnet/minecraft/util/math/BlockPos;I)I"))
    private void legacy$enableLighting(CallbackInfo ci) {
        DiffuseLighting.enableNormally();
    }
}
