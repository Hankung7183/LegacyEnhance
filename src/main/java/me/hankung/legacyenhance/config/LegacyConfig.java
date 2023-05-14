package me.hankung.legacyenhance.config;

import java.util.ArrayList;
import java.util.List;

import io.github.axolotlclient.AxolotlClientConfig.common.ConfigHolder;
import io.github.axolotlclient.AxolotlClientConfig.options.BooleanOption;
import io.github.axolotlclient.AxolotlClientConfig.options.EnumOption;
import io.github.axolotlclient.AxolotlClientConfig.options.Option;
import io.github.axolotlclient.AxolotlClientConfig.options.OptionCategory;

public class LegacyConfig extends ConfigHolder {

	// General
	public final OptionCategory general = new OptionCategory("general");
	public final BooleanOption generalCleanView = new BooleanOption("Clean View", false);

	// BetterChat
	public final OptionCategory betterchat = new OptionCategory("BetterChat");
    public final BooleanOption betterchatCompact = new BooleanOption("Compact", true);
	public final BooleanOption betterchatTransparent = new BooleanOption("Transparent", false);
    public final BooleanOption betterchatAnimate = new BooleanOption("Smooth", true);

	// Performance
	public final OptionCategory performance = new OptionCategory("Performance");
	public final BooleanOption performanceDownscaleTexture = new BooleanOption("Downscale Texture", true);
	// ----
	public final OptionCategory performanceEntityCulling = new OptionCategory("Entity Culling", true);
	public final BooleanOption performanceEntityCullingEnabled = new BooleanOption("enabled", true);
	public final EnumOption performanceEntityCullingInterval = new EnumOption("Culling Interval", new String[]{"10", "25", "50"}, "10");
	public final BooleanOption performanceEntityCullingCAR = new BooleanOption("Check Armorstand Rules", false);
	public final BooleanOption performanceEntityCullingRNTW = new BooleanOption("Render Nametags Through Walls", true);
	// ----
	public final BooleanOption performanceFastWorldSwapping = new BooleanOption("Fast World Swapping", true);

	// Security
	public final OptionCategory security = new OptionCategory("Security");
	public final BooleanOption securityResourceExploit = new BooleanOption("Resource Exploit Fix", true);

	
	public final List<io.github.axolotlclient.AxolotlClientConfig.common.options.OptionCategory> config = new ArrayList<>();
	private final List<Option<?>> options = new ArrayList<>();
	private final List<io.github.axolotlclient.AxolotlClientConfig.common.options.OptionCategory> categories = new ArrayList<>();

	public void add(Option<?> option) {
		options.add(option);
	}

	public void addCategory(OptionCategory cat) {
		categories.add(cat);
	}

	public List<io.github.axolotlclient.AxolotlClientConfig.common.options.OptionCategory> getCategories() {
		return categories;
	}

	public List<Option<?>> getOptions() {
		return options;
	}

	public void init() {
		categories.add(general);
		categories.add(betterchat);
		categories.add(performance);
		categories.add(security);

		categories.forEach(io.github.axolotlclient.AxolotlClientConfig.common.options.OptionCategory::clearOptions);

		general.add(generalCleanView);

		betterchat.add(betterchatCompact);
        betterchat.add(betterchatTransparent);
        betterchat.add(betterchatAnimate);

		performance.add(performanceDownscaleTexture);

		performanceEntityCulling.add(
			performanceEntityCullingEnabled,
			performanceEntityCullingInterval,
			performanceEntityCullingCAR,
			performanceEntityCullingRNTW
		);

		performance.add(performanceEntityCulling);
		performance.add(performanceFastWorldSwapping);

		security.add(securityResourceExploit);
	}
}
