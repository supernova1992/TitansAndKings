package me.supernova1992;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class PlayerListener implements Listener{

	public PlayerListener(TitansAndKings plugin){
			
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
			
	}
	
		@EventHandler
		public void onLogin(PlayerJoinEvent e){
			
			Player player = e.getPlayer();
			
			//ScoreboardManager manager = Bukkit.getScoreboardManager();
			
			Scoreboard tVsK = TitansAndKings.getTVsK();
			
			
			
			player.setScoreboard(tVsK);
				
			
		}
		@EventHandler
		public void onThrow(PlayerEggThrowEvent e) {
			
			Player player = e.getPlayer();
			
			player.sendMessage(ChatColor.RED + "DO NOT THROW EGGS!");
			
			
		}
		
		@EventHandler
		public void onHit(EntityDamageByEntityEvent e){
			
			DamageCause dam = e.getCause();
			
			Scoreboard tVsK = TitansAndKings.getTVsK();
			
			
			
			if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){
				
				Player player = (Player) e.getDamager();
				
				Player damaged = (Player) e.getEntity();
				
				Team pteam = tVsK.getPlayerTeam(player);
				
				Team dteam = tVsK.getPlayerTeam(damaged);
				
				Team titans = tVsK.getTeam("Titans");
				
				if (dam == DamageCause.ENTITY_ATTACK){
					//ItemStack hold = ((HumanEntity) player).getInventory().getItemInHand();
					ItemStack eb = new ItemStack(Material.BOOK);
					
					if(((HumanEntity) player).getItemInHand().equals(eb) && dteam.equals(titans)){
						
						e.setCancelled(false);
						
		
					}else{
						
						e.setCancelled(true);
						
					}
					
				}
				
			}
			
			
			
		}
	
}

