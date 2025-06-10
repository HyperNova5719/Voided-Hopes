package hypernova.voidedhopes.block;

import hypernova.voidedhopes.VoidedHopes;
import hypernova.voidedhopes.block.custom.*;
import hypernova.voidedhopes.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block PURE_VOID = registerBlock("pure_void", new PureVoidBlock(FabricBlockSettings
            .copyOf(Blocks.BARRIER)
            .strength(-1f)));

    public static final BlockEntityType<PureVoidBlockEntity> PURE_VOID_TYPE = registerBlockEntity("pure_void_entity", PureVoidBlockEntity::new, PURE_VOID);

    public static final Block ENDER_SKY_BLOCK = registerBlock(
            "ender_sky_block",
            new EnderSkyBlock(FabricBlockSettings
            .copy(Blocks.OBSIDIAN)));

    public static final BlockEntityType<EnderSkyBlockEntity> ENDER_SKY_TYPE = registerBlockEntity(
            "ender_sky_block_entity",
            EnderSkyBlockEntity::new, ENDER_SKY_BLOCK);

    public static final Block MATRIX_VOID = registerBlock("matrix_void", new MatrixVoidBlock(FabricBlockSettings.copy(Blocks.OBSIDIAN)));

    public static final BlockEntityType<MatrixVoidBlockEntity> MATRIX_VOID_TYPE = registerBlockEntity(
            "matrix_void_entity",
            MatrixVoidBlockEntity::new, MATRIX_VOID);

    public static final Block REALITY_DETONATOR = registerBlock("reality_detonator", new RealityDetonatorBlock(AbstractBlock.Settings.copy(Blocks.OBSIDIAN)));

    public static final BlockEntityType<RealityDetonatorBlockEntity> REALITY_DETONATOR_TYPE = registerBlockEntity(
            "reality_detonator_entity",
            RealityDetonatorBlockEntity::new, REALITY_DETONATOR);

    public static final Block PURE_VOID_GENERATING = registerBlock("pure_void_generating",
            new PureVoidGeneratingBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK)));
    // to add more blocks go to 9:32 in this video: https://www.youtube.com/watch?v=6DY372RYNfE&list=PLKGarocXCE1EeLZggaXPJaARxnAbUD8Y_&index=4

    private static Block registerBlock(String name, Block block) {
        return registerBlock(name, block, ModItemGroup.VOIDEDHOPES_TAB);
    }

    private static Block registerBlock(String name, Block block, ItemGroup tab) {
        registerBlockItem(name, block, tab);
        return Registry.register(Registries.BLOCK, new Identifier(VoidedHopes.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block, ItemGroup tab) {
        Registry.register(Registries.ITEM, VoidedHopes.id(name),
                new BlockItem(block, new FabricItemSettings().group(tab)));
    }

    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, VoidedHopes.id(id),
                FabricBlockEntityTypeBuilder.create(factory, blocks).build());
    }

    public static void registerModBlocks() {
        VoidedHopes.LOGGER.debug("Registering ModBlocks for " + VoidedHopes.MOD_ID);
    }
}