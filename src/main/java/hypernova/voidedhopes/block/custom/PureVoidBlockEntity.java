package hypernova.voidedhopes.block.custom;

import hypernova.voidedhopes.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class PureVoidBlockEntity extends BlockEntity {

    public PureVoidBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.PURE_VOID_TYPE, pos, state);
    }
}

