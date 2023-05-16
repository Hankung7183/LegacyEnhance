package me.hankung.legacyenhance.mixin.oldanimation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.item.FoodItem;

@Mixin(FoodItem.class)
public interface FoodItemAccessor {
	@Accessor("alwaysEdible")
	boolean getAlwaysEdible();
}
