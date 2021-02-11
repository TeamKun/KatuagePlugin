package net.kunmc.lab.katuage;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
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
    Timer timer = new Timer();

    @Override
    public void onEnable() {
        this.getCommand("katuage").setExecutor(this);
        this.getCommand("katuage").setTabCompleter(new TabComp());
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new ItemGet(), this);
        saveDefaultConfig();
    }

    //コマンド処理
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
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
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:KatuagePluginをONにしました！");
                    } else if (args[1].equals("off")) {
                        config.set("PluginOnOff", false);
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:KatuagePluginをOFFにしました！");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                    }
                }
                //ランダムドロップのon,off
                else if (args[0].equals("rand")) {
                    if (args[1].equals("on")) {
                        config.set("RandomDropOnOff", true);
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:ランダムドロップをONにしました！");
                    } else if (args[1].equals("off")) {
                        config.set("RandomDropOnOff", false);
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:ランダムドロップをOFFにしました！");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                    }
                }
                //ランダムドロップの確立設定
                else if (args[0].equals("randP")) {
                    if (Integer.parseInt(args[1]) <= 100 && Integer.parseInt(args[1]) >= 0) {
                        config.set("RandomDropProbability", args[1]);
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:ランダムドロップの確立を" + args[1] + "%にしました！");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                    }
                }
                //インベントリドロップのon,off
                else if (args[0].equals("inve")) {
                    if (args[1].equals("on")) {
                        config.set("InventoryDropOnOff", true);
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:インベントリドロップをONにしました！");
                    } else if (args[1].equals("off")) {
                        config.set("InventoryDropOnOff", false);
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:インベントリドロップをOFFにしました！");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                    }
                }
                //インベントリドロップの確立設定
                else if (args[0].equals("inveP")) {
                    if (Integer.parseInt(args[1]) <= 100 && Integer.parseInt(args[1]) >= 0) {
                        config.set("InventoryDropProbability", args[1]);
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:インベントリドロップの確立を" + args[1] + "%にしました！");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                    }
                }
                //ダメージドロップのon,off
                else if (args[0].equals("dame")) {
                    if (args[1].equals("on")) {
                        config.set("DamageDropOnOff", true);
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:ダメージドロップをONにしました！");
                    } else if (args[1].equals("off")) {
                        config.set("DamageDropOnOff", false);
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:ダメージドロップをOFFにしました！");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
                    }
                }
                //ダメージドロップの確率
                else if (args[0].equals("dameP")) {
                    if (Integer.parseInt(args[1]) <= 100 && Integer.parseInt(args[1]) >= 0) {
                        config.set("DamageDropProbability", args[1]);
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:ダメージドロップの確立を" + args[1] + "%にしました！");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[KatuagePlugin]:引数が違うよ~！");
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
        Player p = event.getPlayer();
        Location loc = p.getLocation();
        reloadConfig();
        FileConfiguration config = getConfig();
        if (LocationComparison(p, loc) == true) {
            if (config.getBoolean("PluginOnOff") == true && config.getBoolean("InventoryDropOnOff") == true && ItemCheck(p) == true) {
                int numP = rand.nextInt(100);//アイテムドロップの確立
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
                int numP = rand.nextInt(100);//アイテムドロップの確立
                int numI = 1 + rand.nextInt(17);//インベントリアイテムの番号指定
                int numA = Integer.parseInt(config.getString("RandomDropProbability"));
                ItemStack item = config.getItemStack("Item" + numI);
                if ((numP <= numA)) {
                    Entity ie = loc.getWorld().dropItem(loc, item);
                    ItemProcessing(p, ie, loc);
                }
            }
        }
    }

    //ダメージドロップの処理
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)){return;}
        if(!(event.getDamager() instanceof Player)){return;}
        Player p = (Player) event.getEntity();
        Player e = (Player) event.getDamager();
        Location loc = p.getLocation();
        reloadConfig();
        FileConfiguration config = getConfig();
        if (Visibilty(p, e) == true&&e.isSneaking()==true) {
            if (config.getBoolean("PluginOnOff") == true && config.getBoolean("DamageDropOnOff") == true && ItemCheck(p) == true) {
                int numP = rand.nextInt(100);//アイテムドロップの確立
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
            TimerTask Pt4 = new TimerTask(){
                public void run(){
                    i.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY,i.getLocation(),8,0.3,0.7,0.3);
                }
            };
            timer.schedule(Pt4,3500);
            TimerTask del = new TimerTask(){
                public void run(){
                    ic.setCustomName(null);
                }
            };
            timer.schedule(del,6000);
        }
}
