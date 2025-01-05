package hypernova.voidedhopes.world;

import hypernova.voidedhopes.VoidedHopes;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

public class ModDimensions {
    private static final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(Registry.DIMENSION_KEY, VoidedHopes.id("the_void"));
    private static final RegistryKey<World> WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, VoidedHopes.id("the_void"));
    private static final RegistryKey<DimensionType> DIMENSION_TYPE = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, VoidedHopes.id("the_void_type"));

    public static void register() {
    }
}
