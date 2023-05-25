package me.hankung.legacyenhance.utils.krypton;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.ClientConnection;

public class AutoFlushUtil {

    public static void setAutoFlush(ServerPlayerEntity player, boolean val) {
        if (player.getClass() == ServerPlayerEntity.class) {
            ConfigurableAutoFlush configurableAutoFlusher = ((ConfigurableAutoFlush) player.networkHandler.getConnection());
            configurableAutoFlusher.setShouldAutoFlush(val);
        }
    }

    public static void setAutoFlush(ClientConnection conn, boolean val) {
        if (conn.getClass() == ClientConnection.class) {
            ConfigurableAutoFlush configurableAutoFlusher = ((ConfigurableAutoFlush) conn);
            configurableAutoFlusher.setShouldAutoFlush(val);
        }
    }

    private AutoFlushUtil() {}

}
