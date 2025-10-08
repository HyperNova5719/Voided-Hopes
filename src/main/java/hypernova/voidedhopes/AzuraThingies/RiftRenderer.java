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
    private int freezeCount = 0;


    public RiftRenderer(float time, long Seed, Vec3d center){
        startTime = time;
        seed = Seed;
        epicenter = center;
        random = new Random(seed);
        freezeCount = 0;
    }



    public void render(Tessellator tess, Camera camera, ShaderProgram pureVoidShader, float globalTime) {
        random.setSeed(seed);
        float time = globalTime - startTime;
        time *= 0.6;

        LazuliBufferBuilder bb = new LazuliBufferBuilder(tess, VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        bb.setCamera(camera);
        LazuliPen pen = new LazuliPen(template);



        if (time < 20) {
            float size = (20 - time) * (20 - time) * (20 - time) * 0.04f;
            size = max(0, size);
            double progress = time / 20f;
            int res = 20;
            LapisRenderer.setShader(GameRenderer.getPositionColorProgram());
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

            if (freezeCount < 1){
                RiftRendererManager.impactFrame = true;
                freezeCount++;
                LazuliFreeze.freeze(2);
            }

            float rot = time * 0.04f;


            //Terrain wave
            if (localTime < 250){
                RiftRendererManager.epicenter = epicenter;
                RiftRendererManager.waveForce = 1;
                RiftRendererManager.t = localTime / 200;
            }


            //Sky rift
            LapisRenderer.setShader(pureVoidShader);
            LapisRenderer.setShaderTexture(0, PureVoidBlockRenderer.SKY_TEXTURE);
            LapisRenderer.setShaderTexture(1, PureVoidBlockRenderer.PORTAL_TEXTURE);
            pen.setModel(template);
            for (double dir = 0; dir < Math.PI * 2; dir += Math.PI / 5) {
                Vec3d head = Vec3d.ZERO;//new Vec3d(60,0,60).rotateY((float) dir);
                Vec3d rVec = new Vec3d(1,0,1).rotateY((float) dir);
                for (double i = 0; i < 20; i++) {
                    double progress = max(0.0, min(1.0, (0.08 * localTime) - i));
                    pen.setU((float) (i / 20));
                    if (progress > 0) pen.point(head, (440 / (1 + i)) / (progress * progress));
                    head = head.add(rVec.multiply(160 * progress));
                    rVec = new Vec3d(random.nextDouble(), 0, random.nextDouble()).normalize().rotateY((float) dir);
                }

                pen.draw(bb, epicenter.add(0, 130 - (5.0 * dir), 0));
                bb.drawAndReset();
                pen.eraseAll();
            }


            //Shockwave
            float size = localTime * 5;
            double progress = (60 - min(localTime, 60)) / 60;
            int res = 6;

            LazuliVertex model = template.copy().color(1f,1f, 0.5f, (float) (progress));
            LapisRenderer.setShader(GameRenderer.getPositionColorProgram());
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

            //beam
            model = template.copy().color(0.7f,1f, 0.7f, 1);
            LapisRenderer.enableCull();

            int beamRes = 10;
            int layers = 6;
            double beamRad = 0.5 * (1 - (1/(1 + (0.25 * localTime))));

            for (int i = 0; i <= layers; i++) {
                model = template.copy().color(0f, min(1f, (0.4f + ((float) i / layers))), 0f, 1f / (i + 1f));
                beamRad += 0.3 * (1 - (1/(1 + (0.5 * localTime))));

                for (int p = 0; p < beamRes; p++) {
                    float angle1 = (float) (p * PI / beamRes) * -2f;
                    float angle2 = (float) ((p + 1) * PI / beamRes) * -2f;

                    double y1 = 0;
                    double y2 = 100;

                    float a = (float) (angle1 / (PI * 2));
                    float b = (float) (angle2 / (PI * 2));

                    Vec3d v1 = new Vec3d(sin(angle1) * beamRad, y1, cos(angle1) * beamRad);
                    Vec3d v2 = new Vec3d(sin(angle1) * beamRad, y2, cos(angle1) * beamRad);
                    Vec3d v3 = new Vec3d(sin(angle2) * beamRad, y2, cos(angle2) * beamRad);
                    Vec3d v4 = new Vec3d(sin(angle2) * beamRad, y1, cos(angle2) * beamRad);

                    bb.addVertex(model.pos(v1.add(epicenter)).uv(a, 0)).addVertex(model.pos(v2.add(epicenter)).uv(a, 1)).addVertex(model.pos(v3.add(epicenter)).uv(b, 1)).addVertex(model.pos(v4.add(epicenter)).uv(b, 0));
                }
                bb.drawAndReset();
            }


            //Vortex
            ShaderProgram vortex = LazuliShaderRegistry.getShader(VoidedHopesShaders.VORTEX_LAZULI_SHADER);
            vortex.getUniformOrDefault("GameTime").set(localTime);
            LapisRenderer.setShader(vortex);

            model = template.copy().color(0.7f,0.8f, 1f, 1f);
            beamRad += 0.7 * (1 - (1/(1 + (0.5 * localTime))));

            beamRes = 20;
            float vortexRes2 = 10;

            for (float n = 0; n < 1.0; n += 1f / vortexRes2) {
                    float growth = 42 * n * n;
                for (float p = 0; p < beamRes; p++) {
                    float angle1 = (float) (p * PI / beamRes) * -2f;
                    float angle2 = (float) ((p + 1) * PI / beamRes) * -2f;

                    double y1 = n * 100;
                    double y2 = (n + 1f / vortexRes2) * 100;

                    float a = p / beamRes;
                    float b = (p + 1) / beamRes;


                    Vec3d p1 = new Vec3d(sin(angle1) * beamRad, y1, cos(angle1) * beamRad);
                    Vec3d p2 = new Vec3d(sin(angle1) * (beamRad + growth), y2, cos(angle1) * (beamRad + growth));
                    Vec3d p3 = new Vec3d(sin(angle2) * (beamRad + growth), y2, cos(angle2) * (beamRad + growth));
                    Vec3d p4 = new Vec3d(sin(angle2) * beamRad, y1, cos(angle2) * beamRad);

                    LazuliVertex v1 = model.copy().pos(p1.add(epicenter)).uv(a, n);
                    LazuliVertex v2 = model.copy().pos(p2.add(epicenter)).uv(a, n + 1f / vortexRes2);
                    LazuliVertex v3 = model.copy().pos(p3.add(epicenter)).uv(b, n + 1f / vortexRes2);
                    LazuliVertex v4 = model.copy().pos(p4.add(epicenter)).uv(b, n);

                    bb.addVertex(v1).addVertex(v2).addVertex(v3).addVertex(v4);

                }
                bb.drawAndReset();
                beamRad += growth;
            }


            //Ring
            float count = 1;
            for (float offset = 0; offset < 0.71; offset += 0.06F) {
                float ringTime = localTime / (0.6666f + (count / 3f));
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
        }
        bb.draw();
    }

    public boolean kill(float globalTime){
        float time = globalTime - startTime;
        time = 0;
        return (time > 800);
    }
}
