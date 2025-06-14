package hypernova.voidedhopes.world;

import hypernova.voidedhopes.VoidedHopes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

public class ModDimensions {
    private static final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(RegistryKeys.DIMENSION, VoidedHopes.id("the_void"));
    private static final RegistryKey<World> WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, VoidedHopes.id("the_void"));
    private static final RegistryKey<DimensionType> DIMENSION_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, VoidedHopes.id("the_void_type"));

    public static void register() {
    }
}
