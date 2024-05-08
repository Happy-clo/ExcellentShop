package su.nightexpress.nexshop.shop.chest.config;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import su.nightexpress.nexshop.Placeholders;
import su.nightexpress.nexshop.api.shop.type.TradeType;
import su.nightexpress.nexshop.currency.handler.VaultEconomyHandler;
import su.nightexpress.nexshop.hook.HookId;
import su.nightexpress.nexshop.shop.chest.util.ShopType;
import su.nightexpress.nightcore.config.ConfigValue;
import su.nightexpress.nightcore.util.*;

import java.util.*;

import static su.nightexpress.nightcore.util.text.tag.Tags.*;
import static su.nightexpress.nexshop.shop.chest.Placeholders.*;

public class ChestConfig {

    public static final ConfigValue<Boolean> DELETE_INVALID_SHOP_CONFIGS = ConfigValue.create("Shops.Delete_Invalid_Shop_Configs",
        false,
        "Sets whether or not invalid shops (that can not be loaded properly) will be auto deleted.");

    public static final ConfigValue<String> ADMIN_SHOP_NAME = ConfigValue.create("Shops.AdminShop_Name",
        "AdminShop",
        "Sets custom shop's owner name for admin shops.");

    public static final ConfigValue<String> DEFAULT_NAME = ConfigValue.create("Shops.Default_Name",
        "Shop",
        "Default shop name, that will be used on shop creation."
    );

    public static final ConfigValue<Integer> SHOP_MAX_NAME_LENGTH = ConfigValue.create("Shops.Max_Name_Length",
        12,
        "Sets max. possible length for shop name.",
        "Useful to prevent players from setting ridiculously long names for their shops."
    );

    public static final ConfigValue<String> DEFAULT_CURRENCY = ConfigValue.create("Shops.Default_Currency",
        VaultEconomyHandler.ID,
        "Sets the default ChestShop currency. It will be used for new products and when no other currencies are available."
    );

    public static final ConfigValue<Set<String>> ALLOWED_CURRENCIES = ConfigValue.create("Shops.Allowed_Currencies",
        Set.of(VaultEconomyHandler.ID),
        "A list of currencies that can be used for Chest Shop products."
    );

    public static final ConfigValue<Set<Material>> ALLOWED_CONTAINERS = ConfigValue.forSet("Shops.Allowed_Containers",
        BukkitThing::getMaterial,
        (cfg, path, set) -> cfg.set(path, set.stream().map(BukkitThing::toString).toList()),
        () -> {
            Set<Material> set = new HashSet<>(Tag.SHULKER_BOXES.getValues());
            set.add(Material.CHEST);
            set.add(Material.TRAPPED_CHEST);
            set.add(Material.BARREL);
            return set;
        },
        "A list of Materials, that can be used for shop creation.",
        "Only 'Container' block materials can be used!",
        "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html",
        "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/block/Container.html"
    );

    public static final ConfigValue<Boolean> SHOP_AUTO_BANK = ConfigValue.create("Shops.Auto_Bank",
        true,
        "Sets whether or not player's shop bank will be auto-managed without manual operations.",
        "This means that players will gain/lose their funds instantly for each transaction in their shops.");

    public static final ConfigValue<Boolean> SHOP_OFFLINE_TRANSACTIONS = ConfigValue.create("Shops.OfflineTransactions",
        false,
        "Sets whether or not plugin will withdraw/deposit currency directly",
        "from/to shop owner's account when player is offline instead of shop bank usage.",
        "",
        "Why? Even with 'Auto_Bank' option enabled, players can't sell items to shops with offline owners",
        "unless there are enough funds in the shop bank.",
        "This setting will fix bypass that.",
        "",
        "[*] This feature may damage performance (depends on the currency plugin and its offline player data handling).",
        "[*] This feature is not available for some currencies.");

    public static final ConfigValue<Double> SHOP_PRODUCT_INITIAL_BUY_PRICE = ConfigValue.create("Shops.Product.InitialPrice.Buy",
        10D,
        "Sets initial buy price for new products added in chest shops.");

    public static final ConfigValue<Double> SHOP_PRODUCT_INITIAL_SELL_PRICE = ConfigValue.create("Shops.Product.InitialPrice.Sell",
        2D,
        "Sets initial sell price for new products added in chest shops.");

    public static final ConfigValue<Double> SHOP_CREATION_COST_CREATE = ConfigValue.create("Shops.Creation.Cost.Create",
        0D,
        "Sets how much player have to pay in order to create a chest shop.");

    public static final ConfigValue<Double> SHOP_CREATION_COST_REMOVE = ConfigValue.create("Shops.Creation.Cost.Remove",
        0D,
        "Sets how much player have to pay in order to remove a chest shop.");

    public static final ConfigValue<Set<String>> SHOP_CREATION_WORLD_BLACKLIST = ConfigValue.create("Shops.Creation.World_Blacklist",
        Set.of("custom_world", "another_world"),
        "List of worlds, where chest shop creation is not allowed.");

