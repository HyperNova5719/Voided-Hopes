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

        Random random = new Random(0);

        LazuliBufferBuilder bb = new LazuliBufferBuilder(tess, VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bb.setCamera(camera);

        LapisRenderer.setShader(LazuliShaderRegistry.getShader(VoidedShaders.MATRIX_VOID_LAZULI_SHADER));
        LapisRenderer.setShaderTexture(0, PureVoidBlockRenderer.SKY_TEXTURE);
        LapisRenderer.setShaderTexture(1, PureVoidBlockRenderer.PORTAL_TEXTURE);

        LazuliPen pen = new LazuliPen(template);


        for (double dir = 0; dir < Math.PI * 2; dir+= Math.PI/6) {
            Vec3d head = Vec3d.ZERO;
            for (double i = 0; i < 10; i++) {
                pen.point(head, (9 - i));
                Vec3d rVec = new Vec3d(random.nextDouble(), 0, random.nextDouble()).normalize().rotateY((float) dir);
                head = head.add(rVec.multiply(40));
            }

            pen.draw(bb, new Vec3d(0, 160, 0));
            bb.drawAndReset();
            pen.eraseAll();
        }
        bb.draw();
        alreadyRendered = true;
    }
}
