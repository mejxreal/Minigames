package mejx.lavarise;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import mejx.MiniGames;

public class LavaRiseReward implements Listener {
	public static String arena;
	public static ArrayList<UUID> rewardSetupers = new ArrayList<>();
	private ArrayList<String> commands = new ArrayList<>();

	private static File lavarise = new File(MiniGames.getInstance().getDataFolder(), "lavarise.yml");
	private static FileConfiguration config = YamlConfiguration.loadConfiguration(lavarise);
	
	private File messages = new File(MiniGames.getInstance().getDataFolder(), "messages.yml");
	private FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messages);
	
	@EventHandler
	public void setReward(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		
		if (rewardSetupers.contains(p.getUniqueId())) {
			
			e.setCancelled(true);
			commands.add(msg);
		}
	}
	
	@EventHandler
	public void setFinish(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		
		if (rewardSetupers.contains(p.getUniqueId())) {
			e.setCancelled(true);
			if (msg.equals(":finish")) {
				rewardSetupers.remove(p.getUniqueId());
				
				config.set("arenas."+arena+".reward", commands);
				
				try {
					config.save(lavarise);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.setup_setted").replace("{setupType}", "Reward")));
			} else {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.reward.wrong_usage")));
			}
		}
	}
	
	public static List<String> getReward(String arena) {
		if (config.contains("arenas."+arena+".reward")) {
			List<String> commands = config.getStringList("arenas."+arena+".reward");
			return commands;
		} else {
			return null;
		}
	}
}
