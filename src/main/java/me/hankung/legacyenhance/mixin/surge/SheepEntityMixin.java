package me.hankung.legacyenhance.mixin.surge;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.hankung.legacyenhance.LegacyEnhance;
import me.hankung.legacyenhance.utils.surge.DyeBlending;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;

@Mixin(SheepEntity.class)
public class SheepEntityMixin {
    @Inject(method = "getChildColor(Lnet/minecraft/entity/passive/AnimalEntity;Lnet/minecraft/entity/passive/AnimalEntity;)Lnet/minecraft/util/DyeColor;", at = @At("HEAD"), cancellable = true)
    private void legacy$getChildColor(AnimalEntity father, AnimalEntity mother,
            CallbackInfoReturnable<DyeColor> cir) {

        if (LegacyEnhance.CONFIG.performanceSheepDyeBlendTable.get() && father instanceof SheepEntity
                && mother instanceof SheepEntity) {
            final DyeColor fatherColor = ((SheepEntity) father).getColor();
            final DyeColor motherColor = ((SheepEntity) mother).getColor();

            final DyeColor blended = DyeBlending.getBlendedColor(fatherColor, motherColor);

            if (blended != null) {
                cir.setReturnValue(blended);
            } else {
                cir.setReturnValue(father.world.random.nextBoolean() ? fatherColor : motherColor);
            }
        }
    }
}
