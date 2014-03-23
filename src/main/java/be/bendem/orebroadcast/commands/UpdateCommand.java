package be.bendem.orebroadcast.commands;

import be.bendem.orebroadcast.OreBroadcast;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public class UpdateCommand extends AbstractCommand {

    public UpdateCommand(OreBroadcast plugin, String permission, boolean canBeUsedFromConsole) {
        super(plugin, permission, canBeUsedFromConsole);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
    }

}
