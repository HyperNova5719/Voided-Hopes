package hypernova.voidedhopes.AzuraThingies;

import hypernova.voidedhopes.AzuraThingies.LazuliLib.*;
import hypernova.voidedhopes.client.VoidedHopesShaders;
import hypernova.voidedhopes.client.renderers.block.PureVoidBlockRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.*;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class RiftRendererManager {
    public static boolean alreadyRendered = false;
    public static LazuliVertex template = new LazuliVertex().color(1f,1f,1f,1f);
    public static List<RiftRenderer> rifts = new ArrayList<>();
    public static float time = 0;
    public static boolean impactFrame = false;
    public static boolean wasImpact = false;
    public static float Post1Force = 0;
    public static Vec3d epicenter = new Vec3d(0,0,0);
    public static float waveForce = 0;

    private static Vec3d dis;
    public static float t = 200;
    private static List<String> shadersToInjectUniforms = new ArrayList<>();

    public static float trueTimeTicks(){
        return (((float) (System.currentTimeMillis() % 86400000) / 1000f) * 20f);

    }





    public static void register() {
        WorldRenderEvents.END.register(context -> {
            alreadyRendered = false;
            if (impactFrame) {
                LazuliShaderRegistry.getPostProcessor(VoidedHopesShaders.IMPACT).render(0);
            }
            wasImpact = impactFrame;
            //time = trueTimeTicks();
            time += context.tickDelta();

            LazuliShaderRegistry.getPostProcessor(VoidedHopesShaders.POST1).render(0);
        });
    }

    public static void addRift(Vec3d pos, long seed){
        rifts.add(new RiftRenderer(time, seed, pos));
    }

    public static void render(Tessellator tess, Camera camera, float tickDelta) {
        waveForce = 0;
        t = 200;
        if (alreadyRendered) return;
        ShaderProgram PureVoid = LazuliShaderRegistry.getShader(VoidedHopesShaders.RIFT_LAZULI_SHADER);
        LapisRenderer.setShaderTexture(0, PureVoidBlockRenderer.SKY_TEXTURE);
        LapisRenderer.setShaderTexture(1, PureVoidBlockRenderer.PORTAL_TEXTURE);

        impactFrame = false;

        Iterator<RiftRenderer> iterator = rifts.iterator();
        while (iterator.hasNext()) {
            RiftRenderer rift = iterator.next();
            rift.render(tess, camera, PureVoid, time);
            if (rift.kill(time)) {
                iterator.remove();
            }
        }


        overrideMinecraftShaderUniforms(camera);
        alreadyRendered = true;

    }

    public static void set(ShaderProgram p){
        p.getUniformOrDefault("epicenter").set((float) dis.x, (float) dis.y, (float) dis.z);
        p.getUniformOrDefault("state").set(t, waveForce);
    }

    private static void overrideMinecraftShaderUniforms(Camera camera){
        dis = camera.getPos().multiply(-1).add(epicenter);

        set(GameRenderer.getPositionProgram());
        set(GameRenderer.getPositionColorProgram());
        set(GameRenderer.getPositionColorTexProgram());
        set(GameRenderer.getPositionTexProgram());
        set(GameRenderer.getPositionTexColorProgram());
        set(GameRenderer.getParticleProgram());
        set(GameRenderer.getPositionColorLightmapProgram());
        set(GameRenderer.getPositionColorTexLightmapProgram());
        set(GameRenderer.getPositionTexColorNormalProgram());
        set(GameRenderer.getPositionTexLightmapColorProgram());
        set(GameRenderer.getRenderTypeSolidProgram());
        set(GameRenderer.getRenderTypeCutoutMippedProgram());
        set(GameRenderer.getRenderTypeCutoutProgram());
        set(GameRenderer.getRenderTypeTranslucentProgram());
        set(GameRenderer.getRenderTypeTranslucentMovingBlockProgram());
        set(GameRenderer.getRenderTypeTranslucentNoCrumblingProgram());
        set(GameRenderer.getRenderTypeArmorCutoutNoCullProgram());
        set(GameRenderer.getRenderTypeEntitySolidProgram());
        set(GameRenderer.getRenderTypeEntityCutoutProgram());
        set(GameRenderer.getRenderTypeEntityCutoutNoNullProgram());
        set(GameRenderer.getRenderTypeEntityCutoutNoNullZOffsetProgram());
        set(GameRenderer.getRenderTypeItemEntityTranslucentCullProgram());
        set(GameRenderer.getRenderTypeEntityTranslucentCullProgram());
        set(GameRenderer.getRenderTypeEntityTranslucentProgram());
        set(GameRenderer.getRenderTypeEntityTranslucentEmissiveProgram());
        set(GameRenderer.getRenderTypeEntitySmoothCutoutProgram());
        set(GameRenderer.getRenderTypeBeaconBeamProgram());
        set(GameRenderer.getRenderTypeEntityDecalProgram());
        set(GameRenderer.getRenderTypeEntityNoOutlineProgram());
        set(GameRenderer.getRenderTypeEntityShadowProgram());
        set(GameRenderer.getRenderTypeEntityAlphaProgram());
        set(GameRenderer.getRenderTypeEyesProgram());
        set(GameRenderer.getRenderTypeEnergySwirlProgram());
        set(GameRenderer.getRenderTypeLeashProgram());
        set(GameRenderer.getRenderTypeWaterMaskProgram());
        set(GameRenderer.getRenderTypeOutlineProgram());
        set(GameRenderer.getRenderTypeArmorGlintProgram());
        set(GameRenderer.getRenderTypeArmorEntityGlintProgram());
        set(GameRenderer.getRenderTypeGlintTranslucentProgram());
        set(GameRenderer.getRenderTypeGlintProgram());
        set(GameRenderer.getRenderTypeGlintDirectProgram());
        set(GameRenderer.getRenderTypeEntityGlintProgram());
        set(GameRenderer.getRenderTypeEntityGlintDirectProgram());
        set(GameRenderer.getRenderTypeTextProgram());
        set(GameRenderer.getRenderTypeTextBackgroundProgram());
        set(GameRenderer.getRenderTypeTextIntensityProgram());
        set(GameRenderer.getRenderTypeTextSeeThroughProgram());
        set(GameRenderer.getRenderTypeTextBackgroundSeeThroughProgram());
        set(GameRenderer.getRenderTypeTextIntensitySeeThroughProgram());
        set(GameRenderer.getRenderTypeLightningProgram());
        set(GameRenderer.getRenderTypeTripwireProgram());
        set(GameRenderer.getRenderTypeEndPortalProgram());
        set(GameRenderer.getRenderTypeEndGatewayProgram());
        set(GameRenderer.getRenderTypeLinesProgram());
        set(GameRenderer.getRenderTypeCrumblingProgram());
        set(GameRenderer.getRenderTypeGuiProgram());
        set(GameRenderer.getRenderTypeGuiOverlayProgram());
        set(GameRenderer.getRenderTypeGuiTextHighlightProgram());
        set(GameRenderer.getRenderTypeGuiGhostRecipeOverlayProgram());
        set(GameRenderer.getRenderTypeEntitySolidProgram());
        set(GameRenderer.getRenderTypeEntityCutoutProgram());
        set(GameRenderer.getRenderTypeEntityCutoutNoNullProgram());
        set(GameRenderer.getRenderTypeEntityCutoutNoNullZOffsetProgram());
        set(GameRenderer.getRenderTypeItemEntityTranslucentCullProgram());
        set(GameRenderer.getRenderTypeEntityTranslucentCullProgram());
        set(GameRenderer.getRenderTypeEntityTranslucentProgram());
        set(GameRenderer.getRenderTypeEntityTranslucentEmissiveProgram());
        set(GameRenderer.getRenderTypeEntitySmoothCutoutProgram());
        set(GameRenderer.getRenderTypeEntityDecalProgram());
        set(GameRenderer.getRenderTypeEntityNoOutlineProgram());
        set(GameRenderer.getRenderTypeEntityShadowProgram());
        set(GameRenderer.getRenderTypeEntityAlphaProgram());
        set(GameRenderer.getRenderTypeEntityGlintProgram());
        set(GameRenderer.getRenderTypeEntityGlintDirectProgram());
    }

}
