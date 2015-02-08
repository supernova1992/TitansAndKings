package me.supernova1992;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class TitansAndKings extends JavaPlugin {
	
	@Override
	public void onEnable(){
		getLogger().info("Hello. Titans and Kings is now functioning!");
		new PlayerListener(this);
		
		
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public static ScoreboardManager manager = Bukkit.getScoreboardManager();
	
	public static Scoreboard tVsK = manager.getNewScoreboard();
	
	public static Team titans = tVsK.registerNewTeam("Titans");
	
	public static Team kings = tVsK.registerNewTeam("Kings");
	
	public static Objective objective = tVsK.registerNewObjective("Kills", "playerKillCount");
	
	public static Score kg = objective.getScore(ChatColor.RED + "King: ");
	
	public static Score tn = objective.getScore(ChatColor.GREEN + "Titan: "); //Get a fake offline player
	
	public static Scoreboard getTVsK(){
		
		return tVsK;
		
	}
	
	public static Score getKg(){
		
		return kg;
	}
	
	public static Score getTn(){
		
		return tn;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		
		
		if(cmd.getName().equalsIgnoreCase("initialize") && sender instanceof Player){
			
			Player player = (Player) sender;
			
			/*ScoreboardManager manager = Bukkit.getScoreboardManager();
			
			Scoreboard tVsK = manager.getNewScoreboard();*/
			
			/*Team titans = tVsK.registerNewTeam("Titans");
			
			Team kings = tVsK.registerNewTeam("Kings");*/
			
			
			
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			objective.setDisplayName("Kills");
			
			
			tn.setScore(0);
			
			
			kg.setScore(0);
			
			for(Player online : Bukkit.getOnlinePlayers()){
				online.setScoreboard(tVsK);
				}
			
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("titan") && sender instanceof Player){
			
			Player player = (Player) sender;
			
			/*ScoreboardManager manager = Bukkit.getScoreboardManager();
			
			Scoreboard tVsK = manager.getMainScoreboard();*/
			
			/*Team titans = tVsK.getTeam("Titans");*/
			
			Team pteam = tVsK.getPlayerTeam(player);
			
			if(pteam == null){
				titans.addPlayer(player);
				
				String pname = player.getPlayerListName();
				
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mv tp " + pname + " tvk");
				
				player.sendMessage(ChatColor.RED + "You have joined the Titan team.");
				
				player.setMaxHealth(2);
			
				
				PlayerInventory inventory = player.getInventory();
				
				inventory.addItem(new ItemStack(Material.MONSTER_EGG, 16, (short) 54));
				inventory.addItem(new ItemStack(Material.RABBIT_FOOT,6));
				inventory.addItem(new ItemStack(Material.COOKED_BEEF, 64));
				inventory.addItem(new ItemStack(Material.POTION, 16, (short) 16396));
				inventory.addItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 2));
				
				
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tp "+ pname + " 80 64 7");
				
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "gamemode 2 "+ pname);
			}else{
				player.sendMessage(ChatColor.RED + "You are already on a team. To change teams use /leave and either /titan or /king");
				
			}
			
			return true;
			
		}
		
		if(cmd.getName().equalsIgnoreCase("king") && sender instanceof Player){
			
			Player player = (Player) sender;
			
			Team pteam = tVsK.getPlayerTeam(player);
			if(pteam == null){
				kings.addPlayer(player);
				
				String pname = player.getPlayerListName();
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mv tp " + pname + " tvk");
				
				player.sendMessage(ChatColor.RED + "You have joined the King team.");
				
				player.setMaxHealth(40);
				
				player.setHealth(40);
				
				ItemStack bow = new ItemStack(Material.BOW, 1);
				
				Enchantment infinity = new EnchantmentWrapper(51);
				
				bow.addEnchantment(infinity, 1);
				
				PlayerInventory inventory = player.getInventory();
				
				inventory.addItem(new ItemStack(Material.BLAZE_POWDER, 6));
				inventory.addItem(new ItemStack(Material.BOOK, 1));
				inventory.addItem(new ItemStack(Material.ARROW, 1));
				inventory.addItem(bow);
				inventory.addItem(new ItemStack(Material.GOLD_HELMET, 1));
				inventory.addItem(new ItemStack(Material.GOLD_BOOTS, 1));
				inventory.addItem(new ItemStack(Material.GOLD_CHESTPLATE, 1));
				inventory.addItem(new ItemStack(Material.GOLD_LEGGINGS, 1));
				inventory.addItem(new ItemStack(Material.COOKED_BEEF, 64));
				inventory.addItem(new ItemStack(Material.DIAMOND_SWORD, 1));
				
				
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tp "+ pname + " 94 70 184");
				
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "gamemode 2 "+ pname);
			}else{
				
				player.sendMessage(ChatColor.RED + "You are already on a team. To change teams use /leave and either /titan or /king");
				
			}
			return true;
			
		}
		
		if(cmd.getName().equalsIgnoreCase("leave") && sender instanceof Player){
			
			Player player = (Player) sender;
			
			Team leave = tVsK.getPlayerTeam(player);
			
			if(leave==null){
				
				player.sendMessage(ChatColor.RED + "You are not on a team, or the game has encountered an error. Please message an Admin.");
			}
			
			
			if(leave.equals(titans) && leave != null){
			
				PlayerInventory inventory = player.getInventory();
				
				inventory.clear();
				player.getInventory().setHelmet(new ItemStack (Material.AIR));
	            player.getInventory().setChestplate(new ItemStack (Material.AIR));
	            player.getInventory().setLeggings(new ItemStack (Material.AIR));
	            player.getInventory().setBoots(new ItemStack (Material.AIR));
				
				player.setMaxHealth(20);
				
				player.setHealth(20);
				
				String pname = player.getPlayerListName();
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mv tp " + pname + " grumblecraft");
				
				titans.removePlayer(player);
			
				player.sendMessage(ChatColor.RED + "You have left the Titan team");
				
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "gamemode 0 "+ pname);
				
				
				
			
			}
			if(leave.equals(kings) && leave != null){
				
				PlayerInventory inventory = player.getInventory();
				
				inventory.clear();
				player.getInventory().setHelmet(new ItemStack (Material.AIR));
	            player.getInventory().setChestplate(new ItemStack (Material.AIR));
	            player.getInventory().setLeggings(new ItemStack (Material.AIR));
	            player.getInventory().setBoots(new ItemStack (Material.AIR));
				
				player.setMaxHealth(20);
				
				player.setHealth(20);
				
				
				String pname = player.getPlayerListName();
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mv tp " + pname + " grumblecraft");
				
				kings.removePlayer(player);
				
				player.sendMessage(ChatColor.RED + "You have left the King team");
				
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "gamemode 0 "+ pname);
				
				
			}
			
			return true;
		}
			if(cmd.getName().equalsIgnoreCase("tvkdp")){
				
				Player player = sender.getServer().getPlayer(args[0]);
				
				File file = new File("plugins"+File.separator+"TitansAndKings"+File.separator+"users"+File.separator+player.getDisplayName()+".yml");
				
				try {
	                
	                if(!(file.exists())){
	                    file.createNewFile();
	                }   
	            } catch (IOException event) {
	                event.printStackTrace();
	            }
				
				Writer writer = null;
				
				try {
					
					writer = new BufferedWriter(new OutputStreamWriter(
					          new FileOutputStream(file),"utf-8" ));
					    writer.write(args[1]);
				} catch (IOException ex) {
					ex.printStackTrace();
				} finally {
				   try {writer.close();} catch (Exception ex) {}
				}
				
				return true;
				
			}
		return false;
	}

}
