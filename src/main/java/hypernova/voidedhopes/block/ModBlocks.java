package hypernova.voidedhopes.block;

import hypernova.voidedhopes.VoidedHopes;
import hypernova.voidedhopes.block.custom.MatrixVoidBlockEntity;
import hypernova.voidedhopes.block.custom.PureVoidBlock;
import hypernova.voidedhopes.block.custom.PureVoidBlockEntity;
import hypernova.voidedhopes.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block PURE_VOID = registerBlock("pure_void",
            new PureVoidBlock(FabricBlockSettings.of(Material.BARRIER).strength(-1f)), ModItemGroup.VOIDEDHOPES_TAB);
    public static final BlockEntityType<? extends PureVoidBlockEntity> PURE_VOID_TYPE = registerType("pure_void", FabricBlockEntityTypeBuilder.create(PureVoidBlockEntity::new, ModBlocks.PURE_VOID).build());
    public static final Block MATRIX_VOID = registerBlock("pure_void",
            new PureVoidBlock(FabricBlockSettings.of(Material.BARRIER).strength(-1f)), ModItemGroup.VOIDEDHOPES_TAB);
    public static final BlockEntityType<? extends MatrixVoidBlockEntity> MATRIX_VOID_TYPE = registerType("matrix_void",FabricBlockEntityTypeBuilder.create(MatrixVoidBlockEntity::new, ModBlocks.MATRIX_VOID).build());


    // to add more blocks go to 9:32 in this video: https://www.youtube.com/watch?v=6DY372RYNfE&list=PLKGarocXCE1EeLZggaXPJaARxnAbUD8Y_&index=4



    private static Block registerBlock(String name, Block block, ItemGroup tab) {
        registerBlockItems(name, block,tab);
        return Registry.register(Registry.BLOCK, new Identifier(VoidedHopes.MOD_ID, name), block);
    }

    private static Item registerBlockItems(String name, Block block, ItemGroup tab) {
        return Registry.register(Registry.ITEM, new Identifier(VoidedHopes.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(tab)));
    }

    public static <T extends BlockEntityType<?>> T registerType(String name, T blockEntityType) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, VoidedHopes.id(name), blockEntityType);
    }

    public static void registerModBlocks() {
        VoidedHopes.LOGGER.debug("Registering ModBlocks for " + VoidedHopes.MOD_ID);
    }
}