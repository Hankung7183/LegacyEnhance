package me.hankung.legacyenhance.mixin.optifinefix;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.block.BlockState;
import net.minecraft.client.world.ChunkRenderCache;
import net.minecraft.util.math.BlockPos;

@Mixin(ChunkRenderCache.class)
public class ChunkRenderCacheMixin {

    @Shadow
    @Final
    private static BlockState DEFAULT_STATE;

    @Shadow
    private BlockState[] field_10664;

    @Inject(method = "getBlockState", at = @At(value = "FIELD", target = "Lnet/minecraft/client/world/ChunkRenderCache;field_10664:[Lnet/minecraft/block/BlockState;", ordinal = 0, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void legacy$connectedTexturesBoundsCheck(BlockPos pos, CallbackInfoReturnable<BlockState> cir,
            int positionIndex) {
        if (positionIndex < 0 || positionIndex >= this.field_10664.length) {
            cir.setReturnValue(DEFAULT_STATE);
        }
    }

}
