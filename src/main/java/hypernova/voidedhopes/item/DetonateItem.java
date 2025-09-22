package hypernova.voidedhopes.item;

import hypernova.voidedhopes.AzuraThingies.RiftRenderer;
import hypernova.voidedhopes.AzuraThingies.RiftRendererManager;
import hypernova.voidedhopes.VoidedHopes;
import hypernova.voidedhopes.block.ModBlocks;
import hypernova.voidedhopes.client.ModSound;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class DetonateItem extends Item {
    public static final ParticleEmitterInfo VFX = new ParticleEmitterInfo(new Identifier("voided_hopes", "realites_end_remade"));
    public DetonateItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        if (!world.isClient() && world.getBlockState(blockPos).getBlock().equals(ModBlocks.REALITY_DETONATOR)) {
            ParticleEmitterInfo playingVfx = VFX.clone().position(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5);

            new Object() {
                private int ticks = 0;

                public void startShakes() {
                    ServerTickEvents.END_SERVER_TICK.register((server) -> {
                        this.ticks++;

                        // IM WORKING ON THIS STILL FUCK FACE!!!

                        // Box check = new Box(blockPos.getX() + 750f, blockPos.getY() + 200f, blockPos.getZ() + 750f, blockPos.getX() - 750f, blockPos.getY() - 200, blockPos.getZ() - 750f);
                        // PlayerEntity targets = world.getEntitiesByType(PlayerEntity.class, check, predicate);

                        if (ticks >= 73 && ticks <= 210) {
                            assert player != null;
                            VoidedHopes.screenshake(player, 0.135f);
                        } else if (ticks >= 210 && ticks <= 365) {
                            assert player != null;
                            VoidedHopes.screenshake(player, 0.1f);
                        }
                    });
                }
            }.startShakes();

            //AAALevel.addParticle(world, 5000, playingVfx);
            RiftRendererManager.addRift(blockPos.toCenterPos().add(0,100,0), 0);
            System.out.println("Starting vfx");
            world.playSound(null, blockPos, ModSound.REALITY_DETONATE, SoundCategory.MASTER, 1, 1);
        }
        return ActionResult.SUCCESS;
    }
}
