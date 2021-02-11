package net.kunmc.lab.katuage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class TabComp implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String Label, String[] args){
        if (cmd.getName().equals("katuage")) {
            if (args.length <= 1) {
                return (sender.hasPermission("katuage")
                        ? Stream.of("main", "item", "inve", "inveP", "rand", "randP", "dame", "dameP")
                        : Stream.of("item", "rand", "dame"))
                        .filter(e -> e.startsWith(args[0])).collect(Collectors.toList());

            }
            else if (args.length == 2) {
                switch (args[0]){
                    case "dame":
                    case "rand":
                    case "inve":
                    case "main": {
                        return (sender.hasPermission("katuage")
                                ? Stream.of("on", "off")
                                : Stream.of("on"))
                                .filter(e -> e.startsWith(args[1])).collect(Collectors.toList());
                    }
                    case "dameP":
                    case "randP":
                    case "inveP": {
                        return (sender.hasPermission("katuage")
                                ? Stream.of("0", "25","50","75","100")
                                : Stream.of("0"))
                                .filter(e -> e.startsWith(args[1])).collect(Collectors.toList());
                    }
                    case "item":{
                        return Collections.singletonList("set");
                    }
                }
            }
        }
        return Collections.emptyList();
    }
}
