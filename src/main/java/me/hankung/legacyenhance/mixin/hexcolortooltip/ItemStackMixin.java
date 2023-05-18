package me.hankung.legacyenhance.mixin.hexcolortooltip;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Ljava/lang/Integer;toHexString(I)Ljava/lang/String;"))
    private String legacy$fixHexColorString(int i) {
        return String.format("%06X", i);
    }
}
