package hypernova.voidedhopes.util.datagen;

import hypernova.voidedhopes.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class ModelGeneration extends FabricModelProvider {
    public ModelGeneration(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PURE_VOID);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MATRIX_VOID);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ENDER_SKY_BLOCK);
        // This does not seem to automatically generate the item models, which is odd, as it generally should.
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
