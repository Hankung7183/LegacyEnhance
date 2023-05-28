package me.hankung.legacyenhance.mixin.lithium.math.sine;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.util.math.MathHelper;

@Mixin(MathHelper.class)
public interface MathHelperAccessor {
    @Accessor("SINE_TABLE")
    static float[] getSINE() {
        throw new UnsupportedOperationException("Mixin failed to inject!");
    }
}
