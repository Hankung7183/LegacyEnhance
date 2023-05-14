package me.hankung.legacyenhance.mixin.noachievement;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.gui.AchievementNotification;

@Mixin(AchievementNotification.class)
public class AchievementNotificationMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void legacy$cancelRendering(CallbackInfo ci) {
        if (LegacyEnhance.CONFIG.miscellaneousNoAchievement.get())
            ci.cancel();
    }
}
