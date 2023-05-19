package me.hankung.legacyenhance.mixin.fastersearching;

import java.io.File;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.resource.ResourcePackLoader;

@Mixin(ResourcePackLoader.class)
public interface ResourcePackLoaderAccessor {

    @Invoker
    List<File> invokeGetResourcePacks();

    @Accessor
    void setAvailableResourcePacks(List<ResourcePackLoader.Entry> entries);

}
