package mejx.lavarise;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import mejx.MiniGames;

public class LavaRiseWait {
	private static File lavarise = new File(MiniGames.getInstance().getDataFolder(), "lavarise.yml");
	private static FileConfiguration config = YamlConfiguration.loadConfiguration(lavarise);

	public static void startWait(String arena) {
		if (checkIfInGameIsEnoughPlayers(arena, LavaRiseGame.waiters.size()) == true) {
			
		} else {
			
		}
	}
	
	public static Location teleportToWaitSpawn(String arena) {
		Location loc = new Location(Bukkit.getWorld(config.getString("arenas."+arena+".spawn.world")), config.getInt("arenas."+arena+".spawn.x"), config.getInt("arenas."+arena+".spawn.y"), config.getInt("arenas."+arena+".spawn.z"), config.getInt("arenas."+arena+".spawn.yaw"), config.getInt("arenas."+arena+".spawn.pitch"));
		return loc;
	}
	
	
	
	private static boolean checkIfInGameIsEnoughPlayers(String arena, int i) {
		if (i > config.getInt("arenas."+arena+".waitSpawn.minimalPlayers")) {
			return true;
		} else {
			return false;
		}
	}
}
