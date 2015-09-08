package net.aruneko.daynightvoting;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Configuration {

	private JavaPlugin jplug;

	private static int rejectTime, votableTime;

	public Configuration(JavaPlugin j) {
		this.jplug = j;
		rejectTime = votableTime = 0;
	}

	public void loadConfig() {
		try {
			if (!jplug.getDataFolder().exists()) {
				jplug.getDataFolder().mkdirs();
			}

			FileSystem fs = FileSystems.getDefault();
			Path configFilePath = fs.getPath(jplug.getDataFolder().toString(), "config.yml");
			FileConfiguration conf;

			if (!Files.exists(configFilePath)) {
				jplug.getLogger().info("config.yml is not found, creating.");
				jplug.saveDefaultConfig();
				conf = jplug.getConfig();
			} else {
				jplug.getLogger().info("config.yml is found, loading.");
				conf = YamlConfiguration.loadConfiguration(configFilePath.toFile());
			}

			rejectTime = conf.getInt("RejectVotingTime(s)");
			votableTime = conf.getInt("VotableTime(s)");
			jplug.getLogger().info("RejectTime: " + rejectTime + ", VotableTime: " + votableTime);
		} catch (Exception e) {
			jplug.getLogger().info("Loading config.yml is failed!");
		}
	}

	public static int getRejectTime() {
		return rejectTime;
	}

	public static int getVotableTime() {
		return votableTime;
	}

}