    public static final ConfigValue<RankMap<Integer>> SHOP_CREATION_MAX_PER_RANK = ConfigValue.create("Shops.Creation.Max_Shops_Per_Rank",
        (cfg, path, rank) -> RankMap.read(cfg, path, Integer.class, 10),
        (cfg, path, map) -> map.write(cfg, path),
        () -> new RankMap<>(
            RankMap.Mode.RANK,
            ChestPerms.PREFIX_SHOP_LIMIT,
            10,
            Map.of(
                "vip", 20,
                "admin", -1
            )
        ),
        "Sets amount of possible shops available for certain ranks/permissions.",
        "Use '-1' for unlimited amount."
    );

    public static final ConfigValue<Boolean> SHOP_CREATION_CHECK_BUILD = ConfigValue.create("Shops.Creation.Check_Build_Access",
        true,
        "Sets whether or not plugin will simulate block placing to make sure that player can crate shops there.",
        "This setting should be enough as universal compatibility solution with claim/protection plugins.",
        "Disable this only if you're experiencing major issues."
    );

    public static final ConfigValue<Boolean> SHOP_CREATION_CLAIM_ONLY = ConfigValue.create("Shops.Creation.In_Player_Claims_Only.Enabled",
        false,
        "Sets whether or not players can create shops in their own claims only.",
        "Supported Plugins: " + HookId.LANDS + ", " + HookId.GRIEF_PREVENTION + ", " + HookId.WORLD_GUARD + ", " + HookId.KINGDOMS);

    public final static ConfigValue<RankMap<Integer>> SHOP_PRODUCTS_MAX_PER_RANK = ConfigValue.create("Shops.Products.Max_Products_Per_Shop",
        (cfg, path, rank) -> RankMap.read(cfg, path, Integer.class, 3),
        (cfg, path, map) -> map.write(cfg, path),
        () -> new RankMap<>(
            RankMap.Mode.RANK,
            ChestPerms.PREFIX_PRODUCT_LIMIT,
            3,
            Map.of(
                "vip", 5,
                "admin", -1
            )
        ),
        "Sets how many products a player with certain rank/permission can put in a shop at the same time.",
        "Use '-1' for unlimited amount."
    );

    public static final ConfigValue<Boolean> SHOP_PRODUCT_NEW_PRODUCTS_SINGLE_AMOUNT = ConfigValue.create("Shops.Products.New_Products_Single_Amount",
        false,
        "When enabled, all items added as products in chest shops will be added with 1 amount no matter of the stack amount player used in."
    );

    public static final ConfigValue<Set<String>> SHOP_PRODUCT_BANNED_ITEMS = ConfigValue.create("Shops.Products.Material_Blacklist",
        Set.of(
            BukkitThing.toString(Material.BARRIER),
            "custom_item_123"
        ),
        "List of items that can not be added as shop products.",
        "Vanilla Names: https://minecraft.wiki/w/Java_Edition_data_values -> Blocks / Items -> Resource location column.",
        "Supported Plugins: " + String.join(", ", HookId.getItemPluginNames())
    );

    public static final ConfigValue<Set<String>> SHOP_PRODUCT_DENIED_LORES = ConfigValue.create("Shops.Products.Lore_Blacklist",
        Set.of("fuck", "ass hole bitch"),
        "Items containing the following words in their lore will be disallowed from being used as shop products.");

    public static final ConfigValue<Set<String>> SHOP_PRODUCT_DENIED_NAMES = ConfigValue.create("Shops.Products.Name_Blacklist",
        Set.of("shit", "sample text"),
        "Items containing the following words in their name will be disallowed from being used as shop products.");


    public static final ConfigValue<Map<String, ItemStack>> DISPLAY_DEFAULT_SHOWCASE = ConfigValue.forMap("Display.Showcase",
        (cfg, path, id) -> cfg.getItem(path + "." + id),
        (cfg, path, map) -> map.forEach((type, item) -> cfg.setItem(path + "." + type, item)),
        () -> Map.of(
            Placeholders.DEFAULT, new ItemStack(Material.GLASS),
            BukkitThing.toString(Material.CHEST), new ItemStack(Material.WHITE_STAINED_GLASS)
        ),
        "Sets an item that will be used as default shop showcase.",
        "You can provide different showcases for different shop types you set in 'Allowed_Containers' option.",
        "Use '" + Placeholders.DEFAULT + "' keyword to set showcase item for all unlisted here container types.",
        "Available item options including model data: " + Placeholders.WIKI_ITEMS_URL
    );

    public static final ConfigValue<Boolean> DISPLAY_PLAYER_CUSTOMIZATION_ENABLED = ConfigValue.create("Display.PlayerCustomization.Enabled",
        true,
        "Sets whether or not players can customize displays for their shops personally.",
        "This feature can also be regulated by the following permission: '" + ChestPerms.DISPLAY_CUSTOMIZATION.getName() + "."
    );

