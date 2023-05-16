package me.hankung.legacyenhance.mixin.oldanimation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.gui.hud.PlayerListHud;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {
    @ModifyVariable(method = "render", at = @At(value = "STORE"), index = 11)
    private boolean legacy$checkTabSetting(boolean original) {
        return !LegacyEnhance.CONFIG.oldanimateOldTab.get() && original;
    }
}
