package me.hankung.legacyenhance.mixin.mousedelayfix;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "getRotationVector", at = @At("HEAD"), cancellable = true)
    private void legacy$mouseDelayFix(float partialTicks, CallbackInfoReturnable<Vec3d> cir) {
        if ((LivingEntity) (Object) this instanceof ClientPlayerEntity) {
            cir.setReturnValue(super.getRotationVector(partialTicks));
        }
    }
    
}
