package net.kunmc.lab.katuage;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemGet implements Listener {
    //ドロップアイテム入手時の処理
    @EventHandler
    public void onPickUpItem(PlayerPickupItemEvent event){
        Item i = event.getItem();
        Player p = event.getPlayer();
        if(i.getCustomName()==null){
            event.setCancelled(false);
        } else if(i.getCustomName().equals("KatuagePlugin"+p.getName())){
            event.setCancelled(true);
        }else if(i.getCustomName().equals("MoneyKatuagePlugin"+p.getName())){
            event.setCancelled(true);
        }else if(i.getCustomName().contains("MoneyKatuagePlugin")){
            ItemMeta meta =  i.getItemStack().getItemMeta();
            meta.setDisplayName("MoneyCraft");
            i.getItemStack().setItemMeta(meta);
            event.setCancelled(true);
        }else if(i.getCustomName().equals("Money")){
            ItemMeta meta =  i.getItemStack().getItemMeta();
            meta.setDisplayName("MoneyCraft");
            i.getItemStack().setItemMeta(meta);
            event.setCancelled(true);
        }
    }

}
