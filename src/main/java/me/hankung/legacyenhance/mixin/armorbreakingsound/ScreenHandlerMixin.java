package me.hankung.legacyenhance.mixin.armorbreakingsound;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.slot.Slot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {
    @Inject(method = "setStackInSlot", at = @At("HEAD"))
    private void legacy$playArmorBreakingSound(int slotID, ItemStack stack, CallbackInfo ci) {
        if (!MinecraftClient.getInstance().world.isClient || stack != null) {
            return;
        }

        ScreenHandler screenHandler = (ScreenHandler) (Object) this;
        if (slotID >= 5 && slotID <= 8 && screenHandler instanceof PlayerScreenHandler) {
            Slot slot = screenHandler.getSlot(slotID);
            if (slot != null) {
                ItemStack slotStack = slot.getStack();
                if (slotStack != null && slotStack.getItem() instanceof ArmorItem && slotStack.getDamage() > slotStack.getMaxDamage() - 2) {
                    MinecraftClient.getInstance().player.playSound("random.break", 1, 1);
                }
            }
        }
    }
}
