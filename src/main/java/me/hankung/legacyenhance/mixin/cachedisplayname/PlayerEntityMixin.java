package me.hankung.legacyenhance.mixin.cachedisplayname;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends EntityMixin {

    @Inject(method = "getName", at = @At("RETURN"))
    private void legacy$cachePlayerDisplayName(CallbackInfoReturnable<Text> cir) {
        super.legacy$cacheDisplayName(cir);
    }

    @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
    private void legacy$returnCachedPlayerDisplayName(CallbackInfoReturnable<Text> cir) {
        super.legacy$returnCachedDisplayName(cir);
    }

    @Redirect(method = "getName", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getHoverEvent()Lnet/minecraft/text/HoverEvent;"))
    private HoverEvent legacy$onlyGetHoverEventInSinglePlayer(PlayerEntity instance) {
        // Only needed in single player
        return MinecraftClient.getInstance().isIntegratedServerRunning()
                ? ((PlayerEntityMixin) (Object) instance).getHoverEvent()
                : null;
    }

    @Redirect(method = "getName", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Style;setHoverEvent(Lnet/minecraft/text/HoverEvent;)Lnet/minecraft/text/Style;"))
    private Style legacy$onlySetHoverEventInSinglePlayer(Style instance, HoverEvent event) {
        return MinecraftClient.getInstance().isIntegratedServerRunning()
                ? instance.setHoverEvent(event)
                : null;
    }

}
