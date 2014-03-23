package be.bendem.orebroadcast.commands;

import be.bendem.orebroadcast.OreBroadcast;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Arrays;
import java.util.HashMap;

public class CommandHandler implements CommandExecutor {

    private final HashMap<String, AbstractCommand> commandRegistry;

    public CommandHandler(OreBroadcast plugin) {
        commandRegistry = new HashMap<>();
        commandRegistry.put("reload", new ReloadCommand(plugin, "ob.reload", true));
        commandRegistry.put("check", new CheckCommand(plugin, "ob.update.check", true));
        commandRegistry.put("update", new UpdateCommand(plugin, "ob.update.update", true));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0 || !command.getName().equalsIgnoreCase("ob")) {
            return false;
        }
        String commandName = args[0].toLowerCase();
        if(commandRegistry.containsKey(commandName)) {
            final AbstractCommand c = commandRegistry.get(commandName);

            if(!c.hasPermission(sender)) {
                c.sendLogMessage(sender, "You don't have the permission to use that command.");
            } else if (sender instanceof ConsoleCommandSender && !c.canBeUsedFromConsole()) {
                c.sendLogMessage(sender, "You can't use this command from the console.");
            } else {
                c.exec(sender, Arrays.asList(args).subList(1, args.length));
            }

            return true;
        }
        return false;
    }

}
