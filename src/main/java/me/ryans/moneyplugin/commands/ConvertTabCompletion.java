package me.ryans.moneyplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ConvertTabCompletion implements TabCompleter {


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
       if(command.getName().equalsIgnoreCase("convert")) {
           if (args.length == 1) {
               List<String> arguments = new ArrayList<>();
               arguments.add("1");
               arguments.add("5");
               arguments.add("10");
               arguments.add("20");
               arguments.add("50");
               arguments.add("100");
               return arguments;
           }
           List<String> ag = new ArrayList<>();
           {
               for (int i = 2; i < args.length; i++) {
                   ag.add(null);
               }
               return ag;
           }
       }
       if(command.getName().equalsIgnoreCase("exchange")) {
           if(args.length > 0) {
               List<String> ag2 = new ArrayList<>();
               for (int i = 1; i < args.length; i++) {
                   ag2.add(null);
               }
               return ag2;
           }
       }
       return null;
    }
}
