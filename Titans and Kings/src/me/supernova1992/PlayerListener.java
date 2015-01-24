package me.supernova1992;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class PlayerListener implements Listener{

	public PlayerListener(TitansAndKings plugin){
			
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
			
	}
	
		@EventHandler
		public void onLogin(PlayerJoinEvent e){
			
			Player player = e.getPlayer();
			
			//ScoreboardManager manager = Bukkit.getScoreboardManager();
			
			Scoreboard tVsK = TitansAndKings.getTVsK();
			
			player.sendMessage("Main scoreboard is " + tVsK);
			
			player.setScoreboard(tVsK);
				
			
		}
		@EventHandler
		public void onThrow(PlayerEggThrowEvent e) {
			
			Player player = e.getPlayer();
			
			player.sendMessage(ChatColor.RED + "DO NOT THROW EGGS!");
			
			
		}
	
}

