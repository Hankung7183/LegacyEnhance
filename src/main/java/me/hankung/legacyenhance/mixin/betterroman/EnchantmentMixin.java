package me.hankung.legacyenhance.mixin.betterroman;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.hankung.legacyenhance.LegacyEnhance;
import me.hankung.legacyenhance.utils.RomanNumerals;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.CommonI18n;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @Shadow
    public abstract String getTranslationKey();

    @Inject(method = "getTranslatedName", at = @At("HEAD"), cancellable = true)
    private void legacy$modifyRomanNumerals(int level, CallbackInfoReturnable<String> cir) {
        String translation = CommonI18n.translate(this.getTranslationKey()) + " ";
        if (LegacyEnhance.CONFIG.miscellaneousNumericalEnchants.get()) {
            cir.setReturnValue(translation + level);
        } else if (LegacyEnhance.CONFIG.miscellaneousBetterRomanNumerals.get()) {
            cir.setReturnValue(translation + RomanNumerals.toRoman(level));
        }
    }

}
