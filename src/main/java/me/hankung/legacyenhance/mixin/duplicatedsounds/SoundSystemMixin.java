package me.hankung.legacyenhance.mixin.duplicatedsounds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;

@Mixin(SoundSystem.class)
public abstract class SoundSystemMixin {

    @Shadow
    public abstract boolean isPlaying(SoundInstance sound);

    @Shadow
    @Final
    private Map<String, SoundInstance> field_8195;

    private final List<String> legacy$pausedSounds = new ArrayList<>();

    @Redirect(method = "pauseAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundSystem$ThreadSafeSoundSystem;pause(Ljava/lang/String;)V"))
    private void legacy$onlyPauseSoundIfNecessary(@Coerce paulscode.sound.SoundSystem soundSystem, String sound) {
        if (isPlaying(field_8195.get(sound))) {
            soundSystem.pause(sound);
            legacy$pausedSounds.add(sound);
        }
    }

    @Redirect(method = "resumeAll", at = @At(value = "INVOKE", target = "Ljava/util/Set;iterator()Ljava/util/Iterator;"))
    private Iterator<String> legacy$iterateOverPausedSounds(Set<String> keySet) {
        return legacy$pausedSounds.iterator();
    }

    @Inject(method = "resumeAll", at = @At("TAIL"))
    private void legacy$clearPausedSounds(CallbackInfo ci) {
        legacy$pausedSounds.clear();
    }

}
