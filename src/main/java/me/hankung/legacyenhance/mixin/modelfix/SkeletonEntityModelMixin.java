package me.hankung.legacyenhance.mixin.modelfix;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.render.entity.model.BiPedModel;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;

@Mixin(SkeletonEntityModel.class)
public class SkeletonEntityModelMixin extends BiPedModel {
    @Override
    public void setArmAngle(float scale) {
        this.rightArm.pivotX++;
        this.rightArm.preRender(scale);
        this.rightArm.pivotX--;
    }
}
