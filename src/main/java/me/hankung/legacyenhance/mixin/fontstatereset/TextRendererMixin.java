package me.hankung.legacyenhance.mixin.fontstatereset;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.font.TextRenderer;

@Mixin(TextRenderer.class)
public abstract class TextRendererMixin {

    @Shadow
    protected abstract void resetState();

    @Inject(method = "draw(Ljava/lang/String;FFIZ)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawLayer(Ljava/lang/String;FFIZ)I", ordinal = 0, shift = At.Shift.AFTER))
    private void legacy$resetState(CallbackInfoReturnable<Integer> ci) {
        this.resetState();
    }

}
