package mejx.lavarise;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import mejx.FileManager;
import mejx.MiniGames;

public class LavaRiseTimer implements Listener {
	private static int timerID;
	public static int timer;

	private static File lavarise = new File(MiniGames.getInstance().getDataFolder(), "lavarise.yml");
	private static FileConfiguration config = YamlConfiguration.loadConfiguration(lavarise);
	public static String arena;
	
	private static File messages = new File(MiniGames.getInstance().getDataFolder(), "messages.yml");
	private static FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messages);

	public static ArrayList<UUID> timerSetupers = new ArrayList<>();

	public static void startTimer(String arena, Player players) {
		timerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MiniGames.getInstance(), new Runnable() {
			public void run() {
				int repeatTime = config.getInt("arenas."+arena+".lavaTimer");
				
				if (repeatTime > config.getInt("arenas."+arena+".lavaTimer")) {
					repeatTime--;
					timer = repeatTime;
					players.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.game_starting").replace("{time}", ""+repeatTime)));
				} else if (repeatTime < config.getInt("arenas."+arena+".lavaTimer")) {
					Bukkit.getScheduler().cancelTask(timerID);
					players.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.game_started")));
				}
			}
		}, 0L, 20L);
	}
	
	@EventHandler
	public void setLavaTimer(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		
		if (timerSetupers.contains(p.getUniqueId())) {
			e.setCancelled(true);
			if (MiniGames.getInstance().isInt(msg) && msg.length() > 0) {
				config.set("arenas."+arena+".lavaTimer", msg);
				
				try {
					config.save(lavarise);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				timerSetupers.remove(p.getUniqueId());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.setup_setted").replace("{setupType}", "LavaTimer")));
			} else {
				timerSetupers.remove(p.getUniqueId());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.setup_cancelled").replace("{setupType}", "LavaTimer")));
			}
		}
	}
	
	public static int getLavaTimer(String arena) {
		if (config.contains("arenas."+arena+".lavaTimer")) {
			if (timer == config.getInt("arenas."+arena+".lavaTimer")) {
				return config.getInt("arenas."+arena+".lavaTimer");
			} else {
				return timer;
			}
		} else {
			return -1;
		}
	}
}
