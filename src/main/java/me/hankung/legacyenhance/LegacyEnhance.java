package me.hankung.legacyenhance;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import io.github.axolotlclient.AxolotlClientConfig.AxolotlClientConfigManager;
import io.github.axolotlclient.AxolotlClientConfig.DefaultConfigManager;
import io.github.axolotlclient.AxolotlClientConfig.common.ConfigManager;
import io.github.axolotlclient.AxolotlClientConfig.options.OptionCategory;
import io.github.axolotlclient.AxolotlClientConfig.screen.OptionsScreenBuilder;

import me.hankung.legacyenhance.config.LegacyConfig;
import me.hankung.legacyenhance.utils.Logger;

public class LegacyEnhance implements ClientModInitializer {

	public static String NAME = "LegacyEnhance";
	public static int maxChatLength = 256;

	public static LegacyConfig CONFIG;
	public static ConfigManager configManager;
	public static Logger LOGGER = new Logger();

	@Override
	public void onInitializeClient() {
		CONFIG = new LegacyConfig();

		CONFIG.init();

		CONFIG.config.addAll(CONFIG.getCategories());

		AxolotlClientConfigManager.getInstance().registerConfig(NAME, CONFIG,
				configManager = new DefaultConfigManager(NAME,
						FabricLoader.getInstance().getConfigDir().resolve(NAME + ".json"), CONFIG.config));
		AxolotlClientConfigManager.getInstance().addIgnoredName(NAME, "x");
		AxolotlClientConfigManager.getInstance().addIgnoredName(NAME, "y");

		LOGGER.info("Initalized");
	}

	public static Screen getConfigScreen(Screen parent) {
		return new OptionsScreenBuilder(parent,
				(OptionCategory) new OptionCategory(LegacyEnhance.NAME, false)
						.addSubCategories(LegacyEnhance.CONFIG.getCategories()),
				LegacyEnhance.NAME);
	}

	public static void sendChatMessage(String message) {
		MinecraftClient mc = MinecraftClient.getInstance();
		mc.inGameHud.getChatHud().addMessage(new LiteralText("§8[§d" + NAME + "§8] §f" + message));
	}
}
