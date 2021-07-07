package net.kjnine.creeperdropall;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

public class CreeperDropAll extends JavaPlugin implements Listener {

	private CoreProtectAPI coApi;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		
		coApi = getCoreProtect();

		getLogger().info("CreeperDropAll Enabled.");
	}

	private CoreProtectAPI getCoreProtect() {
		Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

		if (plugin == null || !(plugin instanceof CoreProtect)) {
			getLogger().severe("CoreProtect is not installed. Functionality will be limited.");
			return null;
		}
		
		CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
		
		if (CoreProtect.isEnabled() == false) {
			getLogger().severe("CoreProtect is not enabled. Functionality will be limited.");
			return null;
		}

		if (CoreProtect.APIVersion() < 6) {
			getLogger().severe("CoreProtect outdated version (API v6 or higher required), Functionality will be limited.");
			return null;
		}
		getLogger().info("CoreProtect hooked properly. Creeper Explosions will be logged.");
		return CoreProtect;
	}

	@Override
	public void onDisable() {
		getLogger().info("CreeperDropAll Disabled.");
	}

	@EventHandler
	public void onCreeperExplode(EntityExplodeEvent e) {
		if (e.getEntityType() == EntityType.CREEPER) {
			
			Creeper c = (Creeper) e.getEntity();
			LivingEntity suspect = c.getTarget();
			
			e.blockList().removeIf(b -> b.getType() == Material.CHEST || b.getType() == Material.TRAPPED_CHEST);
			
			if(coApi != null) {

				if(suspect != null) {
					for (Block b : e.blockList()) {
						coApi.logRemoval("#creeper[" + suspect.getName() + "]", b.getLocation(), b.getType(), b.getBlockData());
					}
				} else {
					e.blockList().clear();
					e.setCancelled(true);
				}
			}
			
			e.setYield(1.0f);
		}
	}

}
