package be.bendem.orebroadcast.updater;

import be.bendem.orebroadcast.OreBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author bendem
 */
public class Updater {

    public static final int    PROJECT_ID     = 72299;
    public static final String API_URL        = "https://api.curseforge.com/servermods/files?projectIds=";
    public static final String UPDATE_MESSAGE = ChatColor.GOLD + "A new version is available, type "
            + ChatColor.BOLD + "/ob update" + ChatColor.RESET + ChatColor.GOLD + " to download it";

    private final OreBroadcast plugin;
    private final String       pluginName;
    private final String       currentVersion;
    private final Channel      updateChannel;

    public Updater(OreBroadcast plugin) {
        this.plugin = plugin;
        this.pluginName = plugin.getName();
        this.currentVersion = plugin.getDescription().getVersion();
        this.updateChannel = plugin.getConfig().getBoolean("use-beta", false) ? Channel.Beta : Channel.Release;
    }

    public boolean checkNewVersion() {
        return false;
    }

    public boolean update() {
        return false;
    }

    public void notifyConsole() {
        Bukkit.getConsoleSender().sendMessage(UPDATE_MESSAGE);
    }

    public void notifyOps() {
        for(OfflinePlayer op : Bukkit.getOperators()) {
            if(op.isOnline()) {
                notifyOp(op.getPlayer());
            }
        }
    }

    public void notifyOp(Player op) {
        op.sendMessage(UPDATE_MESSAGE);
    }

    public Channel getChannel(String version) {
        return version.contains("BETA") ? Channel.Beta : Channel.Release;
    }

    public Channel getChannel() {
        return getChannel(currentVersion);
    }

    public String getVersionNumber(String version) {
        return version.replace("-BETA", "").replace(pluginName + "-", "");
    }

    public String getVersionNumber() {
        return getVersionNumber(currentVersion);
    }

    enum Channel {
        Beta, Release
    }

}
