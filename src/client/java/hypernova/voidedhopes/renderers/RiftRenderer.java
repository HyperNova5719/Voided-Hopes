package hypernova.voidedhopes.renderers;

import hypernova.voidedhopes.LazuliLib.*;
import hypernova.voidedhopes.VoidedShaders;
import hypernova.voidedhopes.renderers.block.PureVoidBlockRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class RiftRenderer {
    public static boolean alreadyRendered = false;
    public static LazuliVertex template = new LazuliVertex().color(1f,1f,1f,1f);

    public static void register() {
        WorldRenderEvents.START.register(context -> {
            alreadyRendered = false;
        });
    }

    public static void render(Tessellator tess, Camera camera, float tickDelta) {
        if (alreadyRendered) return;

        LazuliBufferBuilder bb = new LazuliBufferBuilder(tess, VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bb.setCamera(camera);

        LapisRenderer.setShader(LazuliShaderRegistry.getShader(VoidedShaders.MATRIX_VOID_LAZULI_SHADER));
        LapisRenderer.setShaderTexture(0, PureVoidBlockRenderer.SKY_TEXTURE);
        LapisRenderer.setShaderTexture(1, PureVoidBlockRenderer.PORTAL_TEXTURE);

        LazuliPen pen = new LazuliPen(template);
        pen.point(-50,0,0, 0);
        pen.point(-40,0,0, 2);
        pen.point(-40,0,-20, 4);
        pen.point(-60,0,-20, 6);
        pen.point(-60,0,5, 4);
        pen.point(-60,0,10, 0);

        pen.draw(bb, new Vec3d(0,60,0));
        bb.draw();

        alreadyRendered = true;
    }
}
