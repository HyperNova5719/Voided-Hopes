package hypernova.voidedhopes.item;

import hypernova.voidedhopes.VoidedHopes;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {



    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, VoidedHopes.id(name), item);
    }

    public static void registerModItems() {
        VoidedHopes.LOGGER.debug("Registering Mod Items for " + VoidedHopes.MOD_ID);
    }
}