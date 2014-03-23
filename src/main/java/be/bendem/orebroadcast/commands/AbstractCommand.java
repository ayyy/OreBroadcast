package be.bendem.orebroadcast.commands;

import be.bendem.orebroadcast.OreBroadcast;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
abstract public class AbstractCommand {

    protected final OreBroadcast plugin;
    protected final String       permission;
    protected final boolean      canBeUsedFromConsole;

    public AbstractCommand(OreBroadcast plugin, String permission, boolean canBeUsedFromConsole) {
        this.plugin = plugin;
        this.permission = permission;
        this.canBeUsedFromConsole = canBeUsedFromConsole;
    }

    abstract public void exec(CommandSender sender, List<String> args);

    public boolean canBeUsedFromConsole() {
        return canBeUsedFromConsole;
    }

    public boolean hasPermission(CommandSender sender) {
        return permission == null || sender.hasPermission(permission);
    }

    public void sendLogMessage(CommandSender recipient, String message) {
        sendLogMessage(recipient, message, ChatColor.RED);
    }

    public void sendLogMessage(CommandSender recipient, String message, ChatColor color) {
        recipient.sendMessage(ChatColor.GRAY + "[" + plugin.getName() + ": " + (color == null ? "" : color) + message + ChatColor.GRAY + "]");
    }

}
