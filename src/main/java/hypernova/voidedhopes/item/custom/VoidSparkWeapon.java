package hypernova.voidedhopes.item.custom;


import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class VoidSparkWeapon extends SwordItem {
    private static final WeaponMaterial INSTANCE = new WeaponMaterial();


    public VoidSparkWeapon(Settings settings) {
        super(INSTANCE, 1, -2.4F, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        FX voidSparkUse = FXHelper.getFX(new Identifier("photon:voidsparkused_test2"));

        if (!world.isClient) {
            new EntityEffect(voidSparkUse, world, user, EntityEffect.AutoRotate.NONE).start();
        }
        new Object() {
            private int ticks;


            public void startDelay() {
                ServerTickEvents.END_SERVER_TICK.register((server) -> {
                    this.ticks++;
                    if (ticks <= 40) {
                        user.setVelocity(Vec3d.ZERO);
                        user.fallDistance = 0;

                    }

                });
            }


        }.startDelay();



        int var = 2; // make this variable whatever you want
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
        FX VoidSparkSummon = FXHelper.getFX(new Identifier("photon:voidspark_summoned_mob"));
        World world = entity.getWorld();
        Random random = new Random();

        BlockPos pos = entity.getBlockPos().add(random.nextInt(11) - 5, random.nextInt(5) - 2, random.nextInt(11) - 5);

        WitherSkeletonEntity skele = EntityType.WITHER_SKELETON.create(world);
        skele.setPos(pos.getX(), pos.getY(), pos.getZ());

        world.spawnEntity(skele);
        new EntityEffect(VoidSparkSummon, world, skele, EntityEffect.AutoRotate.LOOK).start();

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
