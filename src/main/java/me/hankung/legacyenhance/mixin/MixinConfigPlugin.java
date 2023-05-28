package me.hankung.legacyenhance.mixin;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;

import net.fabricmc.loader.api.FabricLoader;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    
    private final boolean hasAxolotl = FabricLoader.getInstance().isModLoaded("axolotlclient");
    private final boolean hasVanillaFix = FabricLoader.getInstance().isModLoaded("legacyvanillafix");

    @Override
    public void onLoad(String mixinPackage) {
        MixinExtrasBootstrap.init();
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!isMinimumJava(11) && containsAny(mixinClassName, new String[] { "pipeline.compression", "pipeline.encryption" })) {
            return false;
        }
        if (hasAxolotl && mixinClassName.contains("fastworldswapping.MinecraftClientMixin")) {
            return false;
        }
        if (hasVanillaFix && containsAny(mixinClassName, new String[] { "armpositionfix", "particleculling", "bettercamera" })) {
            return false;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    private boolean containsAny(String mixinClassName, String[] substrings) {
        for (String substring : substrings) {
            if (mixinClassName.contains(substring)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMinimumJava(int java) {
        String version = System.getProperty("java.specification.version");
        int majorVersion = Integer.parseInt(version.split("\\.")[0]);

        return majorVersion >= java;
    }

}
