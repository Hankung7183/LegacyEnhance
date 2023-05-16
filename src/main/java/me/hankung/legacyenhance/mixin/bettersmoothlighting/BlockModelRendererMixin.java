package me.hankung.legacyenhance.mixin.bettersmoothlighting;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.Block;

@Mixin(targets = "net.minecraft.client.render.block.BlockModelRenderer$AmbientOcclusionCalculator")
public class BlockModelRendererMixin {
    @Redirect(
        method = "apply(Lnet/minecraft/world/BlockView;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;[FLjava/util/BitSet;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;isTransluscent()Z")
    )
    private boolean legacy$betterSmoothLighting(Block block) {
        return !block.isLeafBlock() || block.getOpacity() == 0;
    }
}
