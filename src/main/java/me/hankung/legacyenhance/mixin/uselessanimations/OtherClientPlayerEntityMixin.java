package me.hankung.legacyenhance.mixin.uselessanimations;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.OtherClientPlayerEntity;

@Mixin(OtherClientPlayerEntity.class)
public class OtherClientPlayerEntityMixin {
    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/OtherClientPlayerEntity;tickHandSwing()V", shift = At.Shift.AFTER), cancellable = true)
    private void legacy$removeUselessAnimations(CallbackInfo ci) {
        ci.cancel();
    }
}
