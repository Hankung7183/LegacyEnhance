package me.hankung.legacyenhance.mixin.oldanimation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @Shadow
    private ItemStack mainHand;

    @Shadow
    private float lastEquipProgress;

    @Shadow
    private float equipProgress;

    @Shadow
    private int selectedSlot;

    @Inject(method = "renderArmHoldingItem", at = @At(value = "HEAD"), cancellable = true)
    public void legacy$renderArmHoldingItem(float partialTicks, CallbackInfo ci) {
        if (this.mainHand != null) {
            HeldItemRenderer $this = (HeldItemRenderer) ((Object) this);
            float equipProgress = this.lastEquipProgress + (this.equipProgress - this.lastEquipProgress) * partialTicks;
            if (LegacyEnhance.oldAnimation.animationHandler.renderItemInFirstPerson($this, this.mainHand, equipProgress,
                    partialTicks)) {
                ci.cancel();
            }
        }
    }

    @ModifyArg(method = "updateHeldItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"), index = 0)
    private float legacy$handleItemSwitch(float original) {
        ClientPlayerEntity entityplayer = MinecraftClient.getInstance().player;
        ItemStack itemstack = entityplayer.inventory.getMainHandStack();
        if (LegacyEnhance.CONFIG.oldanimateItemSwitch.get() && this.selectedSlot == entityplayer.inventory.selectedSlot
                && ItemStack.equalsIgnoreNbt(this.mainHand, itemstack)) {
            return 1.0f - this.equipProgress;
        }
        return original;
    }

}
