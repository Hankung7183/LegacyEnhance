package me.hankung.legacyenhance.mixin.resolvecrash;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "tickStatusEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectInstance;method_6093(Lnet/minecraft/entity/LivingEntity;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void legacy$checkPotionEffect(CallbackInfo ci,
            Iterator<Integer> iterator, Integer integer,
            StatusEffectInstance potioneffect) {
        if (potioneffect == null) {
            ci.cancel();
        }
    }
}
