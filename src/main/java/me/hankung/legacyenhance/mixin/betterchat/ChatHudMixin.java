package me.hankung.legacyenhance.mixin.betterchat;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.mojang.blaze3d.platform.GlStateManager;

import me.hankung.legacyenhance.LegacyEnhance;
import me.hankung.legacyenhance.utils.Animation;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.util.Window;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {

    // Animation Chat
    @Shadow
    private boolean hasUnreadNewMessages;

    @Shadow
    public abstract float getChatScale();

    private float percentComplete;
    private int newLines;
    private long prevMillis = System.currentTimeMillis();
    private boolean configuring;
    private float animationPercent;
    private int lineBeingDrawn;

    private void updatePercentage(long diff) {
        if (percentComplete < 1)
            percentComplete += 0.004f * diff;
        percentComplete = Animation.clamp(percentComplete, 0, 1);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void legacy$modifyChatRendering(CallbackInfo ci) {
        if (configuring) {
            ci.cancel();
            return;
        }
        long current = System.currentTimeMillis();
        long diff = current - prevMillis;
        prevMillis = current;
        updatePercentage(diff);
        float t = percentComplete;
        animationPercent = Animation.clamp(1 - (--t) * t * t * t, 0, 1);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;pushMatrix()V", ordinal = 0, shift = At.Shift.AFTER))
    private void legacy$translate(CallbackInfo ci) {
        float y = 0;
        if (LegacyEnhance.CONFIG.betterchatAnimate.get() && !this.hasUnreadNewMessages) {
            y += (9 - 9 * animationPercent) * this.getChatScale();
        }
        GlStateManager.translate(0, y, 0);
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;", ordinal = 0), index = 0)
    private int legacy$getLineBeingDrawn(int line) {
        lineBeingDrawn = line;
        return line;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Ljava/lang/String;FFI)I"), index = 3)
    private int legacy$modifyTextOpacity(int original) {
        if (LegacyEnhance.CONFIG.betterchatAnimate.get() && lineBeingDrawn <= newLines) {
            int opacity = (original >> 24) & 0xFF;
            opacity *= animationPercent;
            return (original & ~(0xFF << 24)) | (opacity << 24);
        } else {
            return original;
        }
    }

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;I)V", at = @At("HEAD"))
    private void legacy$resetPercentage(CallbackInfo ci) {
        percentComplete = 0;
    }

    @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", at = @At("STORE"), ordinal = 0)
    private List<Text> legacy$setNewLines(List<Text> original) {
        newLines = original.size() - 1;
        return original;
    }

    // Transparent Chat
    @ModifyArg(method = "render", at = @At(value = "INVOKE", ordinal = 0, target = "net/minecraft/client/gui/hud/ChatHud.fill(IIIII)V"), index = 4)
    public int legacy$customAlpha(int original) {
        return LegacyEnhance.CONFIG.betterchatTransparent.get() ? 0 : original;
    }

    // Bug Fixed
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    public abstract int getVisibleLineCount();

    @Inject(method = "getTextAt", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/hud/ChatHud;scrolledLines:I"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void legacy$stopEventsOutsideWindow(int mouseX, int mouseY, CallbackInfoReturnable<Text> cir, Window window,
            int i, float f, int j, int k, int l) {

        int line = k / client.textRenderer.fontHeight;
        if (line >= getVisibleLineCount())
            cir.setReturnValue(null);
    }

    @Redirect(method = "removeMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHudLine;getId()I"))
    private int legacy$checkIfChatLineIsNull(ChatHudLine instance) {
        if (instance == null)
            return -1;
        return instance.getId();
    }

}
