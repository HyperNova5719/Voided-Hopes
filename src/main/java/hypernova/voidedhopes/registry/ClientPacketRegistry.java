package hypernova.voidedhopes.registry;

import hypernova.voidedhopes.accessors.VoidedPlayerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import static hypernova.voidedhopes.registry.PacketRegistry.*;

@SuppressWarnings("CodeBlock2Expr")
@Environment(EnvType.CLIENT)
public class ClientPacketRegistry {
    public static void registerS2C() {
        ClientPlayNetworking.registerGlobalReceiver(SCREENSHAKE_PACKET_ID, (((client, handler, buf, responseSender) -> {
            float strength = buf.readFloat();
            client.execute(() -> {
                if(client.player instanceof VoidedPlayerEntity winged)
                    winged.addScreenshake(strength);
            });
        })));
    }
}
