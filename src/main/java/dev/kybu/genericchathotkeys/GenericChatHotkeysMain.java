package dev.kybu.genericchathotkeys;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.kybu.genericchathotkeys.config.HotkeysConfig;
import dev.kybu.genericchathotkeys.data.Hotkey;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class GenericChatHotkeysMain implements ModInitializer {
	// Singleton instance
	private static GenericChatHotkeysMain INSTANCE;

	// Logger
	private final Logger logger = LoggerFactory.getLogger("generic-chat-hotkeys");
	private final Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.create();
	private HotkeysConfig hotkeysConfig;
	private File configFile;

	@Override
	public void onInitialize() {
		INSTANCE = this;
		this.loadHotkeysConfig();

		// Debug Hotkey
		new Hotkey("debug", "P", "debug-mode", true)
				.registerKeybinding();

		for (Hotkey hotkey : this.hotkeysConfig.getHotkeys()) {
			hotkey.registerKeybinding();
		}
	}

	private void loadHotkeysConfig() {
		final File configFolder = new File("config");
		if(!configFolder.exists()) {
			configFolder.mkdirs();
		}

		this.configFile = new File(configFolder, "generic-chat-hotkeys.json");
		if(!this.configFile.exists()) {
			this.hotkeysConfig = new HotkeysConfig();
			try(final FileWriter fileWriter = new FileWriter(this.configFile)) {
				gson.toJson(this.hotkeysConfig, fileWriter);
			} catch(final Exception exception) {
				logger.error("Could not create config file", exception);
			}
			return;
		}

		try(final FileReader fileReader = new FileReader(this.configFile)) {
			this.hotkeysConfig = gson.fromJson(fileReader, HotkeysConfig.class);
			logger.info("Successfully loaded hotkeys config file");
		} catch(final Exception exception) {
			logger.error("Could not read config file", exception);
		}
	}

	public Logger getLogger() {
		return logger;
	}

	public HotkeysConfig getHotkeysConfig() {
		return hotkeysConfig;
	}

	public File getConfigFile() {
		return configFile;
	}

	public static GenericChatHotkeysMain getInstance() {
		return INSTANCE;
	}
}