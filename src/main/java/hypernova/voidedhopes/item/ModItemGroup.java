package hypernova.voidedhopes.item;

import hypernova.voidedhopes.VoidedHopes;
import hypernova.voidedhopes.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public class ModItemGroup {
    public static final RegistryKey<ItemGroup> VOIDEDHOPES_TAB = RegistryKey.of(RegistryKeys.ITEM_GROUP, VoidedHopes.id("voidedhopes_tab"));

    public static final ItemGroup GROUP = Registry.register(Registries.ITEM_GROUP, VoidedHopes.id("voidedhopes_tab"), FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.voided_hopes.voidedhopes_tab"))
            .icon(() -> new ItemStack(ModBlocks.REALITY_DETONATOR.asItem())).entries((displayContext, entries) -> {
                entries.add(ModItems.WAYFINDER);
                entries.add(ModItems.VOIDED_SHARD);
                entries.add(ModItems.SOUL_EXTRACTOR);

            }).build());
    public static final RegistryKey<ItemGroup> WEAPONS_AND_SINS = RegistryKey.of(RegistryKeys.ITEM_GROUP, VoidedHopes.id("weapons_and_sins"));

    public static final ItemGroup ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, VoidedHopes.id("weapons_and_sins"), FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.voided_hopes.weapons_and_sins"))
            .icon(() -> new ItemStack(ModItems.WRAITHS_GRAVESTONE.asItem())).entries((displayContext, entries) -> {
                entries.add(ModItems.WRAITHS_GRAVESTONE);
                entries.add(ModItems.REALITIES_BANE);
                entries.add(ModItems.REGRET_OF_FOOLS);
                entries.add(ModItems.ENVY);
                entries.add(ModItems.GLUTTONY);
                entries.add(ModItems.GREED);
                entries.add(ModItems.LUST);
                entries.add(ModItems.PRIDE);
                entries.add(ModItems.SLOTH);
                entries.add(ModItems.WRATH);
                entries.add(ModItems.REALITY_KEY);
            }).build());
}