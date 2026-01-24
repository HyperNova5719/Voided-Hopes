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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RealitiesBaneItem extends SwordItem {
    private static final RealitiesBaneMaterial INSTANCE = new RealitiesBaneMaterial();
    public float instability;

    public RealitiesBaneItem(Settings settings) {
        super(INSTANCE, 3, -2.4f, settings);
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
        return 0.5f + (instability * 7f);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        instability += 0.6F;

        Vec3d boost = target.getPos().subtract(attacker.getPos()).normalize();


        target.addStatusEffect(new StatusEffectInstance(ModEffects.CORRUPTION, (int) (60f * instability), 1, false, false));
        attacker.addStatusEffect(new StatusEffectInstance(ModEffects.CORRUPTION, (int) (40f * instability), 1, false, false));

        Vec3d speed = target.getVelocity();

        speed = speed.add(boost.multiply(instability * 0.12));
        target.setVelocity(speed);

        speed = attacker.getVelocity();

        speed = speed.add(boost.multiply(instability * 0.06));
        attacker.setVelocity(speed);


        target.velocityDirty = true;
        target.velocityModified = true;

        if(instability > 2.0f){
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
