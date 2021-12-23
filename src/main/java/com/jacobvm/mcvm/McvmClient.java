package com.jacobvm.mcvm;

import com.jacobvm.mcvm.mixin.OptionInvoker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class McvmClient implements ClientModInitializer {
	public static Config config;

	public static final Logger LOGGER = LogManager.getLogger("mcvmclient");
	private static KeyBinding zoomKeyBinding;
	public static boolean zoomEnabled = false;
	public static Config.DoubleConfigOption zoomFov;
	public static Config.BooleanConfigOption zoomToggled;
	public static CyclingOption<Boolean> zoomToggledOption;
	public static DoubleOption zoomFovOption;
	public static Config.BooleanConfigOption zoomScrollEnabled;
	public static CyclingOption<Boolean> zoomScrollEnabledOption;

	public static Config.BooleanConfigOption fovLocked;
	public static CyclingOption<Boolean> fovLockedOption;

	private void initConfig() {
		config = new Config("mcvmclient.config", LOGGER);

		zoomFov = new Config.DoubleConfigOption("zoomFov", 25.0);
		zoomToggled = new Config.BooleanConfigOption("zoomToggled", false);
		fovLocked = new Config.BooleanConfigOption("fovLocked", false);
		zoomScrollEnabled = new Config.BooleanConfigOption("zoomScrollEnabled", true);

		config.CreateOption(zoomFov);
		config.CreateOption(zoomToggled);
		config.CreateOption(fovLocked);
		config.CreateOption(zoomScrollEnabled);

		config.load();
	}

	private void initKeybinds() {
		zoomKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Zoom button",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_C,
				"MCVM Client"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (zoomToggled.getOption()) {
				while(zoomKeyBinding.wasPressed()) {
					zoomEnabled = !zoomEnabled;
				}
			} else
				zoomEnabled = zoomKeyBinding.isPressed();
		});
	}

	private void initOptionsScreen() {
		zoomToggledOption = CyclingOption.create("Zoom Mode",
				new TranslatableText("Toggle"),
				new TranslatableText("Hold"),
				gameOptions -> zoomToggled.getOption(),
				((gameOptions, option, value) -> {
					zoomToggled.setOption(value);
					config.save();
				}
				));

		zoomScrollEnabledOption = CyclingOption.create("Zoom Scroll",
				new TranslatableText("Enabled"),
				new TranslatableText("Disabled"),
				gameOptions -> zoomScrollEnabled.getOption(),
				((gameOptions, option, value) -> {
					zoomScrollEnabled.setOption(value);
					config.save();
				}
				));

		zoomFovOption = new DoubleOption("Zoom FOV",
				1.0,
				70.0,
				1.0f,
				gameOptions -> zoomFov.getOption(),
				((gameOptions, newFov) -> {
					zoomFov.setOption(newFov);
					config.save();
				}),
				(gameOptions, doubleOption) -> ((OptionInvoker)doubleOption).invokeGetGenericLabel(zoomFov.getOption().intValue())
		);

		fovLockedOption = CyclingOption.create("Fov lock",
				new TranslatableText("Enabled"),
				new TranslatableText("Disabled"),
				gameOptions -> fovLocked.getOption(),
				((gameOptions, option, value) -> {
					fovLocked.setOption(value);
					config.save();
				}));
	}

	@Override
	public void onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		initConfig();
		initKeybinds();
		initOptionsScreen();

		LOGGER.info("MCVMClient loaded successfully!");

	}

}
