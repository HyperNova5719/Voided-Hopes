package hypernova.voidedhopes.particle;

import hypernova.voidedhopes.VoidedHopes;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModParticles {
public static final DefaultParticleType HERALD = FabricParticleTypes.simple();

public static void registerParticles() {
    Registry.register(Registries.PARTICLE_TYPE, VoidedHopes.id("herald"), HERALD);
}
}
