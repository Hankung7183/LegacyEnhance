package me.hankung.legacyenhance.mixin.splitremnantsfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

@Mixin(HandledScreen.class)
public class HandledScreenMixin extends Screen {

    @Shadow
    private int heldButtonCode;

    @Shadow
    private int draggedStackRemainder;

    @Inject(method = "calculateOffset", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;"), cancellable = true)
    private void legacy$fixRemnants(CallbackInfo ci) {
        if (this.heldButtonCode == 2) {
            this.draggedStackRemainder = client.player.inventory.getCursorStack().getMaxCount();
            ci.cancel();
        }
    }

}
