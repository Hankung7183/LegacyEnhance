package me.hankung.legacyenhance.mixin.cleanview;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hankung.legacyenhance.LegacyEnhance;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "tickStatusEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/client/particle/ParticleType;DDDDDD[I)V"), cancellable = true)
    private void legacy$cleanView(CallbackInfo ci) {
        if (LegacyEnhance.CONFIG.generalCleanView.get() && (Object) this == MinecraftClient.getInstance().player) {
            ci.cancel();
        }
    }
}
