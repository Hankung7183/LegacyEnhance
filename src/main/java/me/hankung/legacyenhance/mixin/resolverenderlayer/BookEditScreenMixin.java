package me.hankung.legacyenhance.mixin.resolverenderlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;

@Mixin(BookEditScreen.class)
public class BookEditScreenMixin extends Screen {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/BookEditScreen;renderTextHoverEffect(Lnet/minecraft/text/Text;II)V"))
    private void legacy$callSuper(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        super.render(mouseX, mouseY, partialTicks);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/BookEditScreen;renderTextHoverEffect(Lnet/minecraft/text/Text;II)V", shift = At.Shift.AFTER), cancellable = true)
    private void legacy$cancelFurtherRendering(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        ci.cancel();
    }

}
