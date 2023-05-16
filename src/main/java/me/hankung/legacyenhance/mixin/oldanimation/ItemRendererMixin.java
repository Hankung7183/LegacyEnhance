package me.hankung.legacyenhance.mixin.oldanimation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Unique
    private LivingEntity lastEntityToRenderFor = null;

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;)V", at = @At("HEAD"))
    public void legacy$renderItem(ItemStack stack, LivingEntity entityToRenderFor,
            Mode cameraTransformType, CallbackInfo ci) {
        this.lastEntityToRenderFor = entityToRenderFor;
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/BakedModel;)V"))
    public void legacy$renderItem(ItemStack stack, BakedModel model,
            Mode cameraTransformType, CallbackInfo ci) {
        if (cameraTransformType == Mode.THIRD_PERSON && this.lastEntityToRenderFor instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) this.lastEntityToRenderFor;
            ItemStack heldStack = p.getStackInHand();
            if (heldStack != null && p.getItemUseTicks() > 0 && heldStack.getUseAction() == UseAction.BLOCK) {
                LegacyEnhance.oldAnimation.animationHandler.doSwordBlock3rdPersonTransform();
            }
        }
    }

}
