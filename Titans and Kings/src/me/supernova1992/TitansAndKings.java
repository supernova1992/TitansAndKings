package me.supernova1992;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
			
			
			titans.addPlayer(player);
			
			
			player.sendMessage(ChatColor.RED + "You have joined the Titan team.");
			
			player.setMaxHealth(2);
			
			return true;
			
		}
		
		if(cmd.getName().equalsIgnoreCase("king") && sender instanceof Player){
			
			Player player = (Player) sender;
			
			kings.addPlayer(player);
			
			player.sendMessage(ChatColor.RED + "You have joined the King team.");
			
			player.setMaxHealth(40);
			
			return true;
			
		}
		
		if(cmd.getName().equalsIgnoreCase("leave") && sender instanceof Player){
			
			Player player = (Player) sender;
			
			player.setMaxHealth(20);
			
			Team leave = tVsK.getPlayerTeam(player);
			
			if(leave.equals(titans)){
			
				titans.removePlayer(player);
			
				player.sendMessage(ChatColor.RED + "You have left the team");
			
			}
			if(leave.equals(kings)){
				
				kings.removePlayer(player);
				
				player.sendMessage(ChatColor.RED + "You have left the team");
				
			}
			if(leave.equals(null)){
				
				player.sendMessage(ChatColor.RED + "You are not on a team, or the game has encountered an error. Please message an Admin.");
			}
			
			return true;
		}
		
		return false;
	}

}
