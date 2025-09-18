package hypernova.voidedhopes.renderers;

import hypernova.voidedhopes.LazuliLib.*;
import hypernova.voidedhopes.VoidedShaders;
import hypernova.voidedhopes.renderers.block.PureVoidBlockRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.*;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class RiftRenderer {
    public static boolean alreadyRendered = false;

    public static void register() {
        WorldRenderEvents.START.register(context -> {
            alreadyRendered = false;
        });
    }

    public static void render(Tessellator tess, Camera camera, float tickDelta) {
        if (alreadyRendered) return;

        LazuliBufferBuilder bb = new LazuliBufferBuilder(tess, VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bb.setCamera(camera);

        float[] color = {1f, 1f, 1f, 1f}; // white, opaque

        LapisRenderer.setShader(LazuliShaderRegistry.getShader(VoidedShaders.MATRIX_VOID_LAZULI_SHADER));
        LapisRenderer.setShaderTexture(0, PureVoidBlockRenderer.SKY_TEXTURE);
        LapisRenderer.setShaderTexture(1, PureVoidBlockRenderer.PORTAL_TEXTURE);

        LazuliVertex vA = new LazuliVertex().pos(new Vec3d(0, 100, 0))
                .color(color[0], color[1], color[2], color[3]);
        LazuliVertex vB = new LazuliVertex().pos(new Vec3d(0, 100, 0))
                .color(color[0], color[1], color[2], color[3]);
        bb.addVertex(vA).addVertex(vB);

        vA = new LazuliVertex().pos(new Vec3d(-5, 100, 20))
                .color(color[0], color[1], color[2], color[3]);
        vB = new LazuliVertex().pos(new Vec3d(5, 100, 20))
                .color(color[0], color[1], color[2], color[3]);
        bb.addVertex(vB).addVertex(vA);
        bb.addVertex(vA).addVertex(vB);

        vA = new LazuliVertex().pos(new Vec3d(-20, 100, 50))
                .color(color[0], color[1], color[2], color[3]);
        vB = new LazuliVertex().pos(new Vec3d(20, 100, 50))
                .color(color[0], color[1], color[2], color[3]);
        bb.addVertex(vB).addVertex(vA);
        bb.addVertex(vA).addVertex(vB);

        vA = new LazuliVertex().pos(new Vec3d(-25, 100, 70))
                .color(color[0], color[1], color[2], color[3]);
        vB = new LazuliVertex().pos(new Vec3d(25, 100, 70))
                .color(color[0], color[1], color[2], color[3]);
        bb.addVertex(vB).addVertex(vA);
        bb.addVertex(vA).addVertex(vB);

        vA = new LazuliVertex().pos(new Vec3d(-20, 100, 90))
                .color(color[0], color[1], color[2], color[3]);
        vB = new LazuliVertex().pos(new Vec3d(20, 100, 90))
                .color(color[0], color[1], color[2], color[3]);
        bb.addVertex(vB).addVertex(vA);
        bb.addVertex(vA).addVertex(vB);

        vA = new LazuliVertex().pos(new Vec3d(0, 100, 140))
                .color(color[0], color[1], color[2], color[3]);
        vB = new LazuliVertex().pos(new Vec3d(0, 100, 140))
                .color(color[0], color[1], color[2], color[3]);
        bb.addVertex(vB).addVertex(vA);





        bb.draw();

        alreadyRendered = true;
    }
}
