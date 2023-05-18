package me.hankung.legacyenhance.mixin.resolvecrash;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mixin(ItemModels.class)
public class ItemModelsMixin {

    @Shadow
    @Final
    private BakedModelManager modelManager;

    @Inject(method = "getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/BakedModel;", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void legacy$returnMissingModel(ItemStack stack, CallbackInfoReturnable<BakedModel> cir, Item item) {
        if (item == null) {
            cir.setReturnValue(this.modelManager.getBakedModel());
        }
    }

}
