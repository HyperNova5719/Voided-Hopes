package hypernova.voidedhopes;

import hypernova.voidedhopes.block.ModBlocks;
import hypernova.voidedhopes.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Very important comment
public class VoidedHopes implements ModInitializer {
	public static final String MOD_ID = "voided_hopes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

	}

	public static Identifier id(String name){
		return Identifier.of(MOD_ID, name);
	}
}