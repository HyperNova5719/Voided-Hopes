package hypernova.voidedhopes.client;

import hypernova.voidedhopes.VoidedHopes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSound {
    public static final SoundEvent REALITY_DETONATE = registerSoundEvent("block.voidedhopes.reality_detonator.detonate");

    public static void register() {

    }

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = VoidedHopes.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}