package me.legrid.newplugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public final class NewPlugin extends JavaPlugin implements Listener {



    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("armlet"))
            if (sender instanceof Player) {
                Player p = (Player) sender;

            }
        return true;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        ItemStack gift = null;
        double percent = 0;

        if (e.getEntityType() == EntityType.BAT){
            gift = new ItemStack(Material.CYAN_SHULKER_BOX);
            ItemMeta m = gift.getItemMeta();
            m.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Подарок");
            List<String> l = new ArrayList<String>();
            l.add(ChatColor.WHITE + "" + ChatColor.BOLD + "ПКМ чтобы открыть");
            m.setLore(l);
            gift.setItemMeta(m);

            percent = 0.1;
        }
        if (gift == null)
            return;
        if (new Random().nextDouble() > percent)
            return;

        e.getDrops().add(gift);
    }

    @EventHandler
    public void openGift(PlayerInteractEvent e) {

        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Подарок"))
            if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()){

                Player p = (Player) e.getPlayer();
                if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                    e.getPlayer().getInventory().setItemInMainHand(dropItem());
                    if (new Random().nextDouble() > 0.1) {
                        p.sendMessage("Подарок с шансом 90% тебе выпал");
                        e.getPlayer().getInventory().addItem(giftDrop1());
                    }
                    if (new Random().nextDouble() > 0.5) {
                        p.sendMessage("подарок с шансом 50% тебе выпал");
                        e.getPlayer().getInventory().addItem(giftDrop2());
                    }
                    if (new Random().nextDouble() > 0.9) {
                        p.sendMessage("Подарок с шансом 10% тебе выпал");
                        e.getPlayer().getInventory().addItem(giftDrop3());
                    }
                }
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
                    e.setCancelled(true);
                    p.sendMessage("Его же нельзя ставить?");
                    e.getPlayer().getInventory().setItemInMainHand(dropItem());
                }


            }
    }
    public ItemStack dropItem(){
        ItemStack skip = new ItemStack(Material.BOWL);
        ItemMeta sm = skip.getItemMeta();
        sm.setDisplayName("Просто пустая и грустная миска");
        List<String> l = new ArrayList<String>();
        l.add("Правда, просто миска");
        sm.setLore(l);
        skip.setItemMeta(sm);

        return skip;
    }
    public ItemStack giftDrop1(){
        ItemStack item = new ItemStack(Material.DIAMOND,2);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName("Алмаз");
        item.setItemMeta(m);

        return item;
    }
    public ItemStack giftDrop2(){
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName("Алмазный Меч");
        m.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
        item.setItemMeta(m);
        return item;
    }
    public ItemStack giftDrop3(){
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(ChatColor.RED + "Алмаз жизни!");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.WHITE + "" + ChatColor.BOLD + "Алмаз, дающий право");
        lore.add(ChatColor.WHITE + "" + ChatColor.BOLD + "на существовании живого");
        m.setLore(lore);
        m.addEnchant(Enchantment.THORNS, 1, true);
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(m);
        return item;
    }



}



