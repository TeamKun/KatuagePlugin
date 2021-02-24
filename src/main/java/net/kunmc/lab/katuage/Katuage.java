package net.kunmc.lab.katuage;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.kunmc.lab.moneycraft.MoneyCraft;
import net.kunmc.lab.moneycraft.api.MoneyCraftAPI;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.*;

public class Katuage extends JavaPlugin implements Listener {

    static private Random rand = new Random();
    static private List<Player> Players = new ArrayList<Player>();
    static private List<Location> loc1 = new ArrayList<Location>();
    static private List<Location> loc2 = new ArrayList<Location>();
    static private List<Location> loc3 = new ArrayList<Location>();
    static private List<Location> loc4 = new ArrayList<Location>();
    static private boolean money = false;
    static private boolean check = false;
    Timer timer = new Timer();

    @Override
    public void onEnable() {
        this.getCommand("katuage").setExecutor(this);
        this.getCommand("katuage").setTabCompleter(new TabComp());
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new ItemGet(), this);
        getLogger().info(ChatColor.AQUA + "KatuagePlugin by yanaaaaa");
        saveDefaultConfig();
        MoneyCheck();
    }

    //コマンド処理
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
        MoneyCheck();
        if (cmd.getName().equals("katuage")) {
            if (!(sender.isOp())) {
                sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:このコマンドの実行にはOP権限が必要だよ~！");
            } else if (args.length > 2) {
                sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が多いよ~！");
            } else if (args.length < 2) {
                sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が少ないよ~！");
            } else {
                reloadConfig();
                FileConfiguration config = getConfig();
                //プラグイン本体のon,off
                if (args[0].equals("main")) {
                    if (args[1].equals("on")) {
                        config.set("PluginOnOff", true);
                        sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:KatuagePluginをONにしました！");
                    } else if (args[1].equals("off")) {
                        config.set("PluginOnOff", false);
                        sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:KatuagePluginをOFFにしました！");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                    }
                }
                //ランダムドロップのon,off
                else if (args[0].equals("rand")) {
                    if (args[1].equals("on")) {
                        config.set("RandomDropOnOff", true);
                        sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:ランダムドロップをONにしました！");
                    } else if (args[1].equals("off")) {
                        config.set("RandomDropOnOff", false);
                        sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:ランダムドロップをOFFにしました！");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                    }
                }
                //ランダムドロップの確立設定
                else if (args[0].equals("randP")) {
                    if (Integer.parseInt(args[1]) <= 100 && Integer.parseInt(args[1]) >= 0) {
                        config.set("RandomDropProbability", args[1]);
                        sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:ランダムドロップの確立を" + args[1] + "%にしました！");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                    }
                }
                //インベントリドロップのon,off
                else if (args[0].equals("inve")) {
                    if (args[1].equals("on")) {
                        config.set("InventoryDropOnOff", true);
                        sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:インベントリドロップをONにしました！");
                    } else if (args[1].equals("off")) {
                        config.set("InventoryDropOnOff", false);
                        sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:インベントリドロップをOFFにしました！");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                    }
                }
                //インベントリドロップの確立設定
                else if (args[0].equals("inveP")) {
                    if (Integer.parseInt(args[1]) <= 100 && Integer.parseInt(args[1]) >= 0) {
                        config.set("InventoryDropProbability", args[1]);
                        sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:インベントリドロップの確立を" + args[1] + "%にしました！");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                    }
                }
                //ダメージドロップのon,off
                else if (args[0].equals("dame")) {
                    if (args[1].equals("on")) {
                        config.set("DamageDropOnOff", true);
                        sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:ダメージドロップをONにしました！");
                    } else if (args[1].equals("off")) {
                        config.set("DamageDropOnOff", false);
                        sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:ダメージドロップをOFFにしました！");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                    }
                }
                //ダメージドロップの確率
                else if (args[0].equals("dameP")) {
                    if (Integer.parseInt(args[1]) <= 100 && Integer.parseInt(args[1]) >= 0) {
                        config.set("DamageDropProbability", args[1]);
                        sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:ダメージドロップの確立を" + args[1] + "%にしました！");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                    }
                }
                //マネードロップのon,off
                else if (args[0].equals("money")) {
                    if(money==false){
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:MoneyCraftが入っていないためこのコマンドは実行できないです~！");
                    }else {
                        if (args[1].equals("on")) {
                            config.set("MoneyDropOnOff", true);
                            sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:マネードロップをONにしました！");
                        } else if (args[1].equals("off")) {
                            config.set("MoneyDropOnOff", false);
                            sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:マネードロップをOFFにしました！");
                        } else {
                            sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                        }
                    }
                }
                //マネードロップの確率
                else if (args[0].equals("moneyP")) {
                    if(money==false){
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:MoneyCraftが入っていないためこのコマンドは実行できないです~！");
                    }else {
                        if (Integer.parseInt(args[1]) <= 100 && Integer.parseInt(args[1]) >= 0) {
                            config.set("MoneyDropProbability", args[1]);
                            sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:マネードロップの確立を" + args[1] + "%にしました！");
                        } else {
                            sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                        }
                    }
                }
                //マネードロップの金額
                else if (args[0].equals("moneyT")) {
                    if(money==false){
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:MoneyCraftが入っていないためこのコマンドは実行できないです~！");
                    }else {
                        if (Integer.parseInt(args[1]) == 100 || Integer.parseInt(args[1]) == 1000) {
                            config.set("MoneyDropType", args[1]);
                            sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:マネードロップの貨幣・紙幣を" + args[1] + "円にしました！");
                        } else {
                            sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！(100 or 1000)");
                        }
                    }
                }
                //マネードロップの枚数
                else if (args[0].equals("moneyA")) {
                    if(money==false){
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:MoneyCraftが入っていないためこのコマンドは実行できないです~！");
                    }else {
                        if (Integer.parseInt(args[1]) >=1 ) {
                            config.set("MoneyDropAmount", args[1]);
                            sender.sendMessage(ChatColor.GREEN + "[KatuagePlugin]:マネードロップの貨幣・紙幣の枚数を" + args[1] + "枚にしました！");
                        } else {
                            sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！(1以上の整数)");
                        }
                    }
                }
                //ランダムドロップのアイテム設定
                else if (args[0].equals("item") && args[1].equals("set")) {
                    Player p = (Player) sender;
                    Inventory inv = Bukkit.createInventory(null, 18, "ItemSetGUI");

                    ItemStack item1 = config.getItemStack("Item1");
                    ItemStack item2 = config.getItemStack("Item2");
                    ItemStack item3 = config.getItemStack("Item3");
                    ItemStack item4 = config.getItemStack("Item4");
                    ItemStack item5 = config.getItemStack("Item5");
                    ItemStack item6 = config.getItemStack("Item6");
                    ItemStack item7 = config.getItemStack("Item7");
                    ItemStack item8 = config.getItemStack("Item8");
                    ItemStack item9 = config.getItemStack("Item9");
                    ItemStack item10 = config.getItemStack("Item10");
                    ItemStack item11 = config.getItemStack("Item11");
                    ItemStack item12 = config.getItemStack("Item12");
                    ItemStack item13 = config.getItemStack("Item13");
                    ItemStack item14 = config.getItemStack("Item14");
                    ItemStack item15 = config.getItemStack("Item15");
                    ItemStack item16 = config.getItemStack("Item16");
                    ItemStack item17 = config.getItemStack("Item17");
                    ItemStack item18 = config.getItemStack("Item18");

                    inv.setItem(0, item1);
                    inv.setItem(1, item2);
                    inv.setItem(2, item3);
                    inv.setItem(3, item4);
                    inv.setItem(4, item5);
                    inv.setItem(5, item6);
                    inv.setItem(6, item7);
                    inv.setItem(7, item8);
                    inv.setItem(8, item9);
                    inv.setItem(9, item10);
                    inv.setItem(10, item11);
                    inv.setItem(11, item12);
                    inv.setItem(12, item13);
                    inv.setItem(13, item14);
                    inv.setItem(14, item15);
                    inv.setItem(15, item16);
                    inv.setItem(16, item17);
                    inv.setItem(17, item18);

                    p.openInventory(inv);
                }
                //その他の処理
                else {
                    sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                }
            }
        }

        saveConfig();
        return true;
    }

    static public Random getRandom() {
        return rand;
    }

    //GUIインベントリ保存処理
    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        MoneyCheck();
        Inventory inv = event.getInventory();
        reloadConfig();
        FileConfiguration config = getConfig();
        if (inv.getType().name().equalsIgnoreCase("CHEST") && inv.getSize() == 18) {
            config.set("Item1", inv.getItem(0));
            config.set("Item2", inv.getItem(1));
            config.set("Item3", inv.getItem(2));
            config.set("Item4", inv.getItem(3));
            config.set("Item5", inv.getItem(4));
            config.set("Item6", inv.getItem(5));
            config.set("Item7", inv.getItem(6));
            config.set("Item8", inv.getItem(7));
            config.set("Item9", inv.getItem(8));
            config.set("Item10", inv.getItem(9));
            config.set("Item11", inv.getItem(10));
            config.set("Item12", inv.getItem(11));
            config.set("Item13", inv.getItem(12));
            config.set("Item14", inv.getItem(13));
            config.set("Item15", inv.getItem(14));
            config.set("Item16", inv.getItem(15));
            config.set("Item17", inv.getItem(16));
            config.set("Item18", inv.getItem(17));
        }
        saveConfig();
    }

    //ドロップの処理
    @EventHandler
    public void onJumped(PlayerJumpEvent event) {
        MoneyCheck();
        Player p = event.getPlayer();
        Location loc = p.getLocation();
        reloadConfig();
        FileConfiguration config = getConfig();
        int numP = rand.nextInt(100);//アイテムドロップの確立
        if (LocationComparison(p, loc) == true) {
            if (config.getBoolean("PluginOnOff") == true && config.getBoolean("InventoryDropOnOff") == true && ItemCheck(p) == true) {
                int numI = rand.nextInt(40);//インベントリアイテムの番号指定
                int numA = Integer.parseInt(config.getString("InventoryDropProbability"));
                ItemStack item = null;
                while (item == null) {
                    item = p.getInventory().getItem(numI);
                    if (item == null) {
                        numI = rand.nextInt(40);
                    }
                }
                if ((numP <= numA) && (p.getInventory().getItem(numI) != null)) {
                    Entity ie = loc.getWorld().dropItem(loc, item);
                    p.getInventory().setItem(numI, null);
                    ItemProcessing(p, ie, loc);

                }
            }
            if (config.getBoolean("PluginOnOff") == true && config.getBoolean("RandomDropOnOff") == true) {
                int numI = 1 + rand.nextInt(17);//インベントリアイテムの番号指定
                int numA = Integer.parseInt(config.getString("RandomDropProbability"));
                ItemStack item = config.getItemStack("Item" + numI);
                if ((numP <= numA)) {
                    Entity ie = loc.getWorld().dropItem(loc, item);
                    ItemProcessing(p, ie, loc);
                }
            }
            if(money==true&&config.getBoolean("MoneyDropOnOff") == true){
                int numA = Integer.parseInt(config.getString("MoneyDropProbability"));
                int type = Integer.parseInt(config.getString("MoneyDropType"));
                int amount = Integer.parseInt(config.getString("MoneyDropAmount"));
                int ans = amount*type;
                if (numP <= numA && MoneyCraft.getEconomy().getBalance(p) >= ans){
                    if(type == 100 || type ==1000){
                        Entity ie = MoneyCraftAPI.dropMoney(p,loc,type,amount,true,true);
                        MoneyItemProcessing(p,ie,loc);
                    }
                }
            }
        }
    }

    //ダメージドロップの処理
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        MoneyCheck();
        if (!(event.getEntity() instanceof Player)){return;}
        if(!(event.getDamager() instanceof Player)){return;}
        Player p = (Player) event.getEntity();
        Player e = (Player) event.getDamager();
        Location loc = p.getLocation();
        reloadConfig();
        int numP = rand.nextInt(100);//アイテムドロップの確立
        FileConfiguration config = getConfig();
        if (Visibilty(p, e) == true&&e.isSneaking()==true&&config.getBoolean("PluginOnOff") == true) {
            if (config.getBoolean("DamageDropOnOff") == true && ItemCheck(p) == true) {
                int numI = rand.nextInt(40);//インベントリアイテムの番号指定
                int numA = Integer.parseInt(config.getString("DamageDropProbability"));
                ItemStack item = null;
                while (item == null) {
                    item = p.getInventory().getItem(numI);
                    if (item == null) {
                        numI = rand.nextInt(40);
                    }
                }
                if ((numP <= numA) && (p.getInventory().getItem(numI) != null)) {
                    Entity ie = loc.getWorld().dropItem(loc, item);
                    p.getInventory().setItem(numI, null);
                    ItemProcessing(p, ie, loc);
                }
            }
            if(money==true&&config.getBoolean("MoneyDropOnOff") == true){
                int numA = Integer.parseInt(config.getString("MoneyDropProbability"));
                int type = Integer.parseInt(config.getString("MoneyDropType"));
                int amount = Integer.parseInt(config.getString("MoneyDropAmount"));
                int ans = type * amount;
                if (numP <= numA){
                    if(type == 100 || type ==1000 && MoneyCraft.getEconomy().getBalance(p) >= ans){
                        Entity ie = MoneyCraftAPI.dropMoney(p,loc,type,amount,true,true);
                        MoneyItemProcessing(p,ie,loc);
                    }
                }
            }
        }
    }

    //マネークラフト連携確認
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        MoneyCheck();
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        MoneyCheck();
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        MoneyCheck();
    }
    @EventHandler
    public void onEnable(PluginEnableEvent event){
        MoneyCheck();
    }
    @EventHandler
    public void onDisable(PluginDisableEvent event){
        MoneyCheck();
    }
    public void MoneyCheck(){
           if(Bukkit.getPluginManager().getPlugin("MoneyCraft") == null && Bukkit.getPluginManager().getPlugin("Vault") == null){
               if(money == true||check == false) {
                   getLogger().info(ChatColor.YELLOW + "MoneyCraftが認識されませんでした。money関係のコマンドを無効化します。");
                   money = false;
                   check = true;
               }
           } else if (Bukkit.getPluginManager().getPlugin("MoneyCraft").isEnabled()&&Bukkit.getPluginManager().getPlugin("Vault").isEnabled()) {
               if (money == false || check == false) {
                   getLogger().info(ChatColor.GREEN + "MoneyCraftが認識されました。money関係のコマンドを有効化します。");
                   money = true;
                   check = true;
               }
           }
           else {
                if(money == true || check == false) {
                    getLogger().info(ChatColor.YELLOW + "MoneyCraftが認識されませんでした。money関係のコマンドを無効化します。");
                    money = false;
                    check = true;
                }
            }
    }

    //MoneyCraft対応
    public ItemStack MoneyCraftItem(int t,int a){
        if(t==100){
            ItemStack MoneyItem = new ItemStack(Material.GOLD_NUGGET,a);
            return MoneyItem;
        }else if(t==1000){
            ItemStack MoneyItem = new ItemStack(Material.GOLD_INGOT,a);
            return MoneyItem;
        }
        else {
            return null;
        }
    }

    //インベントリアイテムの確認
    public boolean ItemCheck(Player p) {
        boolean ans = false;
        for (int i = 0; i <= 40; i++) {
            if (p.getInventory().getItem(i) == null) {
                ans = false;
            } else {
                ans = true;
                i = 41;
            }
        }
        return ans;
    }

    //ジャンプ位置の判定
    public boolean LocationComparison(Player p, Location l) {
        if (!(Players.contains(p))) {
            Players.add(p);
            loc4.add(null);
            loc3.add(null);
            loc2.add(null);
            loc1.add(l);
            return false;
        } else if (Players.contains(p)) {
            int n = Players.indexOf(p);
            loc4.set(n, loc3.get(n));
            loc3.set(n, loc2.get(n));
            loc2.set(n, loc1.get(n));
            loc1.set(n, l);
            if (loc1.get(n) != null && loc2.get(n) != null && loc3.get(n) != null && loc4.get(n) != null) {
                if (loc1.get(n).getX() == loc2.get(n).getX() && loc2.get(n).getX() == loc3.get(n).getX() && loc3.get(n).getX() == loc4.get(n).getX()) {
                    if (loc1.get(n).getZ() == loc2.get(n).getZ() && loc2.get(n).getZ() == loc3.get(n).getZ() && loc3.get(n).getZ() == loc4.get(n).getZ()) {
                        if (loc1.get(n).getY() + 1 > loc2.get(n).getY() && loc1.get(n).getY() - 1 < loc2.get(n).getY()) {
                            if (loc1.get(n).getY() + 1 > loc3.get(n).getY() && loc1.get(n).getY() - 1 < loc3.get(n).getY()) {
                                if (loc1.get(n).getY() + 1 > loc4.get(n).getY() && loc1.get(n).getY() - 1 < loc4.get(n).getY()) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }

                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //背後にプレイヤーがいるかどうか
    public boolean Visibilty(Player p1, Player p2) {
        Location l1 = p1.getLocation();
        Location l2 = p2.getLocation();
        double lx = l2.getX() - l1.getX();
        double lz = l2.getZ() - l1.getZ();
        double r1 = 0, r2 = 0;
        r1 = l1.getYaw();
        if (r1 < 0) {
            if (r1 < 0 && r1 >= -90) {
                if (lx < 0 && lz < 0) {
                    return true;
                } else {
                    return false;
                }
            } else if (r1 < -90 && r1 >= -180) {
                if (lx < 0 && lz >= 0) {
                    return true;
                } else {
                    return false;
                }
            } else if (r1 < -180 && r1 >= -270) {
                if (lx >= 0 && lz >= 0) {
                    return true;
                } else {
                    return false;
                }
            } else if (r1 < -270) {
                if (lx >= 0 && lz < 0) {
                    return true;
                } else {
                    return false;
                }
            }else{
                return false;
            }
        } else {
            if (r1 > 0 && r1 <= 90) {
                if (lx >= 0 && lz < 0) {
                    return true;
                } else {
                    return false;
                }
            } else if (r1 > 90 && r1 <= 180) {
                if (lx >= 0 && lz >= 0) {
                    return true;
                } else {
                    return false;
                }
            } else if (r1 > 180 && r1 <= 270) {
                if (lx < 0 && lz >= 0) {
                    return true;
                } else {
                    return false;
                }
            } else if (r1 > 270) {
                if (lx < 0 && lz < 0) {
                    return true;
                } else {
                    return false;
                }
            }else{
                return false;
            }
        }
    }

    //アイテムのパーティクル等の処理
        public void ItemProcessing(Player p,Entity i,Location l){
            p.getWorld().playSound(l, Sound.ITEM_ARMOR_EQUIP_CHAIN, 100, 1);
            Item ic = (Item) i;
            ic.setCustomName("KatuagePlugin"+p.getName());
            i.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY,i.getLocation(),8,0.3,0.7,0.3);
            TimerTask Pt1 = new TimerTask(){
                public void run(){
                    i.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY,i.getLocation(),8,0.3,0.7,0.3);
                }
            };
            timer.schedule(Pt1,1000);
            TimerTask Pt2 = new TimerTask(){
                public void run(){
                    i.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY,i.getLocation(),8,0.3,0.7,0.3);
                }
            };
            timer.schedule(Pt2,2000);
            TimerTask Pt3 = new TimerTask(){
                public void run(){
                    i.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY,i.getLocation(),8,0.3,0.7,0.3);
                }
            };
            timer.schedule(Pt3,3000);
            TimerTask del = new TimerTask(){
                public void run(){
                    ic.setCustomName(null);
                }
            };
            timer.schedule(del,6000);
        }
    public void MoneyItemProcessing(Player p,Entity i,Location l){
        p.getWorld().playSound(l, Sound.ITEM_ARMOR_EQUIP_CHAIN, 100, 1);
        Item ic = (Item) i;
        ic.setCustomName(null);
        ic.setCustomName("KatuagePlugin"+p.getName());
        i.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY,i.getLocation(),8,0.3,0.7,0.3);
        TimerTask Pt1 = new TimerTask(){
            public void run(){
                i.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY,i.getLocation(),8,0.3,0.7,0.3);
            }
        };
        timer.schedule(Pt1,1000);
        TimerTask Pt2 = new TimerTask(){
            public void run(){
                i.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY,i.getLocation(),8,0.3,0.7,0.3);
            }
        };
        timer.schedule(Pt2,2000);
        TimerTask Pt3 = new TimerTask(){
            public void run(){
                i.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY,i.getLocation(),8,0.3,0.7,0.3);
            }
        };
        timer.schedule(Pt3,3000);
        TimerTask del = new TimerTask(){
            public void run(){
                ic.setCustomName(null);
            }
        };
        timer.schedule(del,6000);
    }

}
