package me.hankung.legacyenhance.mixin.optimization;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;

@Mixin(Chunk.class)
public class ChunkMixin {

    @ModifyArg(method = "getBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;method_3917(III)V", ordinal = 0), index = 1)
    private int legacy$subtractOneFromY(int y) {
        return y - 1;
    }

    /**
     * @author LlamaLad7
     * @reason Optimization
     */
    @Overwrite
    public BlockState method_9154(BlockPos pos) {
        return getBlockState((Chunk) (Object) this, pos);
    }

    private BlockState getBlockState(Chunk chunk, BlockPos pos) {
        final int y = pos.getY();

        if (y >= 0 && y >> 4 < chunk.getBlockStorage().length) {
            final ChunkSection storage = chunk.getBlockStorage()[y >> 4];
            if (storage != null)
                return storage.getBlockState(pos.getX() & 15, y & 15, pos.getZ() & 15);
        }

        return Blocks.AIR.getDefaultState();
    }

}
