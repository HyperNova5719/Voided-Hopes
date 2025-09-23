package hypernova.voidedhopes.AzuraThingies;

import hypernova.voidedhopes.AzuraThingies.LazuliLib.*;
import hypernova.voidedhopes.VoidedHopes;
import hypernova.voidedhopes.client.VoidedHopesShaders;
import hypernova.voidedhopes.client.renderers.block.PureVoidBlockRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import static java.lang.Math.*;

import java.util.*;



public class RiftRenderer {
    public static LazuliVertex template = new LazuliVertex().color(1f,1f,1f,1f).uv(0,0);
    public static Identifier magicCircleThingie = VoidedHopes.id("riftVfx/circle.png");
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

        LazuliBufferBuilder bb = new LazuliBufferBuilder(tess, VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        bb.setCamera(camera);
        LazuliPen pen = new LazuliPen(template);



        if (time < 20) {
            float size = (20 - time) * (20 - time) * (20 - time) * 0.1f;
            double progress = time / 20f;
            int res = 20;

            LazuliVertex model = template.copy().color(1f,1f,1f, (float) (progress));

            for (int p = 0; p < res; p++) {
                float angle1 = (float) (p * PI / res);
                float angle2 = (float) ((p + 1) * PI / res);

                float rad1 = (float) sin(angle1) * size;
                float rad2 = (float) sin(angle2) * size;
                float y1 = (float) cos(angle1) * size;
                float y2 = (float) cos(angle2) * size;

                for (int i = 0; i < res * 2; i++) {
                    float theta = (float) (i * PI / res);
                    float nextTheta = (float) ((i + 1) * PI / res);

                    Vec3d v1 = new Vec3d(sin(theta) * rad1, y1, cos(theta) * rad1);
                    Vec3d v2 = new Vec3d(sin(theta) * rad2, y2, cos(theta) * rad2);
                    Vec3d v3 = new Vec3d(sin(nextTheta) * rad2, y2, cos(nextTheta) * rad2);
                    Vec3d v4 = new Vec3d(sin(nextTheta) * rad1, y1, cos(nextTheta) * rad1);

                    bb.addVertex(model.pos(v1.add(epicenter))).addVertex(model.pos(v2.add(epicenter))).addVertex(model.pos(v3.add(epicenter))).addVertex(model.pos(v4.add(epicenter)));
                }
            }
            bb.draw();
        } else {
            float localTime = time - 20;
            float rot = time * 0.04f;
            //Shockwave
            float size = localTime * 5;
            double progress = (60 - min(localTime, 60)) / 60;
            int res = 6;

            LazuliVertex model = template.copy().color(1f,1f, 0.5f, (float) (progress));

            for (int p = 0; p < res; p++) {
                float angle1 = (float) (p * PI / res);
                float angle2 = (float) ((p + 1) * PI / res);

                float rad1 = (float) sin(angle1) * size;
                float rad2 = (float) sin(angle2) * size;
                float y1 = (float) cos(angle1) * size;
                float y2 = (float) cos(angle2) * size;

                for (int i = 0; i < res * 2; i++) {
                    float theta = (float) (i * PI / res);
                    float nextTheta = (float) ((i + 1) * PI / res);

                    Vec3d v1 = new Vec3d(sin(theta) * rad1, y1, cos(theta) * rad1);
                    Vec3d v2 = new Vec3d(sin(theta) * rad2, y2, cos(theta) * rad2);
                    Vec3d v3 = new Vec3d(sin(nextTheta) * rad2, y2, cos(nextTheta) * rad2);
                    Vec3d v4 = new Vec3d(sin(nextTheta) * rad1, y1, cos(nextTheta) * rad1);

                    bb.addVertex(model.pos(v1.add(epicenter))).addVertex(model.pos(v2.add(epicenter))).addVertex(model.pos(v3.add(epicenter))).addVertex(model.pos(v4.add(epicenter)));
                }
            }
            bb.drawAndReset();

            //Ring
            float count = 1;
            for (float offset = 0; offset < 0.7; offset += 0.07F) {
                float ringTime = localTime / (count / 1.5f);
                double circleSize = 0.6 + ((15 * ringTime) / (ringTime + 6));
                LapisRenderer.setShader(GameRenderer.getPositionColorTexProgram());
                LapisRenderer.setShaderTexture(0, magicCircleThingie);
                pen.point(new Vec3d(-0.5 * circleSize, 0, 0).rotateY(rot), circleSize, template.copy().uv(0, 0).color(1f, 1f, 1f, 1 / count));
                pen.point(new Vec3d(0.5 * circleSize, 0, 0).rotateY(rot), circleSize, template.copy().uv(1, 0).color(1f, 1f, 1f, 1 / count));
                pen.draw(bb, epicenter.add(0, 0.01 + offset, 0));
                pen.eraseAll();
                circleSize += 0.04;
                count += 3;
            }
            bb.drawAndReset();
            pen.eraseAll();

            if (time > 40) {
                LapisRenderer.setShader(pureVoidShader);
                LapisRenderer.setShaderTexture(0, PureVoidBlockRenderer.SKY_TEXTURE);
                LapisRenderer.setShaderTexture(1, PureVoidBlockRenderer.PORTAL_TEXTURE);


                for (double dir = 0; dir < Math.PI * 2; dir += Math.PI / 6) {
                    Vec3d head = Vec3d.ZERO;
                    for (double i = 0; i < 20; i++) {
                        pen.point(head, (20 - i));
                        Vec3d rVec = new Vec3d(random.nextDouble(), 0, random.nextDouble()).normalize().rotateY((float) dir);
                        head = head.add(rVec.multiply(130));
                    }

                    pen.draw(bb, epicenter.add(0, 100, 0));
                    bb.drawAndReset();
                    pen.eraseAll();
                }
            }
        }
        bb.draw();
    }

    public boolean kill(float globalTime){
        float time = globalTime - startTime;
        time = 0;
        return (time > 800);
    }
}
