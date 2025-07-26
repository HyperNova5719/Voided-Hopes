package hypernova.voidedhopes.item;

import hypernova.voidedhopes.block.ModBlocks;
import hypernova.voidedhopes.client.ModSound;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TestItem extends Item {
    public static final ParticleEmitterInfo VFX = new ParticleEmitterInfo(new Identifier("voided_hopes", "realites_end_remade"));
    public TestItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        if (!world.isClient() && world.getBlockState(blockPos).getBlock().equals(ModBlocks.REALITY_DETONATOR)) {
            ParticleEmitterInfo playingVfx = VFX.clone().position(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5);

            AAALevel.addParticle(world, 5000, playingVfx);
            System.out.println("Starting vfx");
            world.playSound(null, blockPos, ModSound.REALITY_DETONATE, SoundCategory.MASTER, 1, 1);
        }
        return ActionResult.SUCCESS;
    }
}
