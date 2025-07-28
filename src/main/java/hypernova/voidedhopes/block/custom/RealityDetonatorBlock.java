package hypernova.voidedhopes.block.custom;

import hypernova.voidedhopes.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class RealityDetonatorBlock extends Block implements BlockEntityProvider {

    public static final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 24, 13);

    public RealityDetonatorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 0f;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return ModBlocks.REALITY_DETONATOR_TYPE.instantiate(pos, state);
    }
}
