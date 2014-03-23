package be.bendem.orebroadcast;

import be.bendem.orebroadcast.commands.CommandHandler;
import be.bendem.orebroadcast.updater.PlayerJoinListener;
import be.bendem.orebroadcast.updater.Updater;
import be.bendem.orebroadcast.updater.Version;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

/**
 * OreBroadcast for Bukkit
 *
 * @author bendem
 */
public class OreBroadcast extends JavaPlugin {

    public Logger logger;
    public Updater updater;
    public HashSet<Block> broadcastBlacklist = new HashSet<>();
    public ArrayList<String> blocksToBroadcast;
    public boolean isNewVersionAvailable = false;

    @Override
    public void onEnable() {
        logger = getLogger();

        saveDefaultConfig();
        loadBlocksToBroadcastList();
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getCommand("ob").setExecutor(new CommandHandler(this));

        updater = new Updater(this);
        if(getConfig().getBoolean("check-version-on-startup", true)) {
            if(updater.checkNewVersion()) {
                updater.notifyConsole();
                isNewVersionAvailable = true;
                if(getConfig().getBoolean("notify-op", true)) {
                    updater.notifyOps();
                    getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
                }
            }
        }

        logger.fine(getDescription().getName() + " version " + getDescription().getVersion() + " is enabled!");
    }

    @Override
    public void onDisable() {
        logger.fine(getDescription().getName() + " want you to have a nice day ;-)");
    }

    public void loadBlocksToBroadcastList() {
        // Create the list of blocks to broadcast from the file
        blocksToBroadcast = new ArrayList<>(getConfig().getStringList("ores"));
        for (int i = 0; i < blocksToBroadcast.size(); ++i) {
            blocksToBroadcast.set(i, blocksToBroadcast.get(i).toUpperCase() + "_ORE");
            // Handle glowing redstone ore (id 74) and redstone ore (id 73)
            if(blocksToBroadcast.get(i).equals("REDSTONE_ORE")) {
                blocksToBroadcast.add("GLOWING_REDSTONE");
            }
        }
    }

}
