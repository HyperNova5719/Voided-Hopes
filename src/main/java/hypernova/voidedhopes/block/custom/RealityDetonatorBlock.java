package hypernova.voidedhopes.block.custom;

import hypernova.voidedhopes.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class RealityDetonatorBlock extends Block implements BlockEntityProvider {

    public VoxelShape makeShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.81875, 0, 0.446875, 0.925, 0.10625, 0.553125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.446875, 0, 0.07500000000000001, 0.553125, 0.10625, 0.18125000000000002), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.18125000000000002, 0, 0.446875, 0.2875, 0.690625, 0.553125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.7125, 0, 0.446875, 0.81875, 0.690625, 0.553125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.446875, 0, 0.18125000000000002, 0.553125, 0.690625, 0.2875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.446875, 0, 0.7125, 0.553125, 0.690625, 0.81875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.2421875, 0.690625, 0.2421875, 0.3390625, 0.74375, 0.3390625), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.2875, 0.6375, 0.446875, 0.70625, 0.690625, 0.553125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.446875, 0.6375, 0.2875, 0.553125, 0.690625, 0.446875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.446875, 0.6375, 0.553125, 0.553125, 0.690625, 0.7125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.2421875, 0.584375, 0.2421875, 0.7578125, 0.6375, 0.7578125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.36875, -0.0031250000000000444, 0.36875, 0.63125, 0.09531249999999991, 0.63125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.553125, 0.6375, 0.553125, 0.81875, 0.690625, 0.81875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.81875, 0, 0.446875, 0.925, 0.10625, 0.553125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.81875, 0, 0.446875, 0.925, 0.10625, 0.553125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.18125000000000002, 0.6375, 0.553125, 0.446875, 0.690625, 0.81875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.18125000000000002, 0.6375, 0.18125000000000002, 0.446875, 0.690625, 0.446875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.553125, 0.6375, 0.18125000000000002, 0.81875, 0.690625, 0.446875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3390625, 0.6640625, 0.6609375, 0.6609375, 0.7171875, 0.69375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.30625, 0.6640625, 0.3390625, 0.69375, 0.7171875, 0.6609375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3390625, 0.6640625, 0.30625, 0.6609375, 0.7171875, 0.3390625), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.2421875, 0.690625, 0.3390625, 0.30625, 0.74375, 0.6609375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.2421875, 0.690625, 0.6609375, 0.3390625, 0.74375, 0.7578125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3390625, 0.690625, 0.2421875, 0.6609375, 0.74375, 0.30625), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.6609375, 0.690625, 0.6609375, 0.7578125, 0.74375, 0.7578125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.6609375, 0.690625, 0.2421875, 0.7578125, 0.74375, 0.3390625), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.69375, 0.690625, 0.3390625, 0.7578125, 0.74375, 0.6609375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3390625, 0.690625, 0.69375, 0.6609375, 0.74375, 0.7578125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.4, 0.09531249999999991, 0.4, 0.6, 0.4859374999999999, 0.6), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.36875, 0.48749999999999993, 0.36875, 0.63125, 0.5859374999999999, 0.63125), BooleanBiFunction.OR);

        return shape;
    }

    public RealityDetonatorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return makeShape();
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
