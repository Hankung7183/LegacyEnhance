package me.hankung.legacyenhance.mixin.fastworldswapping;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Redirect(
        method = "connect(Lnet/minecraft/client/world/ClientWorld;Ljava/lang/String;)V",
        at = @At(value = "INVOKE", target = "Ljava/lang/System;gc()V")
    )
    private void legacy$fastWorldSwapping() {
        if (!LegacyEnhance.CONFIG.performanceFastWorldSwapping.get()) {
            System.gc();
        }
    }
}
