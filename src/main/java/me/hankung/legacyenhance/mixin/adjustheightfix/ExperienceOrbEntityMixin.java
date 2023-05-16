package me.hankung.legacyenhance.mixin.adjustheightfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getEyeHeight()F"))
    private float legacy$lowerHeight(PlayerEntity playerEntity) {
        return (float) (playerEntity.getEyeHeight() / 2.0D);
    }
}
