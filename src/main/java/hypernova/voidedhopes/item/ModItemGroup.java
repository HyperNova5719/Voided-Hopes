package hypernova.voidedhopes.item;

import hypernova.voidedhopes.VoidedHopes;
import hypernova.voidedhopes.block.ModBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup PURE_VOID = FabricItemGroupBuilder.build(
            new Identifier(VoidedHopes.MOD_ID, "pure_void"), () -> new ItemStack(ModBlocks.PURE_VOID));
}