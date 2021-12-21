package net.fabricmc.example;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

public class Config {
    private final Logger LOGGER;
    private final HashMap<String, ConfigOption> configuration = new HashMap<>();
    private final File file;

    public static class ConfigOption<T> {
        private final String key;
        private T option;

        private ConfigOption(String key, T value) {
            this.key = key;
            this.option = value;
        }

        public String getKey() {
            return key;
        }

        public T getOption() {
            return option;
        }

        public void setOption(T option) {
            this.option = option;
        }
    }

    public static class BooleanConfigOption extends ConfigOption<Boolean> {
        public BooleanConfigOption(String key, Boolean value) {
            super(key, value);
        }
    }

    public static class DoubleConfigOption extends ConfigOption<Double> {
        public DoubleConfigOption(String key, Double value) {
            super(key, value);
        }
    }

    public Config(String filename, Logger LOGGER) {
        file = FabricLoader.getInstance().getConfigDir().resolve(filename).toFile();
        this.LOGGER = LOGGER;
    }

    public void load() {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                Files.createFile(file.toPath());
            } else {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                reader.lines().forEach(line -> {
                    String[] kv = line.trim().split(":");
                    if (kv.length == 2 && configuration.containsKey(kv[0])) {
                        if (kv[1].equals("true") || kv[1].equals("false"))
                            ((BooleanConfigOption)configuration.get(kv[0])).setOption(Boolean.parseBoolean(kv[1]));
                        else
                            ((DoubleConfigOption)configuration.get(kv[0])).setOption(Double.parseDouble(kv[1]));

                    }
                });
            }
        } catch (IOException e) {
            LOGGER.error("Error loading config " + e);
        }
    }

    public void CreateOption(ConfigOption configOption) {
        configuration.put(configOption.getKey(), configOption);
    }

    public void save() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("Error saving config " + e);
        }
        for (ConfigOption<?> option: configuration.values()) {
            assert writer != null;
            writer.println(option.getKey() + ":" + option.getOption());
        }
        writer.close();
    }
}
