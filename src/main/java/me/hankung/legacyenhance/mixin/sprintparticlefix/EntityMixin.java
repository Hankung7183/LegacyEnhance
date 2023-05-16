package me.hankung.legacyenhance.mixin.sprintparticlefix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public class EntityMixin {

    @Shadow public boolean onGround;

    @Inject(method = "attemptSprintingParticles", at = @At("HEAD"), cancellable = true)
    private void legacy$checkGroundState(CallbackInfo ci) {
        if (!this.onGround) ci.cancel();
    }

}
