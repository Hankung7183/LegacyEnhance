package me.hankung.legacyenhance.mixin.limitscan;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;

import me.hankung.legacyenhance.utils.limitscan.IChunkOcclusionDataBuilder;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @ModifyVariable(method = "getOpenChunkFaces", name = "visgraph", at = @At(value = "STORE", ordinal = 0))
    private ChunkOcclusionDataBuilder legacy$setLimitScan(ChunkOcclusionDataBuilder visgraph) {
        ((IChunkOcclusionDataBuilder) visgraph).legacy$setLimitScan(true);
        return visgraph;
    }

}
