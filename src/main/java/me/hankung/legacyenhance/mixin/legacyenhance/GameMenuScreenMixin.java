package me.hankung.legacyenhance.mixin.legacyenhance;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {

    @Inject(method = "init", at = @At("TAIL"))
    private void legacy$onInit(CallbackInfo ci) {
        if (!this.client.isIntegratedServerRunning()) {
            this.buttons.add(new ButtonWidget(777, this.width / 2 + 2, this.height / 4 + 80, 98, 20, "LegacyEnhance"));
            this.buttons.remove(3);
        }
    }

    @Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
    private void legacy$onButtonClicked(ButtonWidget button, CallbackInfo ci) {
        if (button.id == 777) {
            this.client.setScreen(LegacyEnhance.getConfigScreen(this.client.currentScreen));
            ci.cancel();
        }
    }

}
