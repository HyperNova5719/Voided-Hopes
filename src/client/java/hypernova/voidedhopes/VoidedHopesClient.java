package hypernova.voidedhopes;

import hypernova.voidedhopes.block.ModBlocks;
import hypernova.voidedhopes.renderers.EnderSkyBlockRenderer;
import hypernova.voidedhopes.renderers.MatrixVoidBlockRenderer;
import hypernova.voidedhopes.renderers.PureVoidBlockRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class VoidedHopesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		VoidedShaders.init();

		BlockEntityRendererFactories.register(ModBlocks.PURE_VOID_TYPE, PureVoidBlockRenderer::new);
		BlockEntityRendererFactories.register(ModBlocks.ENDER_SKY_TYPE, EnderSkyBlockRenderer::new);
	}
}