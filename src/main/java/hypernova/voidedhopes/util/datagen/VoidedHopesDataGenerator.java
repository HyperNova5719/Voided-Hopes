package hypernova.voidedhopes.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class VoidedHopesDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModelGeneration::new);
    }
}
