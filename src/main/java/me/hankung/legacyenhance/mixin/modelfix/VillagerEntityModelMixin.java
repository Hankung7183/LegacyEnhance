package me.hankung.legacyenhance.mixin.modelfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.client.render.entity.model.VillagerEntityModel;

@Mixin(VillagerEntityModel.class)
public class VillagerEntityModelMixin {
    @ModifyConstant(method = "<init>(FFII)V", constant = @Constant(intValue = 18))
    private int legacy$changeTextureHeight(int original) {
        return 20;
    }
}
