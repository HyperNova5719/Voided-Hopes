package hypernova.voidedhopes.AzuraThingies.Weapons;

import hypernova.voidedhopes.VoidedHopes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static StatusEffect CORRUPTION;

    public static StatusEffect registerStatusEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(VoidedHopes.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        CORRUPTION = registerStatusEffect("corruption", new Corruption(StatusEffectCategory.HARMFUL, 0x04C3EE));
    }
}