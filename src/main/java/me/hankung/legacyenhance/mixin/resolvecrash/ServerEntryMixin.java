package me.hankung.legacyenhance.mixin.resolvecrash;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.gui.widget.ServerEntry;
import net.minecraft.client.network.ServerInfo;

@Mixin(ServerEntry.class)
public abstract class ServerEntryMixin {

    @Shadow
    protected abstract void checkServerIcon();

    @Shadow
    @Final
    private ServerInfo serverInfo;

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ServerEntry;checkServerIcon()V"))
    private void legacy$resolveCrash(ServerEntry serverEntry) {
        try {
            checkServerIcon();
        } catch (Exception e) {
            LegacyEnhance.LOGGER.error("Failed to prepare server icon, setting to default.", e);
            serverInfo.setIcon(null);
        }
    }

}
