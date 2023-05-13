package me.hankung.legacyenhance.mixin.hitdelayfix;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow private int attackCooldown;

    @Inject(method = "doAttack", at = @At("HEAD"))
    private void doAttackAfter(final CallbackInfo ci) {
        attackCooldown = 0;
    }
}
