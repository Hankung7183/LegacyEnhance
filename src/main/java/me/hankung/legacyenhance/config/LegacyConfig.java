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
	public final OptionCategory betterchat = new OptionCategory("Better Chat");
	public final BooleanOption betterEnabled = new BooleanOption("enabled", true);
    public final BooleanOption betterchatCompact = new BooleanOption("Compact", true);
	public final BooleanOption betterchatTransparent = new BooleanOption("Transparent", false);
    public final BooleanOption betterchatAnimate = new BooleanOption("Smooth", true);

	// Miscellaneous
	public final OptionCategory miscellaneous = new OptionCategory("Miscellaneous");
	public final BooleanOption miscellaneousBetterKeybind = new BooleanOption("Better Keybind Handling", true);
	public final BooleanOption miscellaneousBetterRomanNumerals = new BooleanOption("Better Roman Numerals", true);
	public final BooleanOption miscellaneousNoAchievement = new BooleanOption("No Achievement Notifications", false);
	public final BooleanOption miscellaneousNumericalEnchants = new BooleanOption("Numerical Enchantments", false);

	// OldAnimations
	public final OptionCategory oldanimate = new OptionCategory("Old Animations");
	public final BooleanOption oldanimateEnabled = new BooleanOption("enabled", true);
	public final BooleanOption oldanimateOldModel = new BooleanOption("1.7 Item Positions", true);
	public final BooleanOption oldanimateOldBow = new BooleanOption("1.7 Bow Pullback", true);
	public final BooleanOption oldanimateOldSwordBlock = new BooleanOption("1.7 Block Animation", true);
	public final BooleanOption oldanimateOldRod = new BooleanOption("1.7 Rod Position", true);
	public final BooleanOption oldanimateOldSwordBlock3rd = new BooleanOption("1.7 3rd Person Block Animation", true);
	public final BooleanOption oldanimateOldEating = new BooleanOption("1.7 Eating Animation", true);
	public final BooleanOption oldanimateOldBlockHit = new BooleanOption("1.7 Block Hit Animation", true);
	public final BooleanOption oldanimateSmoothSneaking = new BooleanOption("Smooth Sneaking", true);
	public final BooleanOption oldanimateLongSneaking = new BooleanOption("Longer Unsneak", true);
	public final BooleanOption oldanimateRedArmor = new BooleanOption("1.7 Red Armor", true);
	public final BooleanOption oldanimatePunching = new BooleanOption("Punching During Usage", true);
	public final BooleanOption oldanimateItemSwitch = new BooleanOption("Item Switching Animation", true);
	// public final BooleanOption oldanimateOldHealth = new BooleanOption("Remove Health Bar Flashing", true);
	public final BooleanOption oldanimateOldTab = new BooleanOption("Tab Overlay", false);
	public final BooleanOption oldanimateOldDebugHitbox = new BooleanOption("Debug Hitbox", true);

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
	public final BooleanOption performanceStaticParticleColor = new BooleanOption("Static Particle Color", true);

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
		categories.add(oldanimate);
		categories.add(miscellaneous);
		categories.add(performance);
		categories.add(security);

		categories.forEach(io.github.axolotlclient.AxolotlClientConfig.common.options.OptionCategory::clearOptions);

		general.add(generalCleanView);

		betterchat.add(
			betterEnabled,
			betterchatCompact,
			betterchatTransparent,
			betterchatAnimate
		);

		miscellaneous.add(
			miscellaneousBetterKeybind,
			miscellaneousBetterRomanNumerals,
			miscellaneousNoAchievement,
			miscellaneousNumericalEnchants
		);

		oldanimate.add(
			oldanimateEnabled,
			oldanimateItemSwitch,
			oldanimateLongSneaking,
			oldanimateOldBlockHit,
			oldanimateOldBow,
			oldanimateOldDebugHitbox,
			oldanimateOldEating,
			// oldanimateOldHealth,
			oldanimateOldModel,
			oldanimateOldRod,
			oldanimateOldSwordBlock,
			oldanimateOldSwordBlock3rd,
			oldanimateOldTab,
			oldanimatePunching,
			oldanimateRedArmor,
			oldanimateSmoothSneaking
		);

		performance.add(performanceDownscaleTexture);

		performanceEntityCulling.add(
			performanceEntityCullingEnabled,
			performanceEntityCullingInterval,
			performanceEntityCullingCAR,
			performanceEntityCullingRNTW
		);

		performance.add(performanceEntityCulling);
		performance.add(performanceFastWorldSwapping);
		performance.add(performanceStaticParticleColor);

		security.add(securityResourceExploit);
	}
}
