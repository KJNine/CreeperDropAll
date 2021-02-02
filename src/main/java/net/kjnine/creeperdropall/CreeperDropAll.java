package net.kjnine.creeperdropall;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CreeperDropAll extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		
		getLogger().info("CreeperDropAll Enabled.");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("CreeperDropAll Disabled.");
	}
	
	@EventHandler
	public void onCreeperExplode(EntityExplodeEvent e) {
		if(e.getEntityType() == EntityType.CREEPER) {
			e.setYield(1.0f);
		}
	}
	
}
