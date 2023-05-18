package me.hankung.legacyenhance.mixin.resolvecrash;

import java.io.File;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.resource.ResourcePackLoader;

@Mixin(ResourcePackLoader.class)
public class ResourcePackLoaderMixin {

    @Shadow
    @Final
    private File serverResourcePackDir;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Inject(method = "deleteOldServerPack", at = @At("HEAD"))
    private void legacy$createDirectory(CallbackInfo ci) {
        if (!this.serverResourcePackDir.exists()) {
            this.serverResourcePackDir.mkdirs();
        }
    }

}
