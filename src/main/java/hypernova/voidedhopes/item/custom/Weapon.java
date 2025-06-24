package hypernova.voidedhopes.item.custom;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class Weapon extends SwordItem {
    private static final WeaponMaterial INSTANCE = new WeaponMaterial();

    public Weapon(Settings settings) {
        super(INSTANCE, 1, -3.4F, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        new Object() {
            private int ticks;

            public void startDelay() {
                ServerTickEvents.END_SERVER_TICK.register((server) -> {
                    this.ticks++;
                    while (ticks != 40) {
                        user.setVelocity(Vec3d.ZERO);
                        user.fallDistance = 0;
                    }
                });
            }
        }.startDelay();

        int var = 1; // make this variable whatever you want
        for (int i = 0; i < var; i++) {
            summonEntity(user);
        }

        return super.use(world, user, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // if you wanna do the enchantment thing, this is the code

//        Random random = new Random();
//        int randomVar = random.nextInt(0, 100);
//        if (EnchantmentHelper.getLevel(EnchantmentRegistry.ENCHANTMENTNAMEHERE, stack) <= 0) {
//            if (target instanceof PlayerEntity) {
//                summonEntity(attacker);
//            } else if (randomVar < 25) {
//                summonEntity(attacker);
//            }
//        }
        return super.postHit(stack, target, attacker);
    }

    public static void summonEntity(LivingEntity entity) {
        World world = entity.getWorld();
        Random random = new Random();

        BlockPos pos = entity.getBlockPos().add(random.nextInt(11) - 5, random.nextInt(5) - 2, random.nextInt(11) - 5);

        WitherSkeletonEntity skele = EntityType.WITHER_SKELETON.create(world);
        skele.setPos(pos.getX(), pos.getY(), pos.getZ());
        // add other bits of code here if you want
        world.spawnEntity(skele);
    }

    private static class WeaponMaterial implements ToolMaterial {
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
            // return Ingredient.ofItems(ModItems.WHATEVERTHEFUCKYOUWANTHERE);
            return null;
        }
    }
}
