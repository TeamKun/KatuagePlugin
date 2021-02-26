package net.kunmc.lab.rulebookplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class RuleBookPlugin extends JavaPlugin implements Listener , TabCompleter {

    public boolean setting = false;
    public List<ItemStack> Books = new ArrayList<>();
    public ItemStack RuleBook ;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setBooks(Books);
        this.getCommand("rulebook").setExecutor(this);
        this.getCommand("rulebook").setTabCompleter(this);
        Bukkit.getPluginManager().registerEvents(this, this);
        getServer().getLogger().info(ChatColor.AQUA+"RuleBookPlugin by yanaaaaa");
    }


    public void setBooks(List<ItemStack> s){
        if(getConfig().getItemStack("Book1").getType() != Material.STICK ) {
            s.add(getConfig().getItemStack("Book1"));
        }if(getConfig().getItemStack("Book2").getType() != Material.STICK) {
            s.add(getConfig().getItemStack("Book2"));
        }if(getConfig().getItemStack("Book3").getType() != Material.STICK) {
            s.add(getConfig().getItemStack("Book3"));
        }if(getConfig().getItemStack("Book4").getType() != Material.STICK) {
            s.add(getConfig().getItemStack("Book4"));
        }if(getConfig().getItemStack("Book5").getType() != Material.STICK) {
            s.add(getConfig().getItemStack("Book5"));
        }if(getConfig().getItemStack("Book6").getType() != Material.STICK) {
            s.add(getConfig().getItemStack("Book6"));
        }if(getConfig().getItemStack("Book7").getType() != Material.STICK) {
            s.add(getConfig().getItemStack("Book7"));
        }if(getConfig().getItemStack("Book8").getType() != Material.STICK) {
            s.add(getConfig().getItemStack("Book8"));
        }if(getConfig().getItemStack("Book9").getType() != Material.STICK) {
            s.add(getConfig().getItemStack("Book9"));
        }if(getConfig().getItemStack("Book10").getType() != Material.STICK) {
            s.add(getConfig().getItemStack("Book10"));
        } if(getConfig().getItemStack("Book11").getType() != Material.STICK) {
            s.add(getConfig().getItemStack("Book11"));
        }if(getConfig().getItemStack("Book12").getType() != Material.STICK) {
            s.add(getConfig().getItemStack("Book12"));
        }
        s.stream().distinct();
    }

    public boolean checkList(List<ItemStack> s,ItemStack item){
        boolean check = true;
        for(int i = 0;i<s.size();i++){
            BookMeta book1 = (BookMeta)s.get(i).getItemMeta();
            BookMeta book2 = (BookMeta)item.getItemMeta();
            if(book1.getTitle().equals(book2.getTitle())){
                check = false;
            }
        }
        return check;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
        if (cmd.getName().equals("rulebook")) {
            if (!(sender.isOp())) {
                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:このコマンドの実行にはOP権限が必要だよ~！");
            } else if (args.length > 3) {
                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:引数が多いよ~！");
            } else if (args.length < 1) {
                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:引数が少ないよ~！");
            } else {
                if(args.length==1) {
                    Player p = (Player) sender;
                    if (args[0].equals("addlist")) {
                        if (p.getItemInHand().getType() == null) {
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:記述した本を持ってコマンドを実行してください！");
                        } else if (p.getItemInHand().getType() == Material.WRITTEN_BOOK) {
                            if (checkList(Books, p.getItemInHand()) == false) {
                                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:リストに同じ名前の本が存在します。本の名前を変更してください！");
                            } else {
                                    Books.add(p.getItemInHand());
                                    sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]:リストに本を追加しました！");
                            }
                        } else {
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:記述した本を持ってコマンドを実行してください！");
                        }
                    } else if (args[0].equals("listinfo")) {
                        if (Books.size() == 0) {
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:リストに設定されている本はありません");
                        } else {
                            List<String> BookName = new ArrayList<>();
                            for (int i = 0; i < Books.size(); i++) {
                                BookMeta book = (BookMeta)Books.get(i).getItemMeta();
                                BookName.add(book.getTitle());
                            }
                            sender.sendMessage(ChatColor.GREEN + "==========本の一覧===========");
                            sender.sendMessage(ChatColor.AQUA + String.valueOf(BookName));
                        }
                    }else if(args[0].equals("newbook")){
                        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
                        p.getLocation().getWorld().dropItem(p.getLocation(), item);
                    } else{
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:引数が違うよ~！");
                    }
                }else if(args.length==2){
                    if(args[0].equals("deleatlist")){
                        if(args[1].equals("all")){
                            Books=new ArrayList<>();
                            sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]:リストをすべて削除しました！");
                        }else {
                            List<String> BookName = new ArrayList<>();
                            for (int i = 0; i < Books.size(); i++) {
                                BookMeta book = (BookMeta)Books.get(i).getItemMeta();
                                BookName.add(book.getTitle());
                            }
                            if (Books.size() >= 12) {
                                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:リストの上限数は12冊です。12冊未満になるように登録された本を削除してください！");
                            } else {
                                if (BookName.contains(args[1])) {
                                    int n = BookName.indexOf(args[1]);
                                    Books.remove(n);
                                    sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]:リストから" + args[1] + "を削除しました！");
                                } else {
                                    sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:リストに" + args[1] + "は存在しません！");
                                }
                            }
                        }
                    }else if(args[0].equals("joinset")){
                        List<String> BookName = new ArrayList<>();
                        for (int i = 0; i < Books.size(); i++) {
                            BookMeta book = (BookMeta)Books.get(i).getItemMeta();
                            BookName.add(book.getTitle());
                        }
                        if(BookName.contains(args[1])){
                            int n = BookName.indexOf(args[1]);
                            RuleBook = Books.get(n);
                            sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]:RuleBookを設定できました！");
                        }else{
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:リストに" + args[1] + "は存在しません！");
                        }
                    }else if(args[0].equals("joinread")) {
                        if (args[1].equals("on") || args[1].equals("off")) {
                            if (args[1].equals("on")) {
                                if (RuleBook == null) {
                                    sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:RuleBookがセットされていません。記述した本を持って/rulebook joinbooksetコマンドを実行してください！");
                                } else {
                                    setting = true;
                                    sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]:ログイン時の本のオープンを" + args[1] + "にしました！");
                                }
                            } else {
                                setting = false;
                                sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]:ログイン時の本のオープンを" + args[1] + "にしました！");
                            }
                        }
                    }else{
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:引数が違うよ~！");
                    }
                }else{
                    if(args[0].equals("read")){
                        List<Entity> player = Bukkit.selectEntities(sender,args[2]);
                        List<String> BookName = new ArrayList<>();
                        for (int i = 0; i < Books.size(); i++) {
                            BookMeta book = (BookMeta)Books.get(i).getItemMeta();
                            BookName.add(book.getTitle());
                        }
                        if(player.size()<=0){
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:対象のプレイヤーは存在しません！");
                        }else{
                            if(BookName.contains(args[1])){
                                int n = BookName.indexOf(args[1]);
                                for(int j=0;j<player.size();j++){
                                    if(player.get(j) instanceof Player){
                                        Player p = (Player) player.get(j);
                                        p.openBook(Books.get(n));
                                    }
                                }
                            }else{
                                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:リストに"+args[1]+"は存在しません！");
                            }
                        }
                    }else if (args[0].equals("givebook")){
                        List<Entity> player = Bukkit.selectEntities(sender,args[2]);
                        List<String> BookName = new ArrayList<>();
                        for (int i = 0; i < Books.size(); i++) {
                            BookMeta book = (BookMeta)Books.get(i).getItemMeta();
                            BookName.add(book.getTitle());
                        }
                        if(player.size()<=0){
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:対象のプレイヤーは存在しません！");
                        }else{
                            if(BookName.contains(args[1])){
                                int n = BookName.indexOf(args[1]);
                                for(int j=0;j<player.size();j++){
                                    if(player.get(j) instanceof Player){
                                        Player p = (Player) player.get(j);
                                        ItemStack item = Books.get(n);
                                        p.getLocation().getWorld().dropItem(p.getLocation(), item);
                                    }
                                }
                            }else{
                                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:リストに"+args[1]+"は存在しません！");
                            }
                        }
                    }else{
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:引数が違うよ~！");
                    }
                }
            }
        }
        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        if(RuleBook == null){
        }else {
            if (setting == true) {
                p.openBook(RuleBook);
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String Label, String[] args){
        List<String> BookName = new ArrayList<>();
        for (int i = 0; i < Books.size(); i++) {
            BookMeta book = (BookMeta)Books.get(i).getItemMeta();
            BookName.add(book.getTitle());
        }
        if (cmd.getName().equals("rulebook")) {
            if (args.length == 1) {
                return (sender.hasPermission("rulebook")
                        ? Stream.of("addlist", "listinfo", "deleatlist", "joinbook", "joinread", "read", "givebook", "newbook")
                        : Stream.of("joinread"))
                        .filter(e -> e.startsWith(args[0])).collect(Collectors.toList());
            } else if (args.length == 2) {
                switch (args[0]) {
                    case "deleatlist": {
                        BookName.add("all");
                        return BookName;
                    }
                    case "givebook":
                    case "read":
                    case "joinbook": {
                        return BookName;
                    }
                    case "joinread": {
                        return (sender.hasPermission("rulebook")
                                ? Stream.of("on", "off")
                                : Stream.of("on"))
                                .filter(e -> e.startsWith(args[1])).collect(Collectors.toList());
                    }
                }
            } else if (args.length == 3) {
                ArrayList<String> PlayerName = new ArrayList<String>();
                getServer().getOnlinePlayers().forEach(player ->{
                   PlayerName.add(player.getName());
                });
                PlayerName.add("@a");
                PlayerName.add("@p");
                PlayerName.add("@r");
                PlayerName.add("@s");
                PlayerName.add("@e");
                switch (args[0]) {
                    case "givebook":
                    case "read": {
                        return PlayerName;
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void onDisable() {
        int n = Books.size();
        ItemStack M = new ItemStack(Material.STICK);
        reloadConfig();
        FileConfiguration config = getConfig();
        if(n==0){
            config.set("Book1",M);
            config.set("Book2",M);
            config.set("Book3",M);
            config.set("Book4",M);
            config.set("Book5",M);
            config.set("Book6",M);
            config.set("Book7",M);
            config.set("Book8",M);
            config.set("Book9",M);
            config.set("Book10",M);
            config.set("Book11",M);
            config.set("Book12",M);
        } else if(n==1){
            config.set("Book1",Books.get(0));
            config.set("Book2",M);
            config.set("Book3",M);
            config.set("Book4",M);
            config.set("Book5",M);
            config.set("Book6",M);
            config.set("Book7",M);
            config.set("Book8",M);
            config.set("Book9",M);
            config.set("Book10",M);
            config.set("Book11",M);
            config.set("Book12",M);
        }
        else if(n==2){
            config.set("Book1",Books.get(0));
            config.set("Book2",Books.get(1));
            config.set("Book3",M);
            config.set("Book4",M);
            config.set("Book5",M);
            config.set("Book6",M);
            config.set("Book7",M);
            config.set("Book8",M);
            config.set("Book9",M);
            config.set("Book10",M);
            config.set("Book11",M);
            config.set("Book12",M);
        }
        else if(n==3){
            config.set("Book1",Books.get(0));
            config.set("Book2",Books.get(1));
            config.set("Book3",Books.get(2));
            config.set("Book4",M);
            config.set("Book5",M);
            config.set("Book6",M);
            config.set("Book7",M);
            config.set("Book8",M);
            config.set("Book9",M);
            config.set("Book10",M);
            config.set("Book11",M);
            config.set("Book12",M);
        }
        else if(n==4){
            config.set("Book1",Books.get(0));
            config.set("Book2",Books.get(1));
            config.set("Book3",Books.get(2));
            config.set("Book4",Books.get(3));
            config.set("Book5",M);
            config.set("Book6",M);
            config.set("Book7",M);
            config.set("Book8",M);
            config.set("Book9",M);
            config.set("Book10",M);
            config.set("Book11",M);
            config.set("Book12",M);
        }
        else if(n==5){
            config.set("Book1",Books.get(0));
            config.set("Book2",Books.get(1));
            config.set("Book3",Books.get(2));
            config.set("Book4",Books.get(3));
            config.set("Book5",Books.get(4));
            config.set("Book6",M);
            config.set("Book7",M);
            config.set("Book8",M);
            config.set("Book9",M);
            config.set("Book10",M);
            config.set("Book11",M);
            config.set("Book12",M);
        }
        else if(n==6){
            config.set("Book1",Books.get(0));
            config.set("Book2",Books.get(1));
            config.set("Book3",Books.get(2));
            config.set("Book4",Books.get(3));
            config.set("Book5",Books.get(4));
            config.set("Book6",Books.get(5));
            config.set("Book7",M);
            config.set("Book8",M);
            config.set("Book9",M);
            config.set("Book10",M);
            config.set("Book11",M);
            config.set("Book12",M);
        }
        else if(n==7){
            config.set("Book1",Books.get(0));
            config.set("Book2",Books.get(1));
            config.set("Book3",Books.get(2));
            config.set("Book4",Books.get(3));
            config.set("Book5",Books.get(4));
            config.set("Book6",Books.get(5));
            config.set("Book7",Books.get(6));
            config.set("Book8",M);
            config.set("Book9",M);
            config.set("Book10",M);
            config.set("Book11",M);
            config.set("Book12",M);
        }
        else if(n==8){
            config.set("Book1",Books.get(0));
            config.set("Book2",Books.get(1));
            config.set("Book3",Books.get(2));
            config.set("Book4",Books.get(3));
            config.set("Book5",Books.get(4));
            config.set("Book6",Books.get(5));
            config.set("Book7",Books.get(6));
            config.set("Book8",Books.get(7));
            config.set("Book9",M);
            config.set("Book10",M);
            config.set("Book11",M);
            config.set("Book12",M);
        }
        else if(n==9){
            config.set("Book1",Books.get(0));
            config.set("Book2",Books.get(1));
            config.set("Book3",Books.get(2));
            config.set("Book4",Books.get(3));
            config.set("Book5",Books.get(4));
            config.set("Book6",Books.get(5));
            config.set("Book7",Books.get(6));
            config.set("Book8",Books.get(7));
            config.set("Book9",Books.get(8));
            config.set("Book10",M);
            config.set("Book11",M);
            config.set("Book12",M);
        }
        else if(n==10){
            config.set("Book1",Books.get(0));
            config.set("Book2",Books.get(1));
            config.set("Book3",Books.get(2));
            config.set("Book4",Books.get(3));
            config.set("Book5",Books.get(4));
            config.set("Book6",Books.get(5));
            config.set("Book7",Books.get(6));
            config.set("Book8",Books.get(7));
            config.set("Book9",Books.get(8));
            config.set("Book10",Books.get(9));
            config.set("Book11",M);
            config.set("Book12",M);
        }
        else if(n==11){
            config.set("Book1",Books.get(0));
            config.set("Book2",Books.get(1));
            config.set("Book3",Books.get(2));
            config.set("Book4",Books.get(3));
            config.set("Book5",Books.get(4));
            config.set("Book6",Books.get(5));
            config.set("Book7",Books.get(6));
            config.set("Book8",Books.get(7));
            config.set("Book9",Books.get(8));
            config.set("Book10",Books.get(9));
            config.set("Book11",Books.get(10));
            config.set("Book12",M);
        }
        else{
            config.set("Book1",Books.get(0));
            config.set("Book2",Books.get(1));
            config.set("Book3",Books.get(2));
            config.set("Book4",Books.get(3));
            config.set("Book5",Books.get(4));
            config.set("Book6",Books.get(5));
            config.set("Book7",Books.get(6));
            config.set("Book8",Books.get(7));
            config.set("Book9",Books.get(8));
            config.set("Book10",Books.get(9));
            config.set("Book11",Books.get(10));
            config.set("Book12",Books.get(11));
        }
        saveConfig();
    }

}
