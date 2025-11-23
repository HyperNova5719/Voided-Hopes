package hypernova.voidedhopes.AzuraThingies;

import hypernova.voidedhopes.AzuraThingies.LazuliLib.*;
import hypernova.voidedhopes.VoidedHopes;
import hypernova.voidedhopes.client.ModSound;
import hypernova.voidedhopes.client.VoidedHopesClient;
import hypernova.voidedhopes.client.VoidedHopesShaders;
import hypernova.voidedhopes.client.renderers.block.PureVoidBlockRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import static java.lang.Math.*;

import java.util.*;

/*
Audio sinc info: Audio length: 20.24 seconds
detonation: 9.16 seconds
 */


public class RiftRenderer {
    //region ==== Variable setups ====
    public static LazuliVertex template = new LazuliVertex().color(1f,1f,1f,1f).uv(0,0);
    public static Identifier magicCircleThingie = VoidedHopes.id("riftVfx/circle.png");
    public float startTime;
    public long seed;
    public Vec3d epicenter;
    private Random random;
    private int freezeCount = 0;
    //endregion

    public RiftRenderer(float time, long Seed, Vec3d center){
        startTime = time;
        seed = Seed;
        epicenter = center;
        random = new Random(seed);
        freezeCount = 0;
    }

    public void render(Tessellator tess, Camera camera, ShaderProgram pureVoidShader, float globalTime) {

        //region ==== Config =====
        float activationSequenceLength = 285.2f;
        double sizeMultiplier = 3.0;
        //endregion

        //region ==== rendering setup ====
        LazuliBufferBuilder bb = new LazuliBufferBuilder(tess, VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        bb.setCamera(camera);
        LazuliPen pen = new LazuliPen(template);
        float time = globalTime - startTime;
        random.setSeed(seed);
        //endregion

        if (time < activationSequenceLength) {
            float localTime = time / activationSequenceLength;

            //region ==== Pre-detonation inward wave ====
            RiftRendererManager.epicenter = epicenter;
            RiftRendererManager.waveForce = 0.2f;
            RiftRendererManager.t = (2.0f * localTime) - 1.0f;
            //endregion

            //region ==== Inward shockwave rendering =====
            float size = (1.0f - localTime) * (1.0f - localTime) * (1.0f - localTime) * 100.0f;
            size = max(0, size);
            int res = 20;
            LapisRenderer.setShader(GameRenderer.getPositionColorProgram());
            LazuliVertex model = template.copy().color(1f,1f,1f, (float) ((double) localTime));

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

                    Vec3d p1 = new Vec3d(sin(theta) * rad1 * sizeMultiplier, y1 * sizeMultiplier, cos(theta) * rad1 * sizeMultiplier);
                    Vec3d p2 = new Vec3d(sin(theta) * rad2 * sizeMultiplier, y2 * sizeMultiplier, cos(theta) * rad2 * sizeMultiplier);
                    Vec3d p3 = new Vec3d(sin(nextTheta) * rad2 * sizeMultiplier, y2 * sizeMultiplier, cos(nextTheta) * rad2 * sizeMultiplier);
                    Vec3d p4 = new Vec3d(sin(nextTheta) * rad1 * sizeMultiplier, y1 * sizeMultiplier, cos(nextTheta) * rad1 * sizeMultiplier);

                    LazuliVertex v1 = model.copy().pos(p1.add(epicenter));
                    LazuliVertex v2 = model.copy().pos(p2.add(epicenter));
                    LazuliVertex v3 = model.copy().pos(p3.add(epicenter));
                    LazuliVertex v4 = model.copy().pos(p4.add(epicenter));

                    bb.addVertex(v1).addVertex(v2).addVertex(v3).addVertex(v4);
                }
            }
            bb.draw();

            //endregion test

        } else {
            float localTime = time - activationSequenceLength;

            //region ==== Detonation inward wave ====
            RiftRendererManager.epicenter = epicenter;
            RiftRendererManager.waveForce = 1;
            RiftRendererManager.t = 1 + (localTime / 200);
            //endregion

            //region ==== impact frame ====
            if (freezeCount < 1){
                RiftRendererManager.impactFrame = true;
                freezeCount++;
                LazuliFreeze.freeze(2);
            }
            //endregion

            //region ==== Rift rendering code ====
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
                    if (progress > 0) pen.point(head, (440 / (1 + i)) * (progress * progress) * sizeMultiplier);
                    head = head.add(rVec.multiply(160 * progress * sizeMultiplier));
                    rVec = new Vec3d(random.nextDouble(), 0, random.nextDouble()).normalize().rotateY((float) dir);
                }

