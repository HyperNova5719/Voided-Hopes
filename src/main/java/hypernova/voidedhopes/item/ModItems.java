package hypernova.voidedhopes.item;

import hypernova.voidedhopes.VoidedHopes;
import hypernova.voidedhopes.item.custom.GodsGravestoneItem;
import hypernova.voidedhopes.item.custom.RealitiesBaneItem;
import hypernova.voidedhopes.item.custom.VoidItem;
import hypernova.voidedhopes.item.custom.WayfinderItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

public class ModItems {


    public static final Item SOUL_EXTRACTOR = registerItem("soul_extractor",
            new Item(new FabricItemSettings()));
    public static final Item VOIDED_SHARD = registerItem("voided_shard",
            new Item(new FabricItemSettings()));
    public static final Item WAYFINDER = registerItem("wayfinder",
            new WayfinderItem(new FabricItemSettings()));



    public static final Item REALITIES_BANE = registerItem("realities_bane",
            new RealitiesBaneItem(new FabricItemSettings()));





    public static final Item REALITY_KEY = registerItem("reality_key",
            new DetonateItem(new FabricItemSettings()));
    public static final Item GODS_GRAVESTONE = registerItem("gods_gravestone",
            new GodsGravestoneItem(new FabricItemSettings()));
    public static final Item REGRET_OF_FOOLS = registerItem("regret_of_fools",
            new VoidItem(new FabricItemSettings()));
    public static final Item ENVY = registerItem("envy",
            new VoidItem(new FabricItemSettings()));
    public static final Item GLUTTONY = registerItem("gluttony",
            new VoidItem(new FabricItemSettings()));
    public static final Item GREED = registerItem("greed",
            new VoidItem(new FabricItemSettings()));
    public static final Item LUST = registerItem("lust",
            new VoidItem(new FabricItemSettings()));
    public static final Item PRIDE = registerItem("pride",
            new VoidItem(new FabricItemSettings()));
    public static final Item SLOTH = registerItem("sloth",
            new VoidItem(new FabricItemSettings()));
    public static final Item WRATH = registerItem("wrath",
            new VoidItem(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM,VoidedHopes.id(name), item);
    }

    public static void registerModItems() {
        VoidedHopes.LOGGER.debug("Registering Mod Items for " + VoidedHopes.MOD_ID);
    }
}
