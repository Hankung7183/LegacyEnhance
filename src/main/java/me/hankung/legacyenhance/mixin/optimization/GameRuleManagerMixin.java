package me.hankung.legacyenhance.mixin.optimization;

import java.util.Objects;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.GameRuleManager$Value")
public class GameRuleManagerMixin {

    @Shadow
    private String stringDefaultValue;

    @Inject(method = "setDefaultValue(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    private void legacy$cancelIfUnchanged(String value, CallbackInfo ci) {
        if (Objects.equals(this.stringDefaultValue, value)) {
            ci.cancel();
        }
    }

}
