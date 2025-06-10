package hypernova.voidedhopes.item;

import hypernova.voidedhopes.VoidedHopes;
import hypernova.voidedhopes.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModItemGroup {
    public static final ItemGroup VOIDEDHOPES_TAB = Registry.register(Registries.ITEM_GROUP,
            VoidedHopes.id("voidedhopes_tab"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemGroup.voided_hopes.voidedhopes_tab"))
                    .icon(() -> new ItemStack(ModBlocks.REALITY_DETONATOR.asItem()))
                    .entries((displayContext, entries) -> {


            }).build());
}