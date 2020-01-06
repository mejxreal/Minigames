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

public class LavaRiseWaitSpawn implements Listener {
	private static File lavarise = new File(MiniGames.getInstance().getDataFolder(), "lavarise.yml");
	private static FileConfiguration config = YamlConfiguration.loadConfiguration(lavarise);
	
	private static File messages = new File(MiniGames.getInstance().getDataFolder(), "messages.yml");
	private static FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messages);

	public static Location getWaitSpawn(String arena) {
		World w = Bukkit.getWorld(config.getString("arenas."+arena+".waitSpawn.world"));
		double x = config.getDouble("arenas."+arena+".waitSpawn.x");
		double y = config.getDouble("arenas."+arena+".waitSpawn.y");
		double z = config.getDouble("arenas."+arena+".waitSpawn.z");
		float yaw = config.getInt("arenas."+arena+".waitSpawn.yaw");
		float pitch = config.getInt("arenas."+arena+".waitSpawn.pitch");
		
		Location loc = new Location(w, x, y, z, yaw, pitch);
		return loc;
	}
	
	public static void setWaitSpawn(String arena, Player p) {
		World w = p.getWorld();
		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		float yaw = p.getLocation().getYaw();
		float pitch = p.getLocation().getPitch();

		Location loc = new Location(w, x, y, z, yaw, pitch);

		config.set("arenas."+arena+".waitSpawn.world", w.getName());
		config.set("arenas."+arena+".waitSpawn.x", x);
		config.set("arenas."+arena+".waitSpawn.y", y);
		config.set("arenas."+arena+".waitSpawn.z", z);
		config.set("arenas."+arena+".waitSpawn.yaw", yaw);
		config.set("arenas."+arena+".waitSpawn.pitch", pitch);
		
		try {
			config.save(lavarise);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		w.getWorldBorder().setCenter(loc);
		w.setSpawnLocation(config.getInt("arenas."+arena+".waitSpawn.x"), config.getInt("arenas."+arena+".waitSpawn.y"), config.getInt("arenas."+arena+".waitSpawn.z"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.setup_setted").replace("{setupType}", "WaitSpawn")));
	}
	
	
}
