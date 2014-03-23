package be.bendem.orebroadcast.commands;

import be.bendem.orebroadcast.OreBroadcast;
import be.bendem.orebroadcast.updater.Updater;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public class CheckCommand extends AbstractCommand {

    public CheckCommand(OreBroadcast plugin, String permission, boolean canBeUsedFromConsole) {
        super(plugin, permission, canBeUsedFromConsole);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        if(plugin.updater.checkNewVersion()) {
            sendLogMessage(sender, Updater.UPDATE_MESSAGE, ChatColor.GREEN);
        } else {
            sendLogMessage(sender, "No update available...", null);
        }
    }

}
