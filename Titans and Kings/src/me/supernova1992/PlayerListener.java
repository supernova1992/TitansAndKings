package me.supernova1992;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
			
			/*e.setHatchingType(EntityType.ZOMBIE);
			e.setHatching(true);*/
			
		}
		
		@EventHandler
		public void onItemUse(PlayerInteractEvent e){
			
			Player player = e.getPlayer();
			
			Material hold = player.getItemInHand().getType();
			
			PlayerInventory inventory = player.getInventory();
			
			if(hold == Material.RABBIT_FOOT){
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 500, 1));
				
				inventory.removeItem(new ItemStack(Material.RABBIT_FOOT, 1));
				
			}
			if(hold == Material.BLAZE_POWDER){
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 500, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 500, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 500, 1));
				
				inventory.removeItem(new ItemStack(Material.BLAZE_POWDER, 1));

			}
			
		}
		
		@EventHandler
		public void onHit(EntityDamageByEntityEvent e){
			
			DamageCause dam = e.getCause();
			
			Scoreboard tVsK = TitansAndKings.getTVsK();
			
			if(e.getEntity() instanceof Player && tVsK.getPlayerTeam((Player) e.getEntity()).equals(tVsK.getTeam("Titans")) && !(e.getDamager() instanceof Player)){
				
				e.setCancelled(true);
			}
			
			
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
						
						damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 1));
						
						e.setCancelled(true);
					}
					
					
				}
				
				
					
				}
				
			}
			
			
			
		
	
		@EventHandler
		public void checkZTarget(EntityTargetLivingEntityEvent e){
			
			if(e.getTarget() instanceof Player){
				Player player = (Player) e.getTarget();
				
				Scoreboard tVsK = TitansAndKings.getTVsK();
				
				Team pteam = tVsK.getPlayerTeam(player);				
				
				Team titans = tVsK.getTeam("Titans");
			
				if(pteam == titans){
					
					e.setCancelled(true);
				}
			
			}
			
			
			
			
		}
		@EventHandler
		public void onFallDam(EntityDamageEvent e){
			
			Player player = (Player) e.getEntity();
			
			if(player instanceof Player && e.getCause().equals(DamageCause.FALL)){
				
				e.setCancelled(true);
				
			}
			
		}
}


