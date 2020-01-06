package mejx.lavarise;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import mejx.FileManager;
import mejx.MiniGames;
import net.md_5.bungee.api.ChatColor;

public class LavaRiseStartFrom implements Listener {
	public static String arena;
	public static ArrayList<UUID> startFromSetupers = new ArrayList<>();

	private static File lavarise = new File(MiniGames.getInstance().getDataFolder(), "lavarise.yml");
	private static FileConfiguration config = YamlConfiguration.loadConfiguration(lavarise);
	
	private static File messages = new File(MiniGames.getInstance().getDataFolder(), "messages.yml");
	private static FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messages);

	@EventHandler
	public void setFrom(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		
		if (startFromSetupers.contains(p.getUniqueId())) {
			if (MiniGames.getInstance().isInt(msg)) {
				e.setCancelled(true);
				
				startFromSetupers.remove(p.getUniqueId());
				config.set("arenas."+arena+".startFrom", msg);
				
				try {
					config.save(lavarise);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.setup_setted").replace("{setupType}", "StartFrom")));
			} else {
				startFromSetupers.remove(p.getUniqueId());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.setup_cancelled").replace("{setupType}", "StartFrom")));
			}
		}
	}
	
	public static int getFrom(String arena) {
		if (config.contains("arenas."+arena+".startFrom")) {
			return config.getInt("arenas."+arena+".startFrom");
		} else {
			return -1;
		}
	}
}
