package me.hankung.legacyenhance.mixin.scoreboardtextfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.client.gui.hud.InGameHud;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @ModifyConstant(method = "renderScoreboardObjective", constant = @Constant(intValue = 553648127))
    private int legacy$fixTextBlending(int original) {
        return -1;
    }

}
