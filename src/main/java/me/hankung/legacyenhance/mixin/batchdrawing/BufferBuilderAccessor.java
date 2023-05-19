package me.hankung.legacyenhance.mixin.batchdrawing;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.render.BufferBuilder;

@Mixin(BufferBuilder.class)
public interface BufferBuilderAccessor {
    @Accessor("building")
    boolean isDrawing();
}
