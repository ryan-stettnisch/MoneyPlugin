package me.ryans.moneyplugin.commands;
import net.md_5.bungee.chat.SelectorComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TutorialCommands implements CommandExecutor, Listener {

    public static NamespacedKey key = new NamespacedKey("money", "price");
    private static int totalBalance;
    public static Player player;

    public static List<ItemStack> billsList;

    private static int exchangeRate;

    private int i;

    private static Map<Integer, ItemStack> BILLS = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;

        player = (Player) sender;

        exchangeRate = 3;

        if (cmd.getName().equalsIgnoreCase("exchange")) {
            int size = 0;
            Material itemType = Material.PAPER;
            ItemStack base = new ItemStack(itemType);
            if (player.getItemInHand().getType().equals(Material.DIAMOND)) {
                size = exchangeRate * player.getInventory().getItemInMainHand().getAmount();
                player.getInventory().setItemInHand(null);
                player.updateInventory();
                List<ItemStack> list1 = createBills(base, size);
                int slots = numSlots();
                if (list1.size() + slots > 36) {
                    for (int k = 36 - slots; k < list1.size(); k++) {
                        ItemStack i1 = list1.get(k);
                        player.getWorld().dropItemNaturally(player.getLocation(), i1);
                    }
                    for (int k = list1.size() - 1; k >= 36 - numSlots(); k--) {
                        list1.remove(k);
                    }
                    list1.forEach(player.getInventory()::addItem);
                } else
                    list1.forEach(player.getInventory()::addItem);

            } else if (!diamondInInv())
                player.sendMessage("Zero diamonds in inventory. ");
            else
                player.sendMessage("Hold the diamonds in your hand.");
        }
        if (cmd.getName().equalsIgnoreCase("exchangerate")) {
            player.sendMessage("The Current Exchange Rate for One Diamond is: $" + exchangeRate + "");
        }
        if (cmd.getName().equalsIgnoreCase("totalbalance")) {
            player.sendMessage("Total Amount in Circulation: " + totalBalance + "");
        }
        if (cmd.getName().equalsIgnoreCase("convert")) {
            int a1 = 0;
            int amount = 0;
            int pos = 0;


                if(Integer.parseInt(args[0]) == 1)
                    pos = 1;
                else if(Integer.parseInt(args[0]) == 5)
                    pos = 5;
                else if(Integer.parseInt(args[0]) == 10)
                    pos = 10;
                else if(Integer.parseInt(args[0]) == 20)
                    pos = 20;
                else if(Integer.parseInt(args[0]) == 50)
                    pos = 50;
                else if(Integer.parseInt(args[0]) == 100)
                    pos = 100;
                else {
                    player.sendMessage("Error");
                    return true;
                }

            Map<Integer, ItemStack> b = createBillsList();
            ItemStack con = player.getInventory().getItemInMainHand();
            amount = con.getAmount();
            con.setAmount(1);

            if(con.equals(b.get(1)) || con.equals(b.get(5)) || con.equals(b.get(10)) || con.equals(b.get(20)) || con.equals(b.get(50)) || con.equals(b.get(100))) {
            }
            else {
                player.sendMessage("Error");
                con.setAmount(amount);
                return true;
            }
                if(con.equals(b.get(1)))
                    a1 = 1;
                else if(con.equals(b.get(5)))
                    a1 = 5;
                else if(con.equals(b.get(10)))
                    a1 = 10;
                else if(con.equals(b.get(20)))
                    a1 = 20;
                else if(con.equals(b.get(50)))
                    a1 = 50;
                else if(con.equals(b.get(100)))
                    a1 = 100;
                else
                    a1 = 1;
            if(args.length == 1) {
                if(amount % pos == 0 && amount == a1) {
                    player.getInventory().setItemInHand(null);
                    for (int a = 0; a < (amount * a1) / pos; a++) {
                        player.getInventory().addItem(b.get(pos));
                    }
                }
                else if((amount * a1) % pos == 0) {
                    player.getInventory().setItemInHand(null);
                    for(int a = 0; a < (amount * a1) / pos; a++) {
                        player.getInventory().addItem(b.get(pos));
                    }
                }
                else if(amount % pos == 0)  {
                    player.getInventory().setItemInHand(null);
                    for(int a = 0; a < amount / pos; a++) {
                        player.getInventory().addItem(b.get(pos));
                    }
                }
                else {
                    con.setAmount(amount);
                    player.sendMessage("Error");
                }
            }
            else {
                con.setAmount(amount);
                player.sendMessage(ChatColor.RED + "Error, Argument Needs To Be /convert (billType)");
            }

        }
        return true;
    }

    public List<ItemStack> createBills(ItemStack base, int amount) {
        List<ItemStack> bills = new ArrayList<>();
        if (amount > 0) {
            int hundreds = (amount - (amount % 100)) / 100;
            amount -= hundreds * 100;
            int fifties = (amount - (amount % 50)) / 50;
            amount -= fifties * 50;
            int twenties = (amount - (amount % 20)) / 20;
            amount -= twenties * 20;
            int tens = (amount - (amount % 10)) / 10;
            amount -= tens * 10;
            int fives = (amount - (amount % 5)) / 5;
            amount -= fives * 5;
            int singles = amount;
            if (hundreds > 0) {
                bills.add(createBill(base, hundreds, 100));
            }
            if (fifties > 0) {
                bills.add(createBill(base, fifties, 50));
            }
            if (twenties > 0) {
                bills.add(createBill(base, twenties, 20));
            }
            if (tens > 0) {
                bills.add(createBill(base, tens, 10));
            }
            if (fives > 0) {
                bills.add(createBill(base, fives, 5));
            }
            if (singles > 0) {
                bills.add(createBill(base, singles, 1));
            }
        }
        return bills;
    }

    public ItemStack createBill(ItemStack base, int amount, int price) {
        ItemStack bill = base.clone();
        ItemMeta baseIM = bill.getItemMeta();
        baseIM.setDisplayName("$" + price + " Bill");
        baseIM.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, price);
        bill.setItemMeta(baseIM);
        bill.setAmount(amount);
        totalBalance += amount * price;
        return bill;
    }

    public int numSlots() {
        i = 0;
        Inventory inv = player.getInventory();
        boolean check = false;
        for (ItemStack item : inv.getContents()) {
            if (item != null)
                i++;
        }
        return i;
    }
    public boolean diamondInInv() {
        Inventory inv2 = player.getInventory();
        Material itemType2 = Material.DIAMOND;
        ItemStack base2 = new ItemStack(itemType2);
        boolean diamonds = inv2.contains(itemType2);
        return diamonds;
    }

    public static Map<Integer, ItemStack> createBillsList() {
        if(!BILLS.isEmpty()) return BILLS;
        BILLS.put(1, createBill(1));
        BILLS.put(5, createBill(5));
        BILLS.put(10, createBill(10));
        BILLS.put(20, createBill(20));
        BILLS.put(50, createBill(50));
        BILLS.put(100, createBill(100));
        return BILLS;
    }
    public static ItemStack createBill(int value) {
        ItemStack bill = new ItemStack(Material.PAPER);
        ItemMeta im = bill.getItemMeta();
        im.setDisplayName("$" + value + " Bill");
        im.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, value);
        bill.setItemMeta(im);
        return bill;
    }
}