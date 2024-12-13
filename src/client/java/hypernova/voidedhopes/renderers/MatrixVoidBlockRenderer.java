package hypernova.voidedhopes.renderers;

import hypernova.voidedhopes.VoidedHopes;
import hypernova.voidedhopes.VoidedShaders;
import hypernova.voidedhopes.block.custom.MatrixVoidBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;

public class MatrixVoidBlockRenderer implements BlockEntityRenderer<MatrixVoidBlockEntity> {
    public static final Identifier SKY_TEXTURE = VoidedHopes.id("textures/environment/white_end_sky.png");
    public static final Identifier PORTAL_TEXTURE = VoidedHopes.id("textures/entity/pure_void.png");

    public MatrixVoidBlockRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(MatrixVoidBlockEntity entity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
        this.renderSides(entity, matrix4f, vertexConsumers.getBuffer(this.getLayer()));
    }

    private void renderSides(MatrixVoidBlockEntity entity, Matrix4f matrix, VertexConsumer vertexConsumer) {
        float f = this.getBottomYOffset();
        float g = this.getTopYOffset();
        this.renderSide(entity, matrix, vertexConsumer, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, Direction.SOUTH);
        this.renderSide(entity, matrix, vertexConsumer, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Direction.NORTH);
        this.renderSide(entity, matrix, vertexConsumer, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.EAST);
        this.renderSide(entity, matrix, vertexConsumer, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.WEST);
        this.renderSide(entity, matrix, vertexConsumer, 0.0f, 1.0f, f, f, 0.0f, 0.0f, 1.0f, 1.0f, Direction.DOWN);
        this.renderSide(entity, matrix, vertexConsumer, 0.0f, 1.0f, g, g, 1.0f, 1.0f, 0.0f, 0.0f, Direction.UP);
    }

    private void renderSide(MatrixVoidBlockEntity entity, Matrix4f model, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, Direction side) {
        if(true) {
            vertices.vertex(model, x1, y1, z1).next();
            vertices.vertex(model, x2, y1, z2).next();
            vertices.vertex(model, x2, y2, z3).next();
            vertices.vertex(model, x1, y2, z4).next();
        }
    }

    protected float getTopYOffset() {
        return 1f;
    }

    protected float getBottomYOffset() {
        return 0f;
    }

    public RenderLayer getLayer() {
        return VoidedShaders.end_decoration_shader_layer;
    }
}
