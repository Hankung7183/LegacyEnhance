package me.hankung.legacyenhance.mixin.oldanimation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.render.entity.model.BiPedModel;

@Mixin(BiPedModel.class)
public class BiPedModelMixin {
    @ModifyConstant(method = "setAngles", constant = @Constant(floatValue = -0.5235988f))
    private float legacy$cancelRotation(float original) {
        return LegacyEnhance.CONFIG.oldanimateOldSwordBlock3rd.get() ? 0.0f : original;
    }
}
