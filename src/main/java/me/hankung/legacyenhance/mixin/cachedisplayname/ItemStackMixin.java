package me.hankung.legacyenhance.mixin.cachedisplayname;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.ItemStack;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    private String legacy$cachedDisplayName;

    @Inject(method = "getCustomName", at = @At("HEAD"), cancellable = true)
    private void legacy$returnCachedDisplayName(CallbackInfoReturnable<String> cir) {
        if (legacy$cachedDisplayName != null) {
            cir.setReturnValue(legacy$cachedDisplayName);
        }
    }

    @Inject(method = "getCustomName", at = @At("RETURN"))
    private void legacy$cacheDisplayName(CallbackInfoReturnable<String> cir) {
        legacy$cachedDisplayName = cir.getReturnValue();
    }

    @Inject(method = "setCustomName", at = @At("HEAD"))
    private void legacy$resetCachedDisplayName(String displayName, CallbackInfoReturnable<ItemStack> cir) {
        legacy$cachedDisplayName = null;
    }

}
