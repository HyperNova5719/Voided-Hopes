package hypernova.voidedhopes;

import hypernova.voidedhopes.block.ModBlocks;
import hypernova.voidedhopes.client.ModSound;
import hypernova.voidedhopes.item.ModItems;
import hypernova.voidedhopes.particle.ModParticles;
import hypernova.voidedhopes.world.ModDimensions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoidedHopes implements ModInitializer {
	public static final String MOD_ID = "voided_hopes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModDimensions.register();
		ModParticles.registerParticles();
		ModSound.register();
	}

	public static Identifier id(String name){
		return Identifier.of(MOD_ID, name);
	}
}