package hypernova.voidedhopes.renderers.block;


import hypernova.voidedhopes.VoidedShaders;
import hypernova.voidedhopes.block.custom.EnderSkyBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class EnderSkyBlockRenderer implements BlockEntityRenderer<EnderSkyBlockEntity> {
    public static final Identifier SKY_TEXTURE = Identifier.of("minecraft", "textures/environment/end_sky.png");

    public EnderSkyBlockRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(EnderSkyBlockEntity entity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
        this.renderSides(entity, matrix4f, vertexConsumers.getBuffer(this.getLayer()));
    }

    private void renderSides(EnderSkyBlockEntity entity, Matrix4f matrix, VertexConsumer vertexConsumer) {
        float f = this.getBottomYOffset();
        float g = this.getTopYOffset();
        this.renderSide(entity, matrix, vertexConsumer, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, Direction.SOUTH);
        this.renderSide(entity, matrix, vertexConsumer, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Direction.NORTH);
        this.renderSide(entity, matrix, vertexConsumer, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.EAST);
        this.renderSide(entity, matrix, vertexConsumer, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.WEST);
        this.renderSide(entity, matrix, vertexConsumer, 0.0f, 1.0f, f, f, 0.0f, 0.0f, 1.0f, 1.0f, Direction.DOWN);
        this.renderSide(entity, matrix, vertexConsumer, 0.0f, 1.0f, g, g, 1.0f, 1.0f, 0.0f, 0.0f, Direction.UP);
    }

    private void renderSide(EnderSkyBlockEntity entity, Matrix4f model, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, Direction side) {
        vertices.vertex(model, x1, y1, z1).next();
        vertices.vertex(model, x2, y1, z2).next();
        vertices.vertex(model, x2, y2, z3).next();
        vertices.vertex(model, x1, y2, z4).next();
    }

    protected float getTopYOffset() {
        return 1f;
    }

    protected float getBottomYOffset() {
        return 0f;
    }

    public RenderLayer getLayer() {
        return VoidedShaders.ENDER_SKY_BLOCK_LAYER;
    }

    @Override
    public boolean isInRenderDistance(EnderSkyBlockEntity blockEntity, Vec3d pos) {
        return true;
    }
}
