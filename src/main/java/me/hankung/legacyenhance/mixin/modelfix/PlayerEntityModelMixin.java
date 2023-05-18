package me.hankung.legacyenhance.mixin.modelfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.render.entity.model.BiPedModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

@Mixin(PlayerEntityModel.class)
public class PlayerEntityModelMixin extends BiPedModel {

    @Shadow
    private boolean thinArms;

    @ModifyConstant(method = "<init>", constant = @Constant(floatValue = 2.5F))
    private float legacy$fixAlexArmHeight(float original) {
        return LegacyEnhance.CONFIG.bugfixesAlexArmsFix.get() ? 2.0F : original;
    }

    /**
     * @author asbyth
     * @reason Resolve item positions being incorrect on Alex models (MC-72397)
     */
    @Overwrite
    public void setArmAngle(float scale) {
        if (this.thinArms) {
            this.rightArm.pivotX += 0.5F;
            this.rightArm.preRender(scale);
            this.rightArm.pivotZ -= 0.5F;
        } else {
            this.rightArm.preRender(scale);
        }
    }

}