    public static final ConfigValue<Map<String, ItemStack>> DISPLAY_PLAYER_CUSTOMIZATION_SHOWCASE_LIST = ConfigValue.forMap("Display.PlayerCustomization.Showcases",
        (cfg, path, id) -> cfg.getItem(path + "." + id),
        (cfg, path, map) -> map.forEach((id, item) -> cfg.setItem(path + "." + id, item)),
        () -> {
            Map<String, ItemStack> map = new HashMap<>();
            map.put("glass", new ItemStack(Material.GLASS));
            map.put("white_glass", new ItemStack(Material.WHITE_STAINED_GLASS));
            map.put("lime_glass", new ItemStack(Material.LIME_STAINED_GLASS));
            map.put("gray_glass", new ItemStack(Material.GRAY_STAINED_GLASS));
            map.put("black_glass", new ItemStack(Material.BLACK_STAINED_GLASS));
            map.put("blue_glass", new ItemStack(Material.BLUE_STAINED_GLASS));
            map.put("brown_glass", new ItemStack(Material.BROWN_STAINED_GLASS));
            map.put("cyan_glass", new ItemStack(Material.CYAN_STAINED_GLASS));
            map.put("green_glass", new ItemStack(Material.GREEN_STAINED_GLASS));
            map.put("light_blue_glass", new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS));
            map.put("light_gray_glass", new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS));
            map.put("magenta_glass", new ItemStack(Material.MAGENTA_STAINED_GLASS));
            map.put("orange_glass", new ItemStack(Material.ORANGE_STAINED_GLASS));
            map.put("pink_glass", new ItemStack(Material.PINK_STAINED_GLASS));
            map.put("purple_glass", new ItemStack(Material.PURPLE_STAINED_GLASS));
            map.put("red_glass", new ItemStack(Material.RED_STAINED_GLASS));
            map.put("yellow_glass", new ItemStack(Material.YELLOW_STAINED_GLASS));
            return map;
        },
        "List of items available to be used by players as shop showcases."
    );

    public static final ConfigValue<Integer> DISPLAY_UPDATE_INTERVAL = ConfigValue.create("Display.Update_Interval",
        3,
        "Defines update interval for shop displays (in seconds).");

    public static final ConfigValue<Integer> DISPLAY_VISIBLE_DISTANCE = ConfigValue.create("Display.Visible_Distance",
        10,
        "Sets shop display visibility distance.",
        "Players will see shop displays when they are close enough.");

    public static final ConfigValue<Boolean> DISPLAY_HOLOGRAM_ENABLED = ConfigValue.create("Display.Title.Enabled",
        true,
        "When 'true', creates a client-side hologram above the shop."
    );

    public static final ConfigValue<Boolean> DISPLAY_HOLOGRAM_FORCE_ARMOR_STAND = ConfigValue.create("Display.Hologram.Force_ArmorStands",
        false,
        "When enabled, forces plugin to use Armor Stand entities instead of Display ones."
    );

    public static final ConfigValue<Double> DISPLAY_HOLOGRAM_LINE_GAP = ConfigValue.create("Display.Hologram.LineGap",
        0.3D,
        "Sets distance between hologram lines."
    );

    public static final ConfigValue<Map<ShopType, List<String>>> DISPLAY_HOLOGRAM_TEXT = ConfigValue.forMap("Display.Title.Values",
        str -> StringUtil.getEnum(str, ShopType.class).orElse(null),
        (cfg, path, type) -> cfg.getStringList(path + "." + type),
        (cfg, path, map) -> map.forEach((type, list) -> cfg.set(path + "." + type.name(), list)),
        Map.of(
            ShopType.ADMIN, Arrays.asList(
                LIGHT_YELLOW.enclose(BOLD.enclose(SHOP_NAME)),
                LIGHT_GRAY.enclose(GENERIC_PRODUCT_NAME),
                LIGHT_GRAY.enclose(GREEN.enclose("B: ") + LIGHT_GREEN.enclose(GENERIC_PRODUCT_PRICE.apply(TradeType.BUY)) + " " + RED.enclose("S: ") + LIGHT_RED.enclose(GENERIC_PRODUCT_PRICE.apply(TradeType.SELL)))
            ),
            ShopType.PLAYER, Arrays.asList(
                LIGHT_YELLOW.enclose(BOLD.enclose(SHOP_NAME)),
                LIGHT_GRAY.enclose(GENERIC_PRODUCT_NAME),
                LIGHT_GRAY.enclose(GREEN.enclose("B: ") + LIGHT_GREEN.enclose(GENERIC_PRODUCT_PRICE.apply(TradeType.BUY)) + " " + RED.enclose("S: ") + LIGHT_RED.enclose(GENERIC_PRODUCT_PRICE.apply(TradeType.SELL)))
            )
        ),
        "Sets hologram text format for player and admin shops.",
        "You can use 'Chest Shop' placeholders: " + URL_WIKI_PLACEHOLDERS,
        "Display item name: " + GENERIC_PRODUCT_NAME,
        "Display item price: " + GENERIC_PRODUCT_PRICE.apply(TradeType.BUY) + ", " + GENERIC_PRODUCT_PRICE.apply(TradeType.SELL),
        Plugins.PLACEHOLDER_API + " is also supported here."
    );

}