                pen.draw(bb, epicenter.add(0, (130 - (5.0 * dir)) * sizeMultiplier, 0));
                bb.drawAndReset();
                pen.eraseAll();
            }

            //endregion

            //region ==== Shockwave rendering code ====
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

                    Vec3d p1 = new Vec3d(sin(theta) * rad1 * sizeMultiplier, y1, cos(theta) * rad1 * sizeMultiplier);
                    Vec3d p2 = new Vec3d(sin(theta) * rad2 * sizeMultiplier, y2, cos(theta) * rad2 * sizeMultiplier);
                    Vec3d p3 = new Vec3d(sin(nextTheta) * rad2 * sizeMultiplier, y2, cos(nextTheta) * rad2 * sizeMultiplier);
                    Vec3d p4 = new Vec3d(sin(nextTheta) * rad1 * sizeMultiplier, y1, cos(nextTheta) * rad1 * sizeMultiplier);

                    LazuliVertex v1 = model.copy().pos(p1.add(epicenter));
                    LazuliVertex v2 = model.copy().pos(p2.add(epicenter));
                    LazuliVertex v3 = model.copy().pos(p3.add(epicenter));
                    LazuliVertex v4 = model.copy().pos(p4.add(epicenter));

                    bb.addVertex(v1).addVertex(v2).addVertex(v3).addVertex(v4);
                }
            }
            bb.drawAndReset();

            //endregion

            //region ==== Beam render code ====
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

                    double y1 = 0 * sizeMultiplier;
                    double y2 = 100 * sizeMultiplier;

                    Vec3d p1 = new Vec3d(sin(angle1) * beamRad * sizeMultiplier, y1, cos(angle1) * beamRad * sizeMultiplier);
                    Vec3d p2 = new Vec3d(sin(angle1) * beamRad * sizeMultiplier, y2, cos(angle1) * beamRad * sizeMultiplier);
                    Vec3d p3 = new Vec3d(sin(angle2) * beamRad * sizeMultiplier, y2, cos(angle2) * beamRad * sizeMultiplier);
                    Vec3d p4 = new Vec3d(sin(angle2) * beamRad * sizeMultiplier, y1, cos(angle2) * beamRad * sizeMultiplier);

                    LazuliVertex v1 = model.copy().pos(p1.add(epicenter));
                    LazuliVertex v2 = model.copy().pos(p2.add(epicenter));
                    LazuliVertex v3 = model.copy().pos(p3.add(epicenter));
                    LazuliVertex v4 = model.copy().pos(p4.add(epicenter));

                    bb.addVertex(v1).addVertex(v2).addVertex(v3).addVertex(v4);
                }
                bb.drawAndReset();
            }

            //endregion

            //region ==== Vortex render code ====
            ShaderProgram vortex = LazuliShaderRegistry.getShader(VoidedHopesShaders.VORTEX_LAZULI_SHADER);
            vortex.getUniformOrDefault("GameTime").set(localTime);
            LapisRenderer.setShader(vortex);

            model = template.copy().color(0.7f,0.8f, 1f, 1f);
            beamRad += 0.7 * (1 - (1/(1 + (0.5 * localTime))));

            beamRes = 20;
            float vortexRes2 = 10;
            float m = min(localTime / 40f, 1f);

            for (float n = 0; n < m; n += (m / vortexRes2)) {
                    float growth = 42 * n * n;
                for (float p = 0; p < beamRes; p++) {
                    float angle1 = (float) (p * PI / beamRes) * -2f;
                    float angle2 = (float) ((p + 1) * PI / beamRes) * -2f;

                    double y1 = n * 100 * sizeMultiplier;
                    double y2 = (n + 1f / vortexRes2) * 100 * sizeMultiplier;

                    float a = p / beamRes;
                    float b = (p + 1) / beamRes;


                    Vec3d p1 = new Vec3d(sin(angle1) * beamRad * sizeMultiplier, y1, cos(angle1) * beamRad * sizeMultiplier);
                    Vec3d p2 = new Vec3d(sin(angle1) * (beamRad + growth) * sizeMultiplier, y2, cos(angle1) * (beamRad + growth) * sizeMultiplier);
                    Vec3d p3 = new Vec3d(sin(angle2) * (beamRad + growth) * sizeMultiplier, y2, cos(angle2) * (beamRad + growth) * sizeMultiplier);
                    Vec3d p4 = new Vec3d(sin(angle2) * beamRad * sizeMultiplier, y1, cos(angle2) * beamRad * sizeMultiplier);

                    LazuliVertex v1 = model.copy().pos(p1.add(epicenter)).uv(a, n);
                    LazuliVertex v2 = model.copy().pos(p2.add(epicenter)).uv(a, n + 1f / vortexRes2);
                    LazuliVertex v3 = model.copy().pos(p3.add(epicenter)).uv(b, n + 1f / vortexRes2);
                    LazuliVertex v4 = model.copy().pos(p4.add(epicenter)).uv(b, n);

                    bb.addVertex(v1).addVertex(v2).addVertex(v3).addVertex(v4);

                }
                bb.drawAndReset();
                beamRad += growth;
            }

            //endregion

            //region ==== Ring render code ====

            float rot = time * 0.04f;

            float count = 1;
            for (float offset = 0; offset < 0.71; offset += 0.06F) {
                float ringTime = localTime / (0.6666f + (count / 3f));
                double circleSize = 0.6 + ((15 * ringTime) / (ringTime + 6));
                LapisRenderer.setShader(GameRenderer.getPositionColorTexProgram());
                LapisRenderer.setShaderTexture(0, magicCircleThingie);
                pen.point(new Vec3d(-0.5 * circleSize * sizeMultiplier, 0, 0).rotateY(rot), circleSize * sizeMultiplier, template.copy().uv(0, 0).color(1f, 1f, 1f, 1 / count));
                pen.point(new Vec3d(0.5 * circleSize * sizeMultiplier, 0, 0).rotateY(rot), circleSize * sizeMultiplier, template.copy().uv(1, 0).color(1f, 1f, 1f, 1 / count));
                pen.draw(bb, epicenter.add(0, 0.01 + offset, 0));
                pen.eraseAll();
                circleSize += 0.04;
                count += 3;
            }

            bb.drawAndReset();
            pen.eraseAll();

            //endregion

        }
        bb.draw();
    }

    public boolean kill(float globalTime){
        float time = globalTime - startTime;
        time = 0;
        return (time > 800);
    }
}
