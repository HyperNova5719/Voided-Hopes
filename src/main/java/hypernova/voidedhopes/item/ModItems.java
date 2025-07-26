package hypernova.voidedhopes.item;

import hypernova.voidedhopes.VoidedHopes;
import hypernova.voidedhopes.item.custom.VoidSparkWeapon;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class ModItems {


    public static final Item SOUL_EXTRACTOR = registerItem("soul_extractor",
            new Item(new FabricItemSettings()));
    public static final Item VOIDED_SHARD = registerItem("voided_shard",
            new Item(new FabricItemSettings()));
    public static final Item WAYFINDER_CLOSED = registerItem("wayfinder_closed",
            new Item(new FabricItemSettings()));
    public static final Item WAYFINDER_OPEN = registerItem("wayfinder_open",
            new Item(new FabricItemSettings()));
    public static final Item VOID_SPARK = registerItem("void_spark",
            new VoidSparkWeapon(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item HERALD = registerItem("yes",
            new TestItem(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item REALITY_KEY = registerItem("reality_key",
            new TestItem(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM,VoidedHopes.id(name), item);
    }

    public static void registerModItems() {
        VoidedHopes.LOGGER.debug("Registering Mod Items for " + VoidedHopes.MOD_ID);
    }
}
