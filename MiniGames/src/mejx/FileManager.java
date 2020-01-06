package mejx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
	public static File file;
	public static FileConfiguration config;
	
	
	public static void createFile(String fileName) {
		try {
			if (!MiniGames.getInstance().getDataFolder().exists()) {
				MiniGames.getInstance().getDataFolder().mkdirs();
			}
			
			file = new File(MiniGames.getInstance().getDataFolder(), fileName);
			
			
			if (!file.exists()) {
				MiniGames.getInstance().getLogger().info("§f[MiniGames] "+fileName+" have been created");
				file.createNewFile();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void setLavaRiseConfig() {
		File file = new File(MiniGames.getInstance().getDataFolder(), "lavarise.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		if (!file.exists()) {
			try {
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void setLavaRiseMessages() {
		File file = new File(MiniGames.getInstance().getDataFolder(), "messages.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		String pfx = "&8[&cLavaRise&8] &c";
		
		ArrayList<String> usage = new ArrayList<>();
		
		usage.add("&8&m------------&r &c&lLavaRise &8&m------------");
		usage.add("");
		usage.add("&c/lavarise &8- &cShow this");
		usage.add("&c/lavarise add <Arena> &8- &cAdd arena");
		usage.add("&c/lavarise remove <Arena> &8- &cRemove arena");
		usage.add("&c/lavarise setup <Arena> &8- &cSetup arena");
		usage.add("&c/lavarise join <Arena> &8- &cJoin the arena!");
		usage.add("&c/lavarise leave &8- &cLeave the arena!");
		usage.add("");
		usage.add("&8&m----------------------------------");
		
		config.addDefault("General.onlyPlayer", pfx+"This command can use only Player!");
		config.addDefault("General.noPermission", pfx+"Not enough permissions!");
		config.addDefault("LavaRise.usage", usage);
		config.addDefault("LavaRise.arena_added", pfx+"Arena &8{arena} &cwas added!");
		config.addDefault("LavaRise.arena_exists", pfx+"Arena &8{arena} &calready exists!");
		config.addDefault("LavaRise.usage_add", pfx+"To add arena type &8/lavarise add <Arena>");
		config.addDefault("LavaRise.usage_remove", pfx+"To remove arena type &8/lavarise remove <Arena>");
		config.addDefault("LavaRise.usage_setup", pfx+"To setup arena type &8/lavarise setup <Arena>");
		config.addDefault("LavaRise.usage_join", pfx+"To join to arena type &8/lavarise join <Arena>");
		config.addDefault("LavaRise.arena_doesnt_exist", pfx+"Arena &8{arena} &cdoesn''t exist!");
		config.addDefault("LavaRise.arena_removed", pfx+"Arena &8{arena} &cwas removed!");
		config.addDefault("LavaRise.game_starting", pfx+"Game is starting in &8{time}");
		config.addDefault("LavaRise.game_started", pfx+"Game started! Good luck!");
		config.addDefault("LavaRise.game_ended", pfx+"Game ended! Thanks for playing!");
		config.addDefault("LavaRise.menu.main.title", "&c&lLavaRise &8settings");
		config.addDefault("LavaRise.menu.main.spawn.item_name", "&8Spawn &csettings");
		config.addDefault("LavaRise.menu.main.spawn.lore", Arrays.asList("","§cSet a spawn where is player standing!"));
		config.addDefault("LavaRise.menu.main.waitSpawn.item_name", "&8WaitSpawn &csettings");
		config.addDefault("LavaRise.menu.main.waitSpawn.lore", Arrays.asList("","§cSet a wait spawn where is player standing!"));
		config.addDefault("LavaRise.menu.main.border.item_name", "&8Border &csettings");
		config.addDefault("LavaRise.menu.main.border.lore", Arrays.asList("","§cAfter you click at this item!", "§cEnter the range amount to the chat!"));
		config.addDefault("LavaRise.menu.main.lavaTimer.item_name", "&8LavaTimer &csettings");
		config.addDefault("LavaRise.menu.main.lavaTimer.lore", Arrays.asList("","§cSet a timer for lava rise!"));
		config.addDefault("LavaRise.menu.main.reward.item_name", "&8Reward &csettings");
		config.addDefault("LavaRise.menu.main.reward.lore", Arrays.asList("","§cAfter you click at this item!", "§cEnter the commands to after player win the game", "§cAfter you want to stop it enter a §8:finish"));
		config.addDefault("LavaRise.menu.main.startFrom.item_name", "&8StartFrom &csettings");
		config.addDefault("LavaRise.menu.main.startFrom.lore", Arrays.asList("","§cAfter you click at this item!", "§cEnter to the chat from what §8Y§c you want to lava rise!"));
		config.addDefault("LavaRise.setup_setted", pfx+"§8{setupType}§c setup have been succesfully setted!");
		config.addDefault("LavaRise.setup_cancelled", pfx+"§8{setupType}§c setup have been cancelled!");
		config.addDefault("LavaRise.broadcast", pfx+"{msg}");
		config.addDefault("LavaRise.player_death", pfx+"{player} died!");
		config.addDefault("LavaRise.menu.waitSpawn.title", "&c&lWaitSpawn &8settings");
		config.addDefault("LavaRise.menu.waitSpawn.spawnName", "&8WaitSpawn &csettings");
		config.addDefault("LavaRise.menu.waitSpawn.spawnLore", Arrays.asList("", "§cSet a §8WaitSpawn §cwhere is player standing!"));
		config.addDefault("LavaRise.menu.waitSpawn.timerName", "&8WaitSpawn timer &csettings");
		config.addDefault("LavaRise.menu.waitSpawn.timerLore", Arrays.asList("", "§cAfter you click at this item!", "§cEnter the time amount to the chat!"));
		config.addDefault("LavaRise.waitSpawn.enter_amount", pfx+"Enter the amount for the WaitSpawnTimer!");
		config.addDefault("LavaRise.reward.wrong_usage", pfx+"To cancel the &8Reward &csetup enter &8:finish");
		config.addDefault("LavaRise.reward.usage", pfx+"Enter the commands to chat, to set rewards after it enter &8:finish");
		config.addDefault("LavaRise.border.usage", pfx+"Enter the range of border to chat!");
		config.addDefault("LavaRise.lavaTimer.usage", pfx+"Enter the amount of the lava increase!");
		config.addDefault("LavaRise.startFrom.usage", pfx+"Enter the amount of the lava start increase from!");

		config.options().copyDefaults(true);
		try {
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
}
