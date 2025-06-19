package dev.cattyn.microkits.commands;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.command.CommandSender;

@Command("kit")
public class KitCommand {

    @Subcommand("save")
    public static void save(CommandSender sender, @AStringArgument String name) {
        
    }

}
