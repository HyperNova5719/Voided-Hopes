package hypernova.voidedhopes.AzuraThingies;

import hypernova.voidedhopes.AzuraThingies.LazuliLib.*;
import hypernova.voidedhopes.client.VoidedHopesShaders;
import hypernova.voidedhopes.client.renderers.block.PureVoidBlockRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.*;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class RiftRenderer {
    public static LazuliVertex template = new LazuliVertex().color(1f,1f,1f,1f);
    public float startTime;
    public long seed;
    public Vec3d epicenter;
    private Random random;

    public RiftRenderer(float time, long Seed, Vec3d center){
        startTime = time;
        seed = Seed;
        epicenter = center;
        random = new Random(seed);
    }



    public void render(Tessellator tess, Camera camera, ShaderProgram pureVoidShader, float globalTime) {
        random.setSeed(seed);
        float time = globalTime - startTime;

        LazuliBufferBuilder bb = new LazuliBufferBuilder(tess, VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bb.setCamera(camera);

        LapisRenderer.setShader(pureVoidShader);
        LapisRenderer.setShaderTexture(0, PureVoidBlockRenderer.SKY_TEXTURE);
        LapisRenderer.setShaderTexture(1, PureVoidBlockRenderer.PORTAL_TEXTURE);

        LazuliPen pen = new LazuliPen(template);


        for (double dir = 0; dir < Math.PI * 2; dir+= Math.PI/6) {
            Vec3d head = Vec3d.ZERO;
            for (double i = 0; i < 20; i++) {
                pen.point(head, (20 - i));
                Vec3d rVec = new Vec3d(random.nextDouble(), 0, random.nextDouble()).normalize().rotateY((float) dir);
                head = head.add(rVec.multiply(70));
            }

            pen.draw(bb, epicenter);
            bb.drawAndReset();
            pen.eraseAll();
        }
        bb.draw();
    }

    public boolean kill(float globalTime){
        float time = globalTime - startTime;
        return (time > 200);
    }
}
