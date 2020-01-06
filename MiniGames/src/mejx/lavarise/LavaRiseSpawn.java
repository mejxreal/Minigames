package mejx.lavarise;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import mejx.FileManager;
import mejx.MiniGames;

public class LavaRiseSpawn implements Listener {
	private static File lavarise = new File(MiniGames.getInstance().getDataFolder(), "lavarise.yml");
	private static FileConfiguration config = YamlConfiguration.loadConfiguration(lavarise);
	
	private static File messages = new File(MiniGames.getInstance().getDataFolder(), "messages.yml");
	private static FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messages);

	public static Location getSpawn(String arena) {
		World w = Bukkit.getWorld(config.getString("arenas."+arena+".spawn.world"));
		double x = config.getDouble("arenas."+arena+".spawn.x");
		double y = config.getDouble("arenas."+arena+".spawn.y");
		double z = config.getDouble("arenas."+arena+".spawn.z");
		float yaw = config.getInt("arenas."+arena+".spawn.yaw");
		float pitch = config.getInt("arenas."+arena+".spawn.pitch");
		
		Location loc = new Location(w, x, y, z, yaw, pitch);
		return loc;
	}
	
	public static void setSpawn(String arena, Player p) {
		World w = p.getWorld();
		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		float yaw = p.getLocation().getYaw();
		float pitch = p.getLocation().getPitch();

		Location loc = new Location(w, x, y, z, yaw, pitch);

		config.set("arenas."+arena+".spawn.world", w.getName());
		config.set("arenas."+arena+".spawn.x", x);
		config.set("arenas."+arena+".spawn.y", y);
		config.set("arenas."+arena+".spawn.z", z);
		config.set("arenas."+arena+".spawn.yaw", yaw);
		config.set("arenas."+arena+".spawn.pitch", pitch);
		
		try {
			config.save(lavarise);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		w.getWorldBorder().setCenter(loc);
		w.setSpawnLocation(config.getInt("arenas."+arena+".spawn.x"), config.getInt("arenas."+arena+".spawn.y"), config.getInt("arenas."+arena+".spawn.z"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.setup_setted").replace("{setupType}", "Spawn")));	
	}
}
