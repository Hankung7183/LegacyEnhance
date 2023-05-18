package me.hankung.legacyenhance.mixin.clearhandles;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ResourcePackScreen;
import net.minecraft.client.resource.ResourcePackLoader;
import net.minecraft.resource.ResourcePack;

@Mixin(ResourcePackScreen.class)
public class ResourcePackScreenMixin {
    @Inject(method = "buttonClicked", at = @At(value = "INVOKE", target = "Ljava/util/Collections;reverse(Ljava/util/List;)V"))
    private void legacy$clearHandles(CallbackInfo ci) {
        ResourcePackLoader repository = MinecraftClient.getInstance().getResourcePackLoader();
        for (ResourcePackLoader.Entry entry : repository.getSelectedResourcePacks()) {
            ResourcePack current = repository.getServerContainer();
            if (current == null || !entry.getName().equals(current.getName())) {
                entry.close();
            }
        }
    }
}
