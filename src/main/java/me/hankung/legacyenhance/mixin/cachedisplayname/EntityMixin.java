package me.hankung.legacyenhance.mixin.cachedisplayname;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    protected abstract HoverEvent getHoverEvent();

    private long legacy$displayNameCachedAt;

    private Text legacy$cachedDisplayName;

    @Inject(method = "getName", at = @At("RETURN"))
    protected void legacy$cacheDisplayName(CallbackInfoReturnable<Text> cir) {
        legacy$cachedDisplayName = cir.getReturnValue();
        legacy$displayNameCachedAt = System.currentTimeMillis();
    }

    @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
    protected void legacy$returnCachedDisplayName(CallbackInfoReturnable<Text> cir) {
        if (System.currentTimeMillis() - legacy$displayNameCachedAt < 50L) {
            cir.setReturnValue(legacy$cachedDisplayName);
        }
    }

    @Redirect(method = "getName", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getHoverEvent()Lnet/minecraft/text/HoverEvent;"))
    private HoverEvent legacy$doNotGetHoverEvent(Entity instance) {
        return null;
    }

    @Redirect(method = "getName", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Style;setHoverEvent(Lnet/minecraft/text/HoverEvent;)Lnet/minecraft/text/Style;"))
    private Style legacy$doNotSetHoverEvent(Style instance, HoverEvent event) {
        return null;
    }

}
