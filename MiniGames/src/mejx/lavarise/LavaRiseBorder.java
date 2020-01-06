package mejx.lavarise;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import mejx.MiniGames;
import net.md_5.bungee.api.ChatColor;

public class LavaRiseBorder implements Listener {
	private static File lavarise = new File(MiniGames.getInstance().getDataFolder(), "lavarise.yml");
	private static FileConfiguration config = YamlConfiguration.loadConfiguration(lavarise);
	
	private File messages = new File(MiniGames.getInstance().getDataFolder(), "messages.yml");
	private FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messages);
	
	public static ArrayList<UUID> borderSetupers = new ArrayList<>();
	public static String arena;

	public void setBorder(String arena) {
		World w = Bukkit.getWorld(config.getString("arenas."+arena+".spawn.world"));
		int range = config.getInt("arenas."+arena+".border");
		
		w.getWorldBorder().setSize(range);
	}
	
	@EventHandler
	public void checkUUIDchat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		
		if (borderSetupers.contains(p.getUniqueId())) {
			e.setCancelled(true);
			if ((MiniGames.getInstance().isInt(msg) && msg.length() > 0 && msg.length() < 256)) {
				config.set("arenas."+arena+".border", msg);
				
				try {
					config.save(lavarise);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				borderSetupers.remove(p.getUniqueId());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.setup_setted").replace("{setupType}", "Border")));
			} else {
				borderSetupers.remove(p.getUniqueId());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.setup_cancelled").replace("{setupType}", "Border")));
			}
		}
	}
	
	@EventHandler
	public void checkUUIDquit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if (borderSetupers.contains(p.getUniqueId())) {
			borderSetupers.remove(p.getUniqueId());
		}
	}
	
	public static int getBorder(String arena) {
		if (config.contains("arenas."+arena+".border")) {
			return config.getInt("arenas."+arena+".border");
		} else {
			return -1;
		}
	}
}
