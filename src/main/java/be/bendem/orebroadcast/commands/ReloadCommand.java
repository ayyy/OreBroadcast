package be.bendem.orebroadcast.commands;

import be.bendem.orebroadcast.OreBroadcast;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public class ReloadCommand extends AbstractCommand {

    public ReloadCommand(OreBroadcast plugin, String permission, boolean canBeUsedFromConsole) {
        super(plugin, permission, canBeUsedFromConsole);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        plugin.reloadConfig();
        plugin.loadBlocksToBroadcastList();
        sender.sendMessage("Config reloaded...");
    }

}
