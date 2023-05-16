package me.hankung.legacyenhance.mixin.oldanimation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.util.UseAction;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "doUse", at = @At("HEAD"))
    public void legacy$doUse(CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (LegacyEnhance.CONFIG.oldanimatePunching.get()
                && mc.interactionManager.isBreakingBlock()
                && mc.player.getStackInHand() != null
                && (mc.player.getStackInHand().getUseAction() != UseAction.NONE
                        || mc.player.getStackInHand().getItem() instanceof BlockItem)) {
            mc.interactionManager.cancelBlockBreaking();
        }
    }
}
