package net.kunmc.lab.katuage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;


public class TabComp implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String Label, String[] args){
        List<String> l = new ArrayList<String>();
        if (cmd.getName().equals("katuage")) {
            if (args.length == 1) {
                l.add("main");
                l.add("item");
                l.add("inve");
                l.add("rand");
                l.add("inveP");
                l.add("randP");
            } else if (args.length == 2) {
                if (args[0].equals("main") || args[0].equals("inve") || args[0].equals("rand")) {
                    l.add("on");
                    l.add("off");
                } else if (args[0].equals("inveP") || args[0].equals("randP")) {
                    l.add("0");
                    l.add("25");
                    l.add("50");
                    l.add("75");
                    l.add("100");
                } else if (args[0].equals("item")) {
                    l.add("set");
                }
            }
        }
        return l;
    }
}
