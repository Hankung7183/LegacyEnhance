package me.hankung.legacyenhance;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.legacyfabric.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import io.github.axolotlclient.AxolotlClientConfig.AxolotlClientConfigManager;
import io.github.axolotlclient.AxolotlClientConfig.DefaultConfigManager;
import io.github.axolotlclient.AxolotlClientConfig.common.ConfigManager;
import io.github.axolotlclient.AxolotlClientConfig.options.OptionCategory;
import io.github.axolotlclient.AxolotlClientConfig.screen.OptionsScreenBuilder;
import io.netty.util.ResourceLeakDetector;
import me.hankung.legacyenhance.config.LegacyConfig;
import me.hankung.legacyenhance.utils.Logger;
import me.hankung.legacyenhance.utils.culling.EntityCulling;

public class LegacyEnhance implements ClientModInitializer {

	public static EntityCulling entityCulling;

	public static String NAME = "LegacyEnhance";
	public static int maxChatLength = 256;

	public static LegacyConfig CONFIG;
	public static ConfigManager configManager;
	public static Logger LOGGER = new Logger();

	static {
        // By default, Netty allocates 16MiB arenas for the PooledByteBufAllocator. This is too much
        // memory for Minecraft, which imposes a maximum packet size of 2MiB! We'll use 4MiB as a more
        // sane default.
        //
        // Note: io.netty.allocator.pageSize << io.netty.allocator.maxOrder is the formula used to
        // compute the chunk size. We lower maxOrder from its default of 11 to 9. (We also use a null
        // check, so that the user is free to choose another setting if need be.)
        if (System.getProperty("io.netty.allocator.maxOrder") == null) {
            System.setProperty("io.netty.allocator.maxOrder", "9");
        }

        // Disable the resource leak detector by default as it reduces performance. Allow the user to
        // override this if desired.
        if (System.getProperty("io.netty.leakDetection.level") == null) {
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);
        }
    }

	@Override
	public void onInitializeClient() {

		entityCulling = new EntityCulling();

		CONFIG = new LegacyConfig();

		CONFIG.init();

		CONFIG.config.addAll(CONFIG.getCategories());

		AxolotlClientConfigManager.getInstance().registerConfig(NAME, CONFIG,
				configManager = new DefaultConfigManager(NAME,
						FabricLoader.getInstance().getConfigDir().resolve(NAME + ".json"), CONFIG.config));
		AxolotlClientConfigManager.getInstance().addIgnoredName(NAME, "x");
		AxolotlClientConfigManager.getInstance().addIgnoredName(NAME, "y");

		entityCulling.init();

		ClientTickEvents.START_WORLD_TICK.register(e -> {
			entityCulling.worldTick();
		});

		ClientTickEvents.START_CLIENT_TICK.register(e -> {
			entityCulling.clientTick();
		});

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
