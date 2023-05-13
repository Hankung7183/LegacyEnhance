package me.hankung.legacyenhance.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.hankung.legacyenhance.LegacyEnhance;

public class ModMenu implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return screen -> {
			return LegacyEnhance.getConfigScreen(screen);
		};
	}
}
