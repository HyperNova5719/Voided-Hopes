package hypernova.voidedhopes.item;

import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TestItem extends Item {
    public static final ParticleEmitterInfo HERALD = new ParticleEmitterInfo(new Identifier("voided_hopes", "realites_end_remade"));


    public TestItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        if (world.isClient()){
            AAALevel.addParticle(world, true, HERALD.clone().position(blockPos.getX() + 0.5d, blockPos.getY() + 1d, blockPos.getZ() + 0.5d));
        }
        return super.useOnBlock(context);
    }
}
