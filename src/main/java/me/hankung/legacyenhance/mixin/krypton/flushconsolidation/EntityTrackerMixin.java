package me.hankung.legacyenhance.mixin.krypton.flushconsolidation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.ServerPlayerEntity;

import static me.hankung.legacyenhance.utils.krypton.AutoFlushUtil.setAutoFlush;

@Mixin(EntityTracker.class)
public class EntityTrackerMixin {
    
    @Inject(at = @At("HEAD"), method = "startTracking")
    public void legacy$disableAutoFlush(ServerPlayerEntity player, CallbackInfo ci) {
        setAutoFlush(player, false);
    }

    @Inject(at = @At("RETURN"), method = "startTracking")
    public void legacy$reenableAutoFlush(ServerPlayerEntity player, CallbackInfo ci) {
        setAutoFlush(player, true);
    }

}
