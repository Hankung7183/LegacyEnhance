package me.hankung.legacyenhance.mixin.betterroman;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import me.hankung.legacyenhance.LegacyEnhance;
import me.hankung.legacyenhance.utils.RomanNumerals;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {

    private int legacy$potionAmplifierLevel;

    @ModifyExpressionValue(method = "drawStatusEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectInstance;getAmplifier()I", ordinal = 0))
    private int legacy$skipOriginalCode(int amplifier) {
        if (LegacyEnhance.CONFIG.generalBetterRomanNumerals.get()) {
            this.legacy$potionAmplifierLevel = amplifier;
            return 1;
        }
        return amplifier;
    }

    @ModifyExpressionValue(method = "drawStatusEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resource/language/I18n;translate(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", ordinal = 1))
    private String legacy$addRomanNumeral(String string) {
        if (LegacyEnhance.CONFIG.generalBetterRomanNumerals.get()) {
            if (this.legacy$potionAmplifierLevel > 0) {
                return RomanNumerals.toRoman(this.legacy$potionAmplifierLevel + 1);
            }
            return "";
        }
        return string;
    }

}
