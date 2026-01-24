package hypernova.voidedhopes.item.custom;

import dev.architectury.platform.Mod;
import hypernova.voidedhopes.AzuraThingies.Weapons.ModEffects;
import hypernova.voidedhopes.AzuraThingies.Weapons.RealityBaneCorruptionManager;
import hypernova.voidedhopes.VoidedHopes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class RealitiesBaneItem extends SwordItem {
    private static final RealitiesBaneMaterial INSTANCE = new RealitiesBaneMaterial();
    public float instability;

    public RealitiesBaneItem(Settings settings) {
        super(INSTANCE, 4, -2.55f, settings);
        instability = 0;
    }

    @Override
    public Text getName(ItemStack stack) {
        Text name = super.getName(stack);
        return Text.literal("").append(name).setStyle(name.getStyle().withFont((VoidedHopes.FONT_2)));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        instability *= 0.98f;
    }

    @Override
    public float getAttackDamage() {
        return 0.5f + (instability * 5f);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        instability += 0.6F;

        target.addStatusEffect(new StatusEffectInstance(ModEffects.CORRUPTION, (int) (60f * instability), 1, false, false));
        attacker.addStatusEffect(new StatusEffectInstance(ModEffects.CORRUPTION, (int) (40f * instability), 1, false, false));



        if(instability > 2.4f){
            attacker.damage(attacker.getDamageSources().outOfWorld(), instability);
        }

        return true;
    }


    public static class RealitiesBaneMaterial implements ToolMaterial {
        @Override
        public int getDurability() {
            return 0;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return 0;
        }

        @Override
        public float getAttackDamage() {
            return 6.0F;
        }

        @Override
        public int getMiningLevel() {
            return 0;
        }

        @Override
        public int getEnchantability() {
            return 0;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return null;
        }

    }
}
