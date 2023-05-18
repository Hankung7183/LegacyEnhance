package me.hankung.legacyenhance.mixin.casecommands;

import java.util.Locale;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.server.command.CommandRegistry;

@Mixin(CommandRegistry.class)
public class CommandRegistryMixin {
    @ModifyArg(
        method = { "execute", "getCompletions" },
        at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;")
    )
    private Object legacy$makeLowerCaseForGet(Object s) {
        if (s instanceof String) {
            return ((String) s).toLowerCase(Locale.ENGLISH);
        }
        return s;
    }

    @ModifyArg(method = "registerCommand", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), index = 0)
    private Object legacy$makeLowerCaseForPut(Object s) {
        if (s instanceof String) {
            return ((String) s).toLowerCase(Locale.ENGLISH);
        }
        return s;
    }

    @ModifyArg(method = "getCompletions", at = @At(value = "INVOKE", target = "Lnet/minecraft/command/AbstractCommand;method_2883(Ljava/lang/String;Ljava/lang/String;)Z"), index = 0)
    private String legacy$makeLowerCaseForTabComplete(String s) {
        return s != null ? s.toLowerCase(Locale.ENGLISH) : null;
    }
}
