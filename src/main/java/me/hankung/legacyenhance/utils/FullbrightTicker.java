package me.hankung.legacyenhance.utils;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.server.MinecraftServer;

public class FullbrightTicker {

    public static boolean isFullbright() {
        MinecraftServer server = MinecraftServer.getServer();
        if (server != null && server.isOnThread()) {
            return false;
        }

        return LegacyEnhance.CONFIG.generalFullBright.get();
    }

}
