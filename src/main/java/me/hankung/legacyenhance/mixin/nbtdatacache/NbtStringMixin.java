package me.hankung.legacyenhance.mixin.nbtdatacache;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.nbt.NbtString;

@Mixin(NbtString.class)
public class NbtStringMixin {

    @Shadow
    private String value;

    @Unique
    private String legacy$dataCache;

    @Inject(method = "read", at = @At("HEAD"))
    private void legacy$emptyDataCache(CallbackInfo ci) {
        this.legacy$dataCache = null;
    }

    /**
     * @author asbyth
     * @reason Utilize data cache
     */
    @Overwrite
    public String toString() {
        if (this.legacy$dataCache == null) {
            this.legacy$dataCache = "\"" + this.value.replace("\"", "\\\"") + "\"";
        }

        return this.legacy$dataCache;
    }

}
