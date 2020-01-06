package mejx.lavarise;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import mejx.MiniGames;

public class LavaRiseCommand implements CommandExecutor, Listener {
	private File lavarise = new File(MiniGames.getInstance().getDataFolder(), "lavarise.yml");
	private FileConfiguration config = YamlConfiguration.loadConfiguration(lavarise);
	
	private File messages = new File(MiniGames.getInstance().getDataFolder(), "messages.yml");
	private FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messages);
	
	private static String arena;
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("General.onlyPlayer")));
		} else {
			Player p = (Player) sender;

			if (cmd.getName().equals("lavarise")) {
				if (!p.hasPermission("minigames.lavarise")) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("General.noPermission")));
				} else {
					if (args.length == 0) {
						for (String msg : messagesConfig.getStringList("LavaRise.usage")) {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
						}
					}
					
					if (args.length == 1) {
						if (!(args[0].equals("add") || args[0].equals("remove") || args[0].equals("setup") || args[0].equals("join") || args[0].equals("leave"))) {
							for (String msg : messagesConfig.getStringList("LavaRise.usage")) {
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
							}
						} else {
							if (args[0].equals("add")) {
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.usage_add")));
							}
							
							if (args[0].equals("remove")) {
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.usage_remove")));
							}
							
							if (args[0].equals("setup")) {
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.usage_setup")));
							}
							
							if (args[0].equals("join")) {
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.usage_join")));
							}
							
							if (args[0].equals("leave")) {
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.")));
							}
						}
					}
					
					if (args.length == 2) {
						if (!(args[0].equals("add") || args[0].equals("remove") || args[0].equals("setup") || args[0].equals("join") || args[0].equals("leave"))) {
							for (String msg : messagesConfig.getStringList("LavaRise.usage")) {
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
							}
						} else {
							if (args[0].equals("add")) {
								if (!config.contains("arenas."+args[1])) {
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.arena_added").replace("{arena}", args[1])));
									config.createSection("arenas."+args[1]);

									try {
										config.save(lavarise);
									} catch (IOException ex) {
										ex.printStackTrace();
									}
								} else {
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.arena_exists").replace("{arena}", args[1])));
								}
							}
							
							if (args[0].equals("remove")) {
								if (!config.contains("arenas."+args[1])) {
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.arena_doesnt_exist").replace("{arena}", args[1])));
								} else {
									config.set("arenas."+args[1], null);
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.arena_removed").replace("{arena}", args[1])));

									try {
										config.save(lavarise);
									} catch (IOException ex) {
										ex.printStackTrace();
									}
								}
							}
							
							if (args[0].equals("setup")) {
								if (!config.contains("arenas."+args[1])) {
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.arena_doesnt_exist").replace("{arena}", args[1])));
								} else {
									arena = args[1];
									openMainMenu(p);
								}
							}
						}
					}
				}
			}
		}
		
		
		return false;
	}
	
	private void openMainMenu(Player p) {
		Inventory mainMenu = Bukkit.createInventory(p, 9, ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.main.title")));
		
		ItemStack spawn = new ItemStack(Material.APPLE);
		ItemStack waitSpawn = new ItemStack(Material.STAINED_CLAY);
		ItemStack border = new ItemStack(Material.BARRIER);
		ItemStack lavaTimer = new ItemStack(Material.LAVA_BUCKET);
		ItemStack reward = new ItemStack(Material.GOLD_INGOT);
		ItemStack startFrom = new ItemStack(Material.BEDROCK);

		ItemMeta spawnM = spawn.getItemMeta();
		ItemMeta waitSpawnM = waitSpawn.getItemMeta();
		ItemMeta borderM = border.getItemMeta();
		ItemMeta lavaTimerM = lavaTimer.getItemMeta();
		ItemMeta rewardM = reward.getItemMeta();
		ItemMeta startFromM = startFrom.getItemMeta();

		spawnM.setDisplayName(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.main.spawn.item_name")));
		waitSpawnM.setDisplayName(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.main.waitSpawn.item_name")));
		borderM.setDisplayName(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.main.border.item_name")));
		lavaTimerM.setDisplayName(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.main.lavaTimer.item_name")));
		rewardM.setDisplayName(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.main.reward.item_name")));
		startFromM.setDisplayName(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.main.startFrom.item_name")));
		
		spawnM.setLore(messagesConfig.getStringList("LavaRise.menu.main.spawn.lore"));
		waitSpawnM.setLore(messagesConfig.getStringList("LavaRise.menu.main.waitSpawn.lore"));
		borderM.setLore(messagesConfig.getStringList("LavaRise.menu.main.border.lore"));
		lavaTimerM.setLore(messagesConfig.getStringList("LavaRise.menu.main.lavaTimer.lore"));
		rewardM.setLore(messagesConfig.getStringList("LavaRise.menu.main.reward.lore"));
		startFromM.setLore(messagesConfig.getStringList("LavaRise.menu.main.startFrom.lore"));
		
		spawn.setItemMeta(spawnM);
		waitSpawn.setItemMeta(waitSpawnM);
		border.setItemMeta(borderM);
		lavaTimer.setItemMeta(lavaTimerM);
		reward.setItemMeta(rewardM);
		startFrom.setItemMeta(startFromM);
		
		mainMenu.setItem(1, spawn);
		mainMenu.setItem(2, waitSpawn); // Open gui with select "spawn & time"
		mainMenu.setItem(3, border);
		mainMenu.setItem(4, lavaTimer);
		mainMenu.setItem(5, reward);
		mainMenu.setItem(6, startFrom);
		
		p.openInventory(mainMenu);
	}

	@EventHandler
	public void openMenus(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		ItemStack item = e.getCurrentItem();
		
		if (inv.getTitle().equals(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.main.title")))) {
			e.setCancelled(true);
			if (item.getItemMeta() != null) {
				if (item.getItemMeta().getDisplayName() != null) {
					if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.main.spawn.item_name")))) {
						p.closeInventory();
						LavaRiseSpawn.setSpawn(arena, p);
					}
					
					if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.main.waitSpawn.item_name")))) {
						p.closeInventory();
						p.openInventory(openWaitSpawnMenu());
					}
					
					if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.main.border.item_name")))) {
						p.closeInventory();
						LavaRiseBorder.arena = arena;
						LavaRiseBorder.borderSetupers.add(p.getUniqueId());
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.border.usage")));
					}
					
					if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.main.lavaTimer.item_name")))) {
						p.closeInventory();
						LavaRiseTimer.arena = arena;
						LavaRiseTimer.timerSetupers.add(p.getUniqueId());
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.lavaTimer.usage")));
					}
					
					if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.main.reward.item_name")))) {
						p.closeInventory();
						LavaRiseReward.arena = arena;
						LavaRiseReward.rewardSetupers.add(p.getUniqueId());
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.reward.usage")));
					}
					
					if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.main.startFrom.item_name")))) {
						p.closeInventory();
						LavaRiseStartFrom.arena = arena;
						LavaRiseStartFrom.startFromSetupers.add(p.getUniqueId());
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.startFrom.usage")));
					}
				}
			}
		}
	}

	private Inventory openWaitSpawnMenu() {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.waitSpawn.title")));
		
		ItemStack waitSpawnSetSpawn = new ItemStack(Material.STAINED_CLAY);
		ItemStack waitSpawnSetTimer = new ItemStack(Material.WATCH);
		
		ItemMeta waitSpawnSetSpawnM = waitSpawnSetSpawn.getItemMeta();
		ItemMeta waitSpawnSetTimerM = waitSpawnSetTimer.getItemMeta();
		
		waitSpawnSetSpawnM.setDisplayName(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.waitSpawn.spawnName")));
		waitSpawnSetSpawnM.setLore(messagesConfig.getStringList("LavaRise.menu.waitSpawn.spawnLore"));
		waitSpawnSetTimerM.setDisplayName(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.waitSpawn.timerName")));
		waitSpawnSetTimerM.setLore(messagesConfig.getStringList("LavaRise.menu.waitSpawn.timerLore"));
		
		waitSpawnSetSpawn.setItemMeta(waitSpawnSetSpawnM);
		waitSpawnSetTimer.setItemMeta(waitSpawnSetTimerM);
		
		inv.setItem(2, waitSpawnSetSpawn);
		inv.setItem(5, waitSpawnSetTimer);
		
		return inv;
	}
	
	@EventHandler
	public void doActions(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		ItemStack item = e.getCurrentItem();
		
		if (inv != null) {
			if (inv.getTitle().equals(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.waitSpawn.title")))) {
				e.setCancelled(true);
				if (item != null) {
					if (item.getItemMeta() != null) {
						if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.waitSpawn.spawnName")))) {
							p.closeInventory();
							LavaRiseWaitSpawn.setWaitSpawn(arena, p);
						}
						
						if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.menu.waitSpawn.timerName")))) {
							p.closeInventory();
							LavaRiseTimer.timerSetupers.add(p.getUniqueId());
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("LavaRise.waitSpawn.enter_amount")));
						}
					}
				}
			}
		}
	}
}
