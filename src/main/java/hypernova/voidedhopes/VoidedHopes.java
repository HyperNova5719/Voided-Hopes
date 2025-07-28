package hypernova.voidedhopes;

import hypernova.voidedhopes.accessors.VoidedPlayerEntity;
import hypernova.voidedhopes.block.ModBlocks;
import hypernova.voidedhopes.client.ModSound;
import hypernova.voidedhopes.item.ModItems;
import hypernova.voidedhopes.particle.ModParticles;
import hypernova.voidedhopes.registry.PacketRegistry;
import hypernova.voidedhopes.world.ModDimensions;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
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

		PacketRegistry.registerC2S();
	}

	public static Identifier id(String name){
		return Identifier.of(MOD_ID, name);
	}

	public static void screenshake(PlayerEntity player, float strength)
	{
		if(player.getWorld().isClient && player instanceof VoidedPlayerEntity winged)
			winged.addScreenshake(strength);
		else if(player instanceof ServerPlayerEntity serverPlayer)
		{
			PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
			buf.writeFloat(strength);
			ServerPlayNetworking.send(serverPlayer, PacketRegistry.SCREENSHAKE_PACKET_ID, buf);
		}
	}
}