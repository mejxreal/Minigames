package mejx;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import mejx.lavarise.LavaRiseBorder;
import mejx.lavarise.LavaRiseCommand;
import mejx.lavarise.LavaRiseReward;
import mejx.lavarise.LavaRiseStartFrom;
import mejx.lavarise.LavaRiseTimer;

public class MiniGames extends JavaPlugin {
	public static MiniGames instance;
	
	private File lavarise = new File(getDataFolder(), "lavarise.yml");
	private FileConfiguration config = YamlConfiguration.loadConfiguration(lavarise);

	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		instance = this;
		FileManager.createFile("lavarise.yml");
		FileManager.setLavaRiseConfig();
		FileManager.createFile("messages.yml");
		FileManager.setLavaRiseMessages();
		
		getCommand("lavarise").setExecutor(new LavaRiseCommand());
		pm.registerEvents(new LavaRiseCommand(), this);
		pm.registerEvents(new LavaRiseReward(), this);
		pm.registerEvents(new LavaRiseBorder(), this);
		pm.registerEvents(new LavaRiseTimer(), this);
		pm.registerEvents(new LavaRiseStartFrom(), this);
	}

	public void onDisable() {
		try {
			config.save(lavarise);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	

	public static MiniGames getInstance() {
		return instance;
	}
	
	public boolean isInt(String s) {
		if (s.contains("0") || s.contains("1") || s.contains("2") || s.contains("3") || s.contains("4") || s.contains("5") || s.contains("6") || s.contains("7") || s.contains("8") || s.contains("9")) {
			return true;
		}
		
		return false;
	}
}
