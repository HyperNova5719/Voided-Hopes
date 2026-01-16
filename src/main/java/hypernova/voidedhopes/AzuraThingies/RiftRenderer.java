package hypernova.voidedhopes.AzuraThingies;

import hypernova.voidedhopes.AzuraThingies.LazuliLib.*;
import hypernova.voidedhopes.VoidedHopes;
import hypernova.voidedhopes.client.ModSound;
import hypernova.voidedhopes.client.VoidedHopesClient;
import hypernova.voidedhopes.client.VoidedHopesShaders;
import hypernova.voidedhopes.client.renderers.block.PureVoidBlockRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
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

        //region ==== Config =====
        float activationSequenceLength = 285.2f;
        double sizeMultiplier = 1;
        //endregion

        //region ==== rendering setup ====
        LazuliBufferBuilder bb = new LazuliBufferBuilder(tess, VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        bb.setCamera(camera);
        LazuliPen pen = new LazuliPen(template);
        float time = globalTime - startTime;
        random.setSeed(seed);

        Vec3d cameraPos = camera.getPos();
        //endregion

        if (time < activationSequenceLength) {
            float localTime = time / activationSequenceLength;

            //starting corruption
            RiftRendererManager.corruption += 0.8 * localTime;

            //region ==== Pre-detonation inward wave ====
            RiftRendererManager.epicenter = epicenter;
            RiftRendererManager.waveForce = 0.2f;
            RiftRendererManager.t = (2.0f * localTime) - 1.0f;
            RiftRendererManager.overrideMinecraftShaderUniforms(camera);
            //endregion

            //region ==== Inward shockwave rendering =====

            float size = (1.0f - time) * (1.0f - time) * (1.0f - time) * 100.0f;
            size = max(0, size);
            int res = 20;
            LapisRenderer.setShader(GameRenderer.getPositionColorProgram());
            LazuliVertex model = template.copy().color(1f,1f,1f, (float) ((double) time));

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
            bb.drawAndReset();

            //endregion

            //region ==== Ring render code ====

            float rot = time * 0.04f;

            float count = 1;
            for (float offset = 0; offset < 1.0; offset += 0.06F) {
                float ringTime = localTime / (0.6666f + (count / 3f));
                double circleSize = 0.6 + ((60 * ringTime) / (ringTime + 6));
                LapisRenderer.setShader(GameRenderer.getPositionColorTexProgram());
                LapisRenderer.setShaderTexture(0, magicCircleThingie);
                pen.point(new Vec3d(-0.5 * circleSize * sizeMultiplier, 0, 0).rotateY(rot), circleSize * sizeMultiplier, template.copy().uv(0, 0).color(1f, 1f, 1f, 1 / count));
                pen.point(new Vec3d(0.5 * circleSize * sizeMultiplier, 0, 0).rotateY(rot), circleSize * sizeMultiplier, template.copy().uv(1, 0).color(1f, 1f, 1f, 1 / count));
                pen.draw(bb, epicenter.add(0, 0.25 + offset, 0));
                pen.eraseAll();
                circleSize += 0.04;
                count += 3;
            }

            bb.drawAndReset();
            pen.eraseAll();

            //endregion

        } else {
            float localTime = time - activationSequenceLength;

            //region ==== Detonation inward wave ====
            RiftRendererManager.epicenter = epicenter;
            RiftRendererManager.waveForce = 1;
            RiftRendererManager.t = Math.min(1 + (localTime / 200), 8);
            //endregion

            //region ==== impact frame ====
            if (freezeCount < 2){
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
            for (double dir = 0; dir + 0.01 < Math.PI * 2; dir += Math.PI / 3) {
                Vec3d head = new Vec3d(260,0,260).rotateY((float) dir).multiply(sizeMultiplier);
                Vec3d rVec = new Vec3d(1,0,1).rotateY((float) dir);
                for (double i = 0; i < 20; i++) {
                    double progress = max(0.0, min(1.0, (0.08 * localTime) - i));
                    pen.setU((float) (i / 20));
                    double d = 100 / ((30 * i) + 1);
                    if (progress > 0) pen.point(head, (d + (440 / (1 + i))) * (progress * progress) * sizeMultiplier);
                    head = head.add(rVec.multiply(160 * progress * sizeMultiplier));
                    rVec = new Vec3d(random.nextDouble(), 0, random.nextDouble()).normalize().rotateY((float) dir);
                }

                pen.draw(bb, epicenter.add(0, (130 - (dir)) * sizeMultiplier, 0));
                bb.drawAndReset();
                pen.eraseAll();
            }

            //endregion

            //region ==== Blackhole rendering code ====

            Vec3d blackHoleCenter = epicenter.add(0, 200 * sizeMultiplier, 0);

            LazuliVertex model = template.copy().color(0f,0f, 0f, 1f);

            //LapisRenderer.setShader(pureVoidShader);
            LapisRenderer.setShader(LazuliShaderRegistry.getShader(VoidedHopesShaders.HORIZON_LAZULI_SHADER));

            LapisRenderer.setShaderTexture(0, PureVoidBlockRenderer.SKY_TEXTURE);
            LapisRenderer.setShaderTexture(1, PureVoidBlockRenderer.PORTAL_TEXTURE);
            LapisRenderer.disableCull();
            pen.setModel(template);

            float sss = 1.4f;

            float size = 26 * sss;
            float res = 13;
            double ss = 1.0;

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

                    Vec3d p1 = new Vec3d(sin(theta) * rad1, y1 * ss, cos(theta) * rad1).multiply(sizeMultiplier);
                    Vec3d p2 = new Vec3d(sin(theta) * rad2, y2 * ss, cos(theta) * rad2).multiply(sizeMultiplier);
                    Vec3d p3 = new Vec3d(sin(nextTheta) * rad2, y2 * ss, cos(nextTheta) * rad2).multiply(sizeMultiplier);
                    Vec3d p4 = new Vec3d(sin(nextTheta) * rad1, y1 * ss, cos(nextTheta) * rad1 ).multiply(sizeMultiplier);

                    LazuliVertex v1 = model.copy().pos(p1.add(blackHoleCenter)).color((float) p1.normalize().dotProduct(cameraPos.subtract(p1.add(blackHoleCenter)).normalize()) * 0.5f + 0.5f, 0f, 0f, 0f);
                    LazuliVertex v2 = model.copy().pos(p2.add(blackHoleCenter)).color((float) p2.normalize().dotProduct(cameraPos.subtract(p2.add(blackHoleCenter)).normalize()) * 0.5f + 0.5f, 0f, 0f, 0f);
                    LazuliVertex v3 = model.copy().pos(p3.add(blackHoleCenter)).color((float) p3.normalize().dotProduct(cameraPos.subtract(p3.add(blackHoleCenter)).normalize()) * 0.5f + 0.5f, 0f, 0f, 0f);
                    LazuliVertex v4 = model.copy().pos(p4.add(blackHoleCenter)).color((float) p4.normalize().dotProduct(cameraPos.subtract(p4.add(blackHoleCenter)).normalize()) * 0.5f + 0.5f, 0f, 0f, 0f);

                    bb.addVertex(v1).addVertex(v2).addVertex(v3).addVertex(v4);

                }
            }
            bb.drawAndReset();

            model = template.copy().color(1f,1f, 0.9f, 0.9f);
            LapisRenderer.setShader(RiftRendererManager.set(LazuliShaderRegistry.getShader(VoidedHopesShaders.ACRESCION_LAZULI_SHADER)));

            res = 60;
            int loops = 56;

            for (int ii = 0; ii < loops; ii++) {
                for (int i = 0; i < res * 2; i++) {
                    double rad1 = 4 + (ii * 3 * sss);
                    double rad2 = 4 + (3 * sss) + (ii * 3* sss);

                    float u1 = ii / (float) loops;
                    float u2 = (ii + 1) / (float) loops;
                    float v1 = i / res;
                    float v2 = (i + 1) / res;

                    float theta = (float) (i * PI / res);
                    float nextTheta = (float) ((i + 1) * PI / res);

                    Vec3d p1 = new Vec3d(sin(theta) * rad1, 0, cos(theta) * rad1).multiply(sizeMultiplier);
                    Vec3d p2 = new Vec3d(sin(theta) * rad2, 0, cos(theta) * rad2).multiply(sizeMultiplier);
                    Vec3d p3 = new Vec3d(sin(nextTheta) * rad2, 0, cos(nextTheta) * rad2).multiply(sizeMultiplier);
                    Vec3d p4 = new Vec3d(sin(nextTheta) * rad1, 0, cos(nextTheta) * rad1).multiply(sizeMultiplier);

                    p1 = p1.rotateX((float) Math.toRadians(35));
                    p2 = p2.rotateX((float) Math.toRadians(35));
                    p3 = p3.rotateX((float) Math.toRadians(35));
                    p4 = p4.rotateX((float) Math.toRadians(35));



                    LazuliVertex vt2 = model.copy().pos(p2.add(blackHoleCenter).add(0, -2 * sizeMultiplier, 0)).uv(u2, v1);
                    LazuliVertex vt1 = model.copy().pos(p1.add(blackHoleCenter).add(0, -2 * sizeMultiplier, 0)).uv(u1, v1);
                    LazuliVertex vt3 = model.copy().pos(p3.add(blackHoleCenter).add(0, -2 * sizeMultiplier, 0)).uv(u2, v2);
                    LazuliVertex vt4 = model.copy().pos(p4.add(blackHoleCenter).add(0, -2 * sizeMultiplier, 0)).uv(u1, v2);

                    bb.addVertex(vt1).addVertex(vt2).addVertex(vt3).addVertex(vt4);
                    vt1.displacePos(new Vec3d(0, 4 * sizeMultiplier, 0));
                    vt2.displacePos(new Vec3d(0, 4 * sizeMultiplier, 0));
                    vt3.displacePos(new Vec3d(0, 4 * sizeMultiplier, 0));
                    vt4.displacePos(new Vec3d(0, 4 * sizeMultiplier, 0));
                    bb.addVertex(vt1).addVertex(vt2).addVertex(vt3).addVertex(vt4);
                }
                bb.drawAndReset();
            }


            //endregion

            //region ==== Shockwave rendering code ====
            size = localTime * 5;
            double progress = (60 - min(localTime, 60)) / 60;
            res = 6;

            model = template.copy().color(1f,1f, 0.5f, (float) (progress));
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
            model = template.copy().color(1.0f,1f, 1.0f, 1);
            LapisRenderer.enableCull();

            int beamRes = 10;
            int layers = 6;
            double beamRad = 0.5 * (1 - (1/(1 + (0.25 * localTime))));

            for (int i = 0; i <= layers; i++) {
                model = template.copy().color(min(1f, (0.4f + ((float) i / layers))), min(1f, (0.4f + ((float) i / layers))), min(1f, (0.4f + ((float) i / layers))), 1f / (i + 1f));
                beamRad += 0.3 * (1 - (1/(1 + (0.5 * localTime))));

                for (int p = 0; p < beamRes; p++) {
                    float angle1 = (float) (p * PI / beamRes) * -2f;
                    float angle2 = (float) ((p + 1) * PI / beamRes) * -2f;

                    double y1 = 0 * sizeMultiplier;
                    double y2 = 10000 * sizeMultiplier;

                    Vec3d p1 = new Vec3d(sin(angle1) * beamRad * sizeMultiplier, y1, cos(angle1) * beamRad * sizeMultiplier);
                    Vec3d p2 = new Vec3d(sin(angle1) * beamRad * sizeMultiplier, y2, cos(angle1) * beamRad * sizeMultiplier);
                    Vec3d p3 = new Vec3d(sin(angle2) * beamRad * sizeMultiplier, y2, cos(angle2) * beamRad * sizeMultiplier);
                    Vec3d p4 = new Vec3d(sin(angle2) * beamRad * sizeMultiplier, y1, cos(angle2) * beamRad * sizeMultiplier);

                    LazuliVertex v1 = model.copy().pos(p1.add(epicenter));
                    LazuliVertex v2 = model.copy().pos(p2.add(epicenter));
                    LazuliVertex v3 = model.copy().pos(p3.add(epicenter));
                    LazuliVertex v4 = model.copy().pos(p4.add(epicenter));

                    //bb.addVertex(v1).addVertex(v2).addVertex(v3).addVertex(v4);
                }
                //bb.drawAndReset();
            }

            //endregion

            //region ==== Vortex render code ====
            ShaderProgram vortex = LazuliShaderRegistry.getShader(VoidedHopesShaders.VORTEX_LAZULI_SHADER);
            vortex.getUniformOrDefault("GameTime").set(localTime);
            LapisRenderer.setShader(vortex);

            model = template.copy().color(0.7f,0.8f, 1f, 1f);
            beamRad += 0.7 * (1 - (1/(1 + (0.5 * localTime))));

            beamRes = 20;

            float recall = min(localTime / 400f, 1.0f);

            float vortexRes2 = 10;
            float m = min(localTime / 40f, 1f);

            float totalGrowth = 0;

            for (float n = 0; n < m; n += (m / vortexRes2)) {
                totalGrowth = 50 + (42 * n * n);
            }

            for (float n = 0; n < m; n += (m / vortexRes2)) {
                    float growth = 42 * n * n;

                double y1 = ((n * 100) * (1 - recall)) + (recall * totalGrowth);
                double y2 = (((n + 1f / vortexRes2) * 100) * (1 - recall)) + (recall * totalGrowth);

                float pr = n / m; // Progress

                for (float p = 0; p < beamRes; p++) {
                    float angle1 = (float) (p * PI / beamRes) * -2f;
                    float angle2 = (float) ((p + 1) * PI / beamRes) * -2f;

                    float a = p / beamRes;
                    float b = (p + 1) / beamRes;


                    Vec3d p1 = new Vec3d(sin(angle1) * beamRad, y1, cos(angle1) * beamRad).multiply(sizeMultiplier);
                    Vec3d p2 = new Vec3d(sin(angle1) * (beamRad + growth), y2, cos(angle1) * (beamRad + growth)).multiply(sizeMultiplier);
                    Vec3d p3 = new Vec3d(sin(angle2) * (beamRad + growth), y2, cos(angle2) * (beamRad + growth)).multiply(sizeMultiplier);
                    Vec3d p4 = new Vec3d(sin(angle2) * beamRad, y1, cos(angle2) * beamRad).multiply(sizeMultiplier);

                    float alpha = min(max(0f, (pr + 1f) - (2.0f * recall)), 1f);

                    LazuliVertex v1 = model.copy().pos(p1.add(epicenter)).uv(a, n).color(0f, 0f, 0f, alpha);
                    LazuliVertex v2 = model.copy().pos(p2.add(epicenter)).uv(a, n + 1f / vortexRes2).color(0f, 0f, 0f, alpha);
                    LazuliVertex v3 = model.copy().pos(p3.add(epicenter)).uv(b, n + 1f / vortexRes2).color(0f, 0f, 0f, alpha);
                    LazuliVertex v4 = model.copy().pos(p4.add(epicenter)).uv(b, n).color(0f, 0f, 0f, alpha);

                    bb.addVertex(v1).addVertex(v2).addVertex(v3).addVertex(v4);

                }
                bb.drawAndReset();
                beamRad += growth;
            }

            //endregion

            RiftRendererManager.corruption += pow((390f - cameraPos.distanceTo(epicenter)) / 390f, 4.0);
        }
        bb.draw();


    }

    public boolean kill(float globalTime){
        float time = globalTime - startTime;
        time = 0;
        return (time > 800);
    }
}
