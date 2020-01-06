package mejx.lavarise;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import mejx.MiniGames;
import net.md_5.bungee.api.ChatColor;

public class LavaRiseChat {
	private static File messages = new File(MiniGames.getInstance().getDataFolder(), "messages.yml");
	private static FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messages);

	@SuppressWarnings("deprecation")
	public static void broadcast(String msg) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (LavaRiseGame.gamers.contains(all)) {
				all.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.broadcast").replace("{msg}", msg)));
			}
		}
	}
}
