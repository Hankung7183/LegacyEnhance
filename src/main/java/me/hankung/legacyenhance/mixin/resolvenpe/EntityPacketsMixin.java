package me.hankung.legacyenhance.mixin.resolvenpe;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySetHeadYawS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.world.World;

@Mixin({ EntityS2CPacket.class, EntitySetHeadYawS2CPacket.class, EntityStatusS2CPacket.class })
public class EntityPacketsMixin {

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = { "getEntity", "func_149065_a", "func_149381_a", "func_149161_a" }, at = @At("HEAD"), cancellable = true, remap = false)
    private void legacy$addNullCheck(World worldIn, CallbackInfoReturnable<Entity> cir) {
        if (worldIn == null) {
            cir.setReturnValue(null);
        }
    }

}
