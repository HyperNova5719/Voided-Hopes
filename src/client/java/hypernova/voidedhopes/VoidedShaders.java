package hypernova.voidedhopes;

import hypernova.voidedhopes.block.ModBlocks;
import hypernova.voidedhopes.renderers.EnderSkyBlockRenderer;
import hypernova.voidedhopes.renderers.MatrixVoidBlockRenderer;
import hypernova.voidedhopes.renderers.PureVoidBlockRenderer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

public class VoidedShaders {
    public static RenderLayer PURE_VOID_LAYER;
    protected static RenderPhase.Shader PURE_VOID_SHADER;

    public static RenderLayer MATRIX_VOID_LAYER;
    protected static RenderPhase.Shader MATRIX_VOID_SHADER;

    public static RenderLayer ENDER_SKY_BLOCK_LAYER;
    protected static RenderPhase.Shader ENDER_SKY_BLOCK_SHADER;

    public static void init() {
        CoreShaderRegistrationCallback.EVENT.register(ctx -> {
            ctx.register(VoidedHopes.id("rendertype_pure_void"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, shaderProgram -> {
                PURE_VOID_SHADER = new RenderPhase.Shader(() -> shaderProgram);

                PURE_VOID_LAYER = RenderLayer.of(
                        "rendertype_pure_void",
                        VertexFormats.POSITION,
                        VertexFormat.DrawMode.QUADS,
                        256,
                        false,
                        false,
                        RenderLayer.MultiPhaseParameters.builder().shader(PURE_VOID_SHADER)
                                .texture(
                                        (RenderPhase.TextureBase)RenderPhase.Textures.create()
                                                .add(PureVoidBlockRenderer.SKY_TEXTURE, false, false)
                                                .add(PureVoidBlockRenderer.PORTAL_TEXTURE, false, false).build()
                                ).build(false));


                BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURE_VOID, PURE_VOID_LAYER);
            });

            ctx.register(VoidedHopes.id("rendertype_matrix_void"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, shaderProgram -> {
                MATRIX_VOID_SHADER = new RenderPhase.Shader(() -> shaderProgram);

                MATRIX_VOID_LAYER = RenderLayer.of(
                        "rendertype_matrix_void",
                        VertexFormats.POSITION,
                        VertexFormat.DrawMode.QUADS,
                        256,
                        false,
                        false,
                        RenderLayer.MultiPhaseParameters.builder().shader(MATRIX_VOID_SHADER)
                                .texture(
                                        (RenderPhase.TextureBase)RenderPhase.Textures.create()
                                                .add(MatrixVoidBlockRenderer.SKY_TEXTURE, false, false)
                                                .add(MatrixVoidBlockRenderer.PORTAL_TEXTURE, false, false).build()
                                ).build(false));


                BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MATRIX_VOID, MATRIX_VOID_LAYER);
            });

            ctx.register(VoidedHopes.id("rendertype_ender_sky"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, shaderProgram -> {
                ENDER_SKY_BLOCK_SHADER = new RenderPhase.Shader(() -> shaderProgram);

                ENDER_SKY_BLOCK_LAYER = RenderLayer.of(
                        "rendertype_ender_sky",
                        VertexFormats.POSITION,
                        VertexFormat.DrawMode.QUADS,
                        256,
                        false,
                        false,
                        RenderLayer.MultiPhaseParameters.builder().shader(ENDER_SKY_BLOCK_SHADER)
                                .texture(
                                        (RenderPhase.TextureBase)RenderPhase.Textures.create()
                                                .add(EnderSkyBlockRenderer.SKY_TEXTURE, false, false).build()
                                ).build(false));


                BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ENDER_SKY_BLOCK, ENDER_SKY_BLOCK_LAYER);
            });
        });
    }
}
