package hypernova.voidedhopes.client;

import hypernova.voidedhopes.AzuraThingies.LazuliLib.LazuliShaderRegistry;
import hypernova.voidedhopes.VoidedHopes;
import hypernova.voidedhopes.block.ModBlocks;
import hypernova.voidedhopes.client.renderers.block.EnderSkyBlockRenderer;
import hypernova.voidedhopes.client.renderers.block.MatrixVoidBlockRenderer;
import hypernova.voidedhopes.client.renderers.block.PureVoidBlockRenderer;
import hypernova.voidedhopes.client.shader.VoidedHopesShader;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

public class VoidedHopesShaders {
    public static VoidedHopesShader PURE_VOID_SHADER;
    public static VoidedHopesShader MATRIX_VOID_SHADER;
    public static VoidedHopesShader ENDER_SKY_BLOCK_SHADER;

    public static String PURE_VOID_LAZULI_SHADER = "rendertype_pure_void";

    public static void init() {
        LazuliShaderRegistry.registerShader(PURE_VOID_LAZULI_SHADER, "voided_hopes", VertexFormats.POSITION);

        PURE_VOID_SHADER = new VoidedHopesShader(VoidedHopes.id("rendertype_pure_void"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL)
                .setRenderLayerFactory(() -> shaderProgram -> RenderLayer.of(
                        "rendertype_pure_void",
                        VertexFormats.POSITION,
                        VertexFormat.DrawMode.QUADS,
                        256,
                        false,
                        false,
                        RenderLayer.MultiPhaseParameters.builder()
                                .program(shaderProgram)
                                .texture(
                                        RenderPhase.Textures.create()
                                                .add(PureVoidBlockRenderer.SKY_TEXTURE, false, false)
                                                .add(PureVoidBlockRenderer.PORTAL_TEXTURE, false, false).build()
                                ).build(false)))
                .setAppliedBlocks(ModBlocks.PURE_VOID);

        MATRIX_VOID_SHADER = new VoidedHopesShader(VoidedHopes.id("rendertype_matrix_void"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL)
                .setRenderLayerFactory(() -> shaderProgram -> RenderLayer.of(
                        "rendertype_matrix_void",
                        VertexFormats.POSITION,
                        VertexFormat.DrawMode.QUADS,
                        256,
                        false,
                        false,
                        RenderLayer.MultiPhaseParameters.builder()
                                .program(shaderProgram)
                                .texture(
                                        RenderPhase.Textures.create()
                                                .add(MatrixVoidBlockRenderer.SKY_TEXTURE, false, false)
                                                .add(MatrixVoidBlockRenderer.PORTAL_TEXTURE, false, false).build()
                                ).build(false)))
                .setAppliedBlocks(ModBlocks.MATRIX_VOID);

        ENDER_SKY_BLOCK_SHADER =  new VoidedHopesShader(VoidedHopes.id("rendertype_ender_sky"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL)
                .setRenderLayerFactory(() -> shaderProgram -> RenderLayer.of(
                        "rendertype_ender_sky",
                        VertexFormats.POSITION,
                        VertexFormat.DrawMode.QUADS,
                        256,
                        false,
                        false,
                        RenderLayer.MultiPhaseParameters.builder()
                                .program(shaderProgram)
                                .texture(
                                        RenderPhase.Textures.create()
                                                .add(EnderSkyBlockRenderer.SKY_TEXTURE, false, false).build()
                                ).build(false)))
                        .setAppliedBlocks(ModBlocks.ENDER_SKY_BLOCK);

        CoreShaderRegistrationCallback.EVENT.register(ctx -> {
            PURE_VOID_SHADER.register(ctx);
            MATRIX_VOID_SHADER.register(ctx);
            ENDER_SKY_BLOCK_SHADER.register(ctx);
        });
    }
}
