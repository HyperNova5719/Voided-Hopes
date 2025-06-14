package hypernova.voidedhopes.client;

import hypernova.voidedhopes.block.ModBlocks;
import hypernova.voidedhopes.client.renderers.block.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class VoidedHopesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		VoidedHopesShaders.init();

		BlockEntityRendererFactories.register(ModBlocks.PURE_VOID_TYPE, PureVoidBlockRenderer::new);
		//BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.PURE_VOID.asItem(), new PureVoidHeldItemRenderer());
		BlockEntityRendererFactories.register(ModBlocks.MATRIX_VOID_TYPE, MatrixVoidBlockRenderer::new);
		BlockEntityRendererFactories.register(ModBlocks.ENDER_SKY_TYPE, EnderSkyBlockRenderer::new);

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.REALITY_DETONATOR, RenderLayer.getCutout());
	}
}