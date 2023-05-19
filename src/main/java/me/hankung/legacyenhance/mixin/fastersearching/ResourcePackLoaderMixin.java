package me.hankung.legacyenhance.mixin.fastersearching;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.ResourcePackScreen;
import net.minecraft.client.resource.ResourcePackLoader;

@Mixin(ResourcePackLoader.class)
public class ResourcePackLoaderMixin {

    @Inject(method = "initResourcePacks", at = @At("HEAD"), cancellable = true)
    private void legacy$searchUsingSet(CallbackInfo ci) {
        updateRepositoryEntriesAll((ResourcePackLoader) (Object) this);
        ci.cancel();
    }

    private void updateRepositoryEntriesAll(ResourcePackLoader repository) {
        ResourcePackLoaderAccessor accessor = (ResourcePackLoaderAccessor) repository;

        final Map<Integer, ResourcePackLoader.Entry> all = new HashMap<>();
        for (ResourcePackLoader.Entry entry : repository.getAvailableResourcePacks()) {
            all.put(entry.hashCode(), entry);
        }

        final Set<ResourcePackLoader.Entry> newSet = new LinkedHashSet<>();
        for (File file : accessor.invokeGetResourcePacks()) {
            final ResourcePackLoader.Entry entry = repository.new Entry(file);
            final int entryHash = entry.hashCode();
            if (!all.containsKey(entryHash)) {
                try {
                    entry.loadIcon();
                    newSet.add(entry);
                } catch (Exception ignored) {
                    newSet.remove(entry);
                }
            } else {
                newSet.add(all.get(entryHash));
            }
        }

        for (ResourcePackLoader.Entry entry : all.values()) {
            if (!newSet.contains(entry)) {
                entry.close();
            }
        }

        accessor.setAvailableResourcePacks(new ArrayList<>(newSet));
    }

}
