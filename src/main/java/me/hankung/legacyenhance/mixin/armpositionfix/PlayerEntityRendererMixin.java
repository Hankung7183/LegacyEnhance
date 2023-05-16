package me.hankung.legacyenhance.mixin.armpositionfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Redirect(method = "renderRightArm", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/model/PlayerEntityModel;sneaking:Z", ordinal = 0))
    private void legacy$resetArmState(PlayerEntityModel modelPlayer, boolean value) {
        modelPlayer.riding = modelPlayer.sneaking = false;
    }
}
