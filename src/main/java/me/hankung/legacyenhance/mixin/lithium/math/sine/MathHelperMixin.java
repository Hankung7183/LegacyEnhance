package me.hankung.legacyenhance.mixin.lithium.math.sine;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import me.hankung.legacyenhance.utils.lithium.math.CompactSineLUT;
import net.minecraft.util.math.MathHelper;

@Mixin(value = MathHelper.class, priority = 1500)
public abstract class MathHelperMixin {
    
    /**
     * @author jellysquid3
     * @reason use an optimized implementation
     */
    @Overwrite
    public static float sin(float f) {
        return CompactSineLUT.sin(f);
    }

    /**
     * @author jellysquid3
     * @reason use an optimized implementation
     */
    @Overwrite
    public static float cos(float f) {
        return CompactSineLUT.cos(f);
    }

}
