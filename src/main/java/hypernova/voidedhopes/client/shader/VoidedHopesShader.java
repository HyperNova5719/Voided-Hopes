package hypernova.voidedhopes.client.shader;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.block.Block;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;

public class VoidedHopesShader {
    private final Identifier identifier;
    private final VertexFormat vertexFormat;

    private RenderPhase.ShaderProgram shaderProgram;
    private RenderPhase renderPhase;

    private RenderLayer renderLayer;
    private Supplier<Function<RenderPhase.ShaderProgram, RenderLayer>> renderLayerFactory;
    private Block[] appliedBlocks;

    public VoidedHopesShader(@NotNull Identifier identifier, @NotNull VertexFormat vertexFormat) {
        this.identifier = identifier;
        this.vertexFormat = vertexFormat;
    }

    public RenderPhase getRenderPhase() {
        return renderPhase;
    }

    public RenderPhase.ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public VoidedHopesShader setShaderProgram(RenderPhase.ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
        return this;
    }

    public Supplier<Function<RenderPhase.ShaderProgram, RenderLayer>> getRenderLayerFactory() {
        return renderLayerFactory;
    }

    public VoidedHopesShader setRenderLayerFactory(Supplier<Function<RenderPhase.ShaderProgram, RenderLayer>> factory) {
        this.renderLayerFactory = factory;
        return this;
    }

    public RenderLayer getRenderLayer() {
        return this.renderLayer;
    }

    public Block[] getAppliedBlocks() {
        return appliedBlocks;
    }

    public VoidedHopesShader setAppliedBlocks(Block... appliedBlocks) {
        this.appliedBlocks = appliedBlocks;
        return this;
    }

    public void register(CoreShaderRegistrationCallback.RegistrationContext registrationContext) throws IOException {
        if(this.appliedBlocks == null || this.renderLayerFactory == null) throw new RuntimeException("Didn't provide everything for the shader: " + this.identifier);

        registrationContext.register(this.identifier, this.vertexFormat, this::attachShader);
    }

    private void attachShader(ShaderProgram glShaderProgram) {
        this.shaderProgram = new RenderPhase.ShaderProgram(() -> glShaderProgram);
        this.renderPhase = this.shaderProgram;

        this.renderLayer = this.renderLayerFactory.get().apply(this.shaderProgram);

        for (Block appliedBlock : this.appliedBlocks) {
            BlockRenderLayerMap.INSTANCE.putBlock(appliedBlock, this.renderLayer);
        }
    }
}
