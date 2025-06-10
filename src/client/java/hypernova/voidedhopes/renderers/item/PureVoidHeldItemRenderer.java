package hypernova.voidedhopes.renderers.item;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class PureVoidHeldItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    @Override
    public void render(ItemStack stack, ModelTransformation mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        // TODO: I might want to use the shader for items instead of the plain textures we're using rn
        // yo nico i have no clue wtf you wanna do here so imma just leave this like this for now -crab
    }

    @Override
    public void render(ItemStack itemStack, ModelTransformationMode modelTransformationMode, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int i1) {

    }
}
