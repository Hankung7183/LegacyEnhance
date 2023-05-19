package me.hankung.legacyenhance.mixin.limitscan;

import java.util.Queue;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import me.hankung.legacyenhance.utils.limitscan.IChunkOcclusionDataBuilder;
import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.util.math.Direction;

@Mixin(ChunkOcclusionDataBuilder.class)
public class ChunkOcclusionDataBuilderMixin implements IChunkOcclusionDataBuilder {

    @Unique
    private boolean legacy$limitScan;

    @Inject(method = "getOpenFaces(I)Ljava/util/Set;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/chunk/ChunkOcclusionDataBuilder;addEdgeFaces(ILjava/util/Set;)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void legacy$checkLimitScan(int enumfacing, CallbackInfoReturnable<Set<Direction>> cir,
            Set<Direction> set, Queue<Integer> queue, int i) {
        if (this.legacy$limitScan && set.size() > 1) {
            cir.setReturnValue(set);
        }
    }

    @Override
    public void legacy$setLimitScan(boolean limitScan) {
        this.legacy$limitScan = limitScan;
    }

}
