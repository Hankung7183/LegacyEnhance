package me.hankung.legacyenhance.utils;

import me.hankung.legacyenhance.LegacyEnhance;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;

public class Logger {

	private static final String INDEX = "[" + LegacyEnhance.NAME + "] ";
	public static org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(LegacyEnhance.NAME);

	public void warn(String message, Object... args) {
		LOGGER.warn(INDEX + message, args);
	}

	public void error(String message, Object... args) {
		try {
			LOGGER.error(INDEX + message, args);
		} catch (Exception e) {
			LOGGER.warn(INDEX + "[ERROR]" + message, args);
		}
	}

	public void info(String message, Object... args) {
		LOGGER.info(INDEX + message, args);
	}

	public void debug(String message, Object... args) {
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			info(INDEX + "[DEBUG] " + message, args);
		}
	}
}
