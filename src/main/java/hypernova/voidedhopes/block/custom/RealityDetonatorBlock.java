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
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(1.0801249999999998, 1.465841337200402e-16, 0.4033125, 1.2734999999999999, 0.19337499999999994, 0.5966874999999999), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.4033125, 1.465841337200402e-16, -0.27349999999999997, 0.5966874999999999, 0.19337499999999994, -0.08012499999999989), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.08012499999999989, 1.465841337200402e-16, 0.4033125, 0.11325000000000002, 1.2569374999999998, 0.5966874999999999), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8867499999999999, 1.465841337200402e-16, 0.4033125, 1.0801249999999998, 1.2569374999999998, 0.5966874999999999), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.4033125, 1.465841337200402e-16, -0.08012499999999989, 0.5966874999999999, 1.2569374999999998, 0.11325000000000002), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.4033125, 1.465841337200402e-16, 0.8867499999999999, 0.5966874999999999, 1.2569374999999998, 1.0801249999999998), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.030781250000000038, 1.2569374999999998, 0.030781250000000038, 0.20709374999999997, 1.353625, 0.20709375000000002), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.11325000000000002, 1.1602499999999998, 0.4033125, 0.875375, 1.2569374999999998, 0.5966874999999999), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.4033125, 1.1602499999999998, 0.11325000000000002, 0.5966874999999999, 1.2569374999999998, 0.4033125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.4033125, 1.1602499999999998, 0.5966874999999999, 0.5966874999999999, 1.2569374999999998, 0.8867499999999999), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.030781250000000038, 1.0635625, 0.030781250000000038, 0.9692187499999999, 1.1602499999999998, 0.96921875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.26112500000000005, -0.0028437500000000073, 0.26112500000000005, 0.738875, 0.1763125, 0.738875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5966874999999999, 1.1602499999999998, 0.5966874999999999, 1.0801249999999998, 1.2569374999999998, 1.0801249999999998), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.40331249999999996, 1.465841337200402e-16, 1.0801249999999998, 0.5966875000000001, 0.19337499999999994, 1.2734999999999999), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.27349999999999997, 1.465841337200402e-16, 0.4033125, -0.08012499999999989, 0.19337499999999994, 0.5966874999999999), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.08012499999999989, 1.1602499999999998, 0.5966874999999999, 0.4033125, 1.2569374999999998, 1.0801249999999998), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.08012499999999989, 1.1602499999999998, -0.08012499999999989, 0.4033125, 1.2569374999999998, 0.4033125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5966874999999999, 1.1602499999999998, -0.08012499999999989, 1.0801249999999998, 1.2569374999999998, 0.4033125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.20709374999999997, 1.20859375, 0.7929062499999999, 0.7929062499999999, 1.30528125, 0.852625), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1473750000000001, 1.20859375, 0.20709375000000002, 0.852625, 1.30528125, 0.7929062499999999), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.20709374999999997, 1.20859375, 0.1473750000000001, 0.7929062499999999, 1.30528125, 0.20709375000000002), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.030781250000000038, 1.2569374999999998, 0.20709375000000002, 0.1473750000000001, 1.353625, 0.7929062499999999), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.030781250000000038, 1.2569374999999998, 0.7929062499999999, 0.20709374999999997, 1.353625, 0.96921875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.20709374999999997, 1.2569374999999998, 0.030781250000000038, 0.7929062499999999, 1.353625, 0.1473750000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.7929062499999999, 1.2569374999999998, 0.7929062499999999, 0.9692187499999999, 1.353625, 0.96921875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.7929062499999999, 1.2569374999999998, 0.030781250000000038, 0.9692187499999999, 1.353625, 0.20709375000000002), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.852625, 1.2569374999999998, 0.20709375000000002, 0.9692187499999999, 1.353625, 0.7929062499999999), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.20709374999999997, 1.2569374999999998, 0.852625, 0.7929062499999999, 1.353625, 0.96921875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.31800000000000006, 0.1763125, 0.3180000000000001, 0.6819999999999999, 0.8844062499999997, 0.682), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.26112500000000005, 0.8844062499999997, 0.26112500000000005, 0.738875, 1.0635625, 0.738875), BooleanBiFunction.OR);

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
