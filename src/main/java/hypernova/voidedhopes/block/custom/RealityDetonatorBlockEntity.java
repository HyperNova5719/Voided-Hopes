package hypernova.voidedhopes.block.custom;

import hypernova.voidedhopes.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class RealityDetonatorBlockEntity extends BlockEntity {


    public RealityDetonatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.REALITY_DETONATOR_TYPE, pos, state);
    }
}