package hypernova.voidedhopes;

import hypernova.voidedhopes.block.ModBlocks;
import hypernova.voidedhopes.renderers.RiftRenderer;
import hypernova.voidedhopes.renderers.block.EnderSkyBlockRenderer;
import hypernova.voidedhopes.renderers.block.MatrixVoidBlockRenderer;
import hypernova.voidedhopes.renderers.block.PureVoidBlockRenderer;
import hypernova.voidedhopes.renderers.item.PureVoidHeldItemRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.TransparentBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class VoidedHopesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		VoidedShaders.init();
		RiftRenderer.register();
		BlockEntityRendererFactories.register(ModBlocks.PURE_VOID_TYPE, PureVoidBlockRenderer::new);
		//BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.PURE_VOID.asItem(), new PureVoidHeldItemRenderer());
		BlockEntityRendererFactories.register(ModBlocks.MATRIX_VOID_TYPE, MatrixVoidBlockRenderer::new);
		BlockEntityRendererFactories.register(ModBlocks.ENDER_SKY_TYPE, EnderSkyBlockRenderer::new);

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.REALITY_DETONATOR, RenderLayer.getCutout());
	}
}