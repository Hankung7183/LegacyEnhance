package me.hankung.legacyenhance.mixin.brightnessfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(Entity.class)
public class EntityMixin {
    @Redirect(method = "getLightmapCoordinates", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;blockExists(Lnet/minecraft/util/math/BlockPos;)Z"))
    public boolean legacy$alwaysReturnTrue(World world, BlockPos pos) {
        return true;
    }
}
