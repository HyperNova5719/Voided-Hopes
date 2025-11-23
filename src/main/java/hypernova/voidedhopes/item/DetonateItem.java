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
            RiftRendererManager.addRift(blockPos.toCenterPos().add(0,-0.5, 0), 0);
            System.out.println("Starting vfx");
            world.playSound(null, blockPos, ModSound.REALITY_DETONATE, SoundCategory.MASTER, 1, 1);
        }
        return ActionResult.SUCCESS;
    }
}
