package me.hankung.legacyenhance.mixin.hitdelayfix;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hankung.legacyenhance.LegacyEnhance;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    private int attackCooldown;

    @Inject(method = "doAttack", at = @At("HEAD"))
    private void legacy$doAttackAfter(final CallbackInfo ci) {
        if (LegacyEnhance.CONFIG.generalHitDelayFix.get()) {
            attackCooldown = 0;
        }
    }
    
}
