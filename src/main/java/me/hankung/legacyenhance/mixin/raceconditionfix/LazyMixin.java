package me.hankung.legacyenhance.mixin.raceconditionfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.util.Lazy;

@Mixin(Lazy.class)
public abstract class LazyMixin<T> {

    @Shadow
    private boolean initialized;

    @Shadow
    private T value;

    @Shadow
    protected abstract T create();

    /**
     * @author LlamaLad7
     * @reason Fix race condition
     */
    @Overwrite
    public T get() {
        if (!this.initialized) {
            synchronized (this) {
                if (!this.initialized) {
                    this.value = this.create();
                    this.initialized = true;
                }
            }
        }

        return this.value;
    }

}
