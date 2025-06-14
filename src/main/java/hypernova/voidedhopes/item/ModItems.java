package hypernova.voidedhopes.item;

import hypernova.voidedhopes.VoidedHopes;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {




    public static final Item SOUL_EXTRACTOR = registerItem("soul_extractor",
            new Item(new FabricItemSettings()));
    public static final Item VOIDED_SHARD = registerItem("voided_shard",
            new Item(new FabricItemSettings()));
    public static final Item WAYFINDER_CLOSED = registerItem("wayfinder_closed",
            new Item(new FabricItemSettings()));
    public static final Item WAYFINDER_OPEN = registerItem("wayfinder_open",
            new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, VoidedHopes.id(name), item);
    }

    public static void registerModItems() {
        VoidedHopes.LOGGER.debug("Registering Mod Items for " + VoidedHopes.MOD_ID);
    }
}