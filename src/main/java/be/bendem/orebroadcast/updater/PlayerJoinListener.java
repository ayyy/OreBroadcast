package be.bendem.orebroadcast.updater;

import be.bendem.orebroadcast.OreBroadcast;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author bendem
 */
public class PlayerJoinListener implements Listener {

    private final OreBroadcast plugin;

    public PlayerJoinListener(OreBroadcast plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if(e.getPlayer().isOp()) {
            plugin.updater.notifyOp(e.getPlayer());
        }
    }

}
