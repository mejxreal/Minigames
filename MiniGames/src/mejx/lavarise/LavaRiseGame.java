package mejx.lavarise;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import mejx.MiniGames;

public class LavaRiseGame implements Listener {
	public static ArrayList<Player> gamers = new ArrayList<Player>();
	public static ArrayList<Player> waiters = new ArrayList<Player>();
	
	private static File lavarise = new File(MiniGames.getInstance().getDataFolder(), "lavarise.yml");
	private static FileConfiguration config = YamlConfiguration.loadConfiguration(lavarise);

	private static File messages = new File(MiniGames.getInstance().getDataFolder(), "messages.yml");
	private static FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messages);
	
	@SuppressWarnings("deprecation")
	public static void startGame(String arena) {
		gamers.addAll(waiters);
		waiters.removeAll(waiters);
		LavaRiseChat.broadcast(messagesConfig.getString("LavaRise.game_started"));
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (gamers.contains(all)) {
//				LavaRiseDefaultItems.loadDefaultItems(arena, all, config);
			}
		}
		
		
	}
	
	public static void endGame() {
		
	}
	
	@EventHandler
	public void playerDie(PlayerDeathEvent e) {
		Player p = e.getEntity();
		
		if (gamers.contains(p)) {
			if (e.getKeepInventory() == false) {
				gamers.remove(p);
				LavaRiseChat.broadcast(messagesConfig.getString("LavaRise.player_death").replace("{player}", p.getName()));
			}
		}
	}
}
