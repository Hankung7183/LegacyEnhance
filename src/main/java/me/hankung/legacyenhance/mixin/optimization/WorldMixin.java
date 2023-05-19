package me.hankung.legacyenhance.mixin.optimization;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

@Mixin(World.class)
public class WorldMixin {

    @Inject(method = "doesBoxCollide", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getEntitiesIn(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Ljava/util/List;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void legacy$filterEntities(Entity entityIn, Box bb,
            CallbackInfoReturnable<List<Box>> cir, List<Box> list) {
        if (entityIn instanceof TntEntity || entityIn instanceof FallingBlockEntity
                || entityIn instanceof ItemEntity
                || entityIn instanceof Particle) {
            cir.setReturnValue(list);
        }
    }

}
