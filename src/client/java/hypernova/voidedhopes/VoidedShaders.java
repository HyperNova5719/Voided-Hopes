package hypernova.voidedhopes;

import hypernova.voidedhopes.block.ModBlocks;
import hypernova.voidedhopes.renderers.PureVoidBlockRenderer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

public class VoidedShaders {
    public static RenderLayer end_decoration_shader_layer;

    protected static RenderPhase.Shader END_DECORATION_SHADER;

    public static void init() {
        CoreShaderRegistrationCallback.EVENT.register(ctx -> {
            ctx.register(VoidedHopes.id("rendertype_pure_void"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, shaderProgram -> {
                END_DECORATION_SHADER = new RenderPhase.Shader(() -> shaderProgram);

                end_decoration_shader_layer = RenderLayer.of(
                        "rendertype_pure_void",
                        VertexFormats.POSITION,
                        VertexFormat.DrawMode.QUADS,
                        256,
                        false,
                        false,
                        RenderLayer.MultiPhaseParameters.builder().shader(END_DECORATION_SHADER)
                                .texture(
                                        (RenderPhase.TextureBase)RenderPhase.Textures.create()
                                                .add(PureVoidBlockRenderer.SKY_TEXTURE, false, false)
                                                .add(PureVoidBlockRenderer.PORTAL_TEXTURE, false, false).build()
                                ).build(false));


                BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURE_VOID, end_decoration_shader_layer);
            });
        });
    }
}
