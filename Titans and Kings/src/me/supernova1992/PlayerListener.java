package me.supernova1992;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
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
			
			try {
                File file = new File("plugins"+File.separator+"TitansAndKings"+File.separator+"users"+File.separator+e.getPlayer().getName()+".yml");
                if(!(file.exists())){
                    file.createNewFile();
                }   
            } catch (IOException event) {
                event.printStackTrace();
            }
				
			
		}
		/*@EventHandler
		public void onThrow(PlayerEggThrowEvent e) {
			
			Player player = e.getPlayer();
			
			e.setHatchingType(EntityType.ZOMBIE);
			e.setHatching(true);
			
		} */
		
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
			if(hold == Material.SKULL_ITEM){
				
				World world = player.getWorld();
				 
				Location pLoc = player.getLocation();
				
				world.spawnEntity(pLoc, EntityType.GIANT);
				
				inventory.removeItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 2));
				
				e.setCancelled(true);
				
			}
			
		}
		
		@EventHandler
		public void onHit(EntityDamageByEntityEvent e){
			
			DamageCause dam = e.getCause();
			
			Scoreboard tVsK = TitansAndKings.getTVsK();
			
			if (e.getDamager() instanceof Arrow && e.getEntity() instanceof Player){
				
				/*ItemStack arrow = new ItemStack(Material.BOW);*/
				
				Entity damager =  e.getDamager();
				
				Player damaged = (Player) e.getEntity();
				
				//Team pteam = tVsK.getPlayerTeam(player);
				
				Team dteam = tVsK.getPlayerTeam(damaged);
				
				Team titans = tVsK.getTeam("Titans");
				
				if(dteam.equals(titans) && damager instanceof Arrow){
					
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
					
					damaged.sendMessage("hit");
					
					e.setCancelled(true);
					
					
				}
				
				
			}
			
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
			if(e.getEntity() instanceof Player){
				Player player = (Player) e.getEntity();
				
				Scoreboard tVsK = TitansAndKings.getTVsK();
				
				Team pteam = tVsK.getPlayerTeam(player);
				
				Team titans = tVsK.getTeam("Titans");
				
				Team kings = tVsK.getTeam("Kings");
				
				if(player instanceof Player && e.getCause().equals(DamageCause.FALL) && (pteam == titans | pteam == kings) ){
					
					e.setCancelled(true);
					
				}
			}
		}
		
		@EventHandler
		public void onTeamRespawn(PlayerRespawnEvent e){
			
			Player player = e.getPlayer();
			
			World tvk = Bukkit.getWorld("tvk");
			
			if(player.getWorld().equals(tvk)){
			
				String pname = player.getPlayerListName();
				
				Scoreboard tVsK = TitansAndKings.getTVsK();
				
				Team pteam = tVsK.getPlayerTeam(player);
				
				Team titans = tVsK.getTeam("Titans");
				
				Team kings = tVsK.getTeam("Kings");
				
				if(pteam == null){
					
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tell " + pname + " Welcome back");
					
				}
				
				if(pteam.equals(titans)){
					
					PlayerInventory inventory = player.getInventory();
					
					inventory.addItem(new ItemStack(Material.MONSTER_EGG, 16, (short) 54));
					inventory.addItem(new ItemStack(Material.RABBIT_FOOT,6));
					inventory.addItem(new ItemStack(Material.COOKED_BEEF, 64));
					inventory.addItem(new ItemStack(Material.POTION, 16, (short) 16396));
					inventory.addItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 2));
					
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tp "+ pname + " 80 64 7");
					
				}
				if(pteam.equals(kings)){
					
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
					
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tp "+ pname + " 94 64 181");
					
				}
			}
		}
		
		@EventHandler
		public void onPoint(PlayerDeathEvent e){
			
			Player player = e.getEntity().getPlayer();
			
			World world = player.getWorld();
			
			World tvk = Bukkit.getWorld("tvk");
			
			if(world.equals(tvk)){
			
				String pname = player.getPlayerListName();
				
				//Player killer = e.getEntity().getKiller();
				
				Scoreboard tVsK = TitansAndKings.getTVsK();
				
				Team pteam = tVsK.getPlayerTeam(player);
				
				Team titans = tVsK.getTeam("Titans");
				
				Team kings = tVsK.getTeam("Kings");
				
				Score kg = TitansAndKings.getKg();
				
				Score tn = TitansAndKings.getTn();
				
				if(pteam == null){
					
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tell " + pname + " Hey, you died.");
					
				}
				
				if(pteam.equals(titans)){
					
					e.setKeepInventory(true);
					
					PlayerInventory inventory = player.getInventory();
					
					inventory.clear();
					
					/*inventory.addItem(new ItemStack(Material.MONSTER_EGG, 16, (short) 54));
					inventory.addItem(new ItemStack(Material.RABBIT_FOOT,6));
					
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tp "+ pname + " 80 64 7");*/
					
					Integer oldscore = kg.getScore();
					
					kg.setScore(oldscore + 1);
					
					e.setDeathMessage("A Titan has fallen in battle!");
					
				}
				
				if(pteam.equals(kings)){
					
					e.setKeepInventory(true);
					
					PlayerInventory inventory = player.getInventory();
					
					inventory.clear();
					
					/*inventory.addItem(new ItemStack(Material.BLAZE_POWDER, 6));
					inventory.addItem(new ItemStack(Material.BOOK, 1));
					inventory.addItem(new ItemStack(Material.ARROW, 64));
					inventory.addItem(new ItemStack(Material.BOW, 1));
					inventory.addItem(new ItemStack(Material.GOLD_HELMET, 1));
					inventory.addItem(new ItemStack(Material.GOLD_BOOTS, 1));
					inventory.addItem(new ItemStack(Material.GOLD_CHESTPLATE, 1));
					inventory.addItem(new ItemStack(Material.GOLD_LEGGINGS, 1));
					
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tp "+ pname + " 94 64 181");*/
					
					Integer oldscore = tn.getScore();
					
					tn.setScore(oldscore + 1);
					
					e.setDeathMessage("A King has fallen in battle!");
					
					
				}
				
				
				
				
				
				/*Integer tnscore = tn.getScore();
				
				Integer kgscore = kg.getScore();
				
				if(tnscore == 5 | kgscore == 5){
					
					if(tnscore == 5){
						
						
						
					}
					
				}*/
			
			}
		}
		
		@EventHandler
		public void onEntityCombust(EntityCombustEvent event){
			
			World tvk = Bukkit.getServer().getWorld("tvk");
			
			World world = event.getEntity().getWorld();
			
			if(event.getEntity() instanceof Zombie && world.equals(tvk)){
				
				event.setCancelled(true);
		 
			}
		 
		}
		
		@EventHandler
		public void onHunger(FoodLevelChangeEvent e){
			
			Player player = (Player) e.getEntity();
			
			World tvk = Bukkit.getWorld("tvk");
			
			if(player.getWorld().equals(tvk)){
			
				Scoreboard tVsK = TitansAndKings.getTVsK();
				
				Team team = tVsK.getPlayerTeam(player);
				
				Team titans = tVsK.getTeam("Titans");
				
				Team kings = tVsK.getTeam("Kings");
				
				if(team.equals(titans) | team.equals(kings)){
				e.setCancelled(true);
				}
			}
		}
}


