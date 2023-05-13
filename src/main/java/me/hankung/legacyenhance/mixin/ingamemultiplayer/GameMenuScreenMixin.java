package me.hankung.legacyenhance.mixin.ingamemultiplayer;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {

    @Inject(method = {"init"}, at = { @At("TAIL") })
    private void onInit(CallbackInfo ci) {
        // Lan Button
        // this.buttons.remove(3);
        this.buttons.add(new ButtonWidget(333, this.width / 2 - 100, this.height / 4 + 56, 200, 20, "Multiplayer"));
    }

    @Inject(method = {"buttonClicked"}, at = {@At("HEAD")}, cancellable = true)
    private void onButtonClicked(ButtonWidget button, CallbackInfo ci) {
        if (button.id == 333) {
            this.client.setScreen(new MultiplayerScreen(this.client.currentScreen));
            ci.cancel();
        }
    }

}
