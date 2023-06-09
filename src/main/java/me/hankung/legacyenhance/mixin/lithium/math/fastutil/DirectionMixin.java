package me.hankung.legacyenhance.mixin.lithium.math.fastutil;

import java.util.Random;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.util.math.Direction;

@Mixin(Direction.class)
public class DirectionMixin {

    @Shadow
    @Final
    private static Direction[] ALL;

    @Shadow
    @Final
    private int idOpposite;

    /**
     * @reason Avoid the modulo/abs operations
     * @author JellySquid
     */
    @Overwrite
    public Direction getOpposite() {
        return ALL[this.idOpposite];
    }

    /**
     * @reason Do not allocate an excessive number of Direction arrays
     * @author JellySquid
     */
    @Overwrite
    public static Direction random(Random rand) {
        return ALL[rand.nextInt(ALL.length)];
    }

}
