package me.hankung.legacyenhance.mixin.resolvecrash;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.gui.widget.LanServerEntry;

@Mixin(MultiplayerServerListWidget.class)
public class MultiplayerServerListWidgetMixin {

    @Shadow
    @Final
    private List<LanServerEntry> lanServers;

    @Shadow
    @Final
    private EntryListWidget.Entry scanningWidget;

    @Inject(method = "getEntry", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/multiplayer/MultiplayerServerListWidget;lanServers:Ljava/util/List;"), cancellable = true)
    private void legacy$resolveIndexError(int index, CallbackInfoReturnable<EntryListWidget.Entry> cir) {
        if (index >= this.lanServers.size()) {
            cir.setReturnValue(this.scanningWidget);
        }
    }

}
