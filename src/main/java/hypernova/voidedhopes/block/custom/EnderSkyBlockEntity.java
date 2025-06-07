package hypernova.voidedhopes.block.custom;

import hypernova.voidedhopes.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class EnderSkyBlockEntity extends BlockEntity {
    public EnderSkyBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.ENDER_SKY_TYPE, pos, state);
    }
}
