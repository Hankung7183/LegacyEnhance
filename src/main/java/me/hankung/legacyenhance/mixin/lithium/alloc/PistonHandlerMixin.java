package me.hankung.legacyenhance.mixin.lithium.alloc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.piston.PistonHandler;
import net.minecraft.util.math.Direction;

@Mixin(PistonHandler.class)
public class PistonHandlerMixin {

    private static final Direction[] VALUES = Direction.values();

    @Redirect(method = "canMoveAdjacentBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Direction;values()[Lnet/minecraft/util/math/Direction;"))
    private Direction[] redirectCanMoveAdjacentBlockValues() {
        return VALUES;
    }

}
