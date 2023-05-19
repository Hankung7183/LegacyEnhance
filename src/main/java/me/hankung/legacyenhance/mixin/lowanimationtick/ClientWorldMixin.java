package me.hankung.legacyenhance.mixin.lowanimationtick;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.world.ClientWorld;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @ModifyConstant(method = "spawnRandomParticles", constant = @Constant(intValue = 1000))
    private int legacy$lowerTickCount(int original) {
        return LegacyEnhance.CONFIG.performanceLowAnimationTick.get() ? 100 : original;
    }
}
