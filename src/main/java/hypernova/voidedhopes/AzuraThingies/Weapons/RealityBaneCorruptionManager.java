package hypernova.voidedhopes.AzuraThingies.Weapons;


import hypernova.voidedhopes.AzuraThingies.LazuliLib.*;
import hypernova.voidedhopes.AzuraThingies.RiftRenderer;
import hypernova.voidedhopes.AzuraThingies.RiftRendererManager;
import hypernova.voidedhopes.client.VoidedHopesShaders;
import hypernova.voidedhopes.client.renderers.block.PureVoidBlockRenderer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.*;

import static hypernova.voidedhopes.AzuraThingies.RiftRenderer.template;

public class RealityBaneCorruptionManager {

    public static float Corruption = 0;
    public static float UserCorruption = 0;
    public static boolean alreadyRendered = false;
    public static LazuliVertex template2 = new LazuliVertex().color(1f,1f,1f,1f).uv(0,0);
    private static Map<UUID, Integer> corrupted = new HashMap<>();

    public static void register(){
        ClientTickEvents.START_WORLD_TICK.register(t ->{

            Corruption = Corruption * 0.95f;
            UserCorruption = UserCorruption * 0.95f;
            corrupted = new HashMap<>();


            //System.out.println(Corruption + UserCorruption);

        });

        HudRenderCallback.EVENT.register(new HudRenderCallback() {
            @Override
            public void onHudRender(DrawContext drawContext, float v) {
                alreadyRendered = false;

                if (Corruption + UserCorruption > 0.01 && MinecraftClient.getInstance().currentScreen == null) {
                    LazuliPostEffectShader bp = LazuliShaderRegistry.getPostProcessor(VoidedHopesShaders.POST2);
                    bp.passes.get(0).getProgram().getUniformByNameOrDummy("TT").set((float) (RiftRendererManager.time / 1000f) % 1f);
                    bp.passes.get(0).getProgram().getUniformByNameOrDummy("Corruption").set(1f - (1f / (float) (1.0 + (Corruption + UserCorruption))));
                    bp.render(0);
                }
            }
        });
    }

    static void addCorrupted(UUID uuid, int time){
        corrupted.put(uuid, time);
    }




    public static void render(Tessellator tess, Camera camera, float tickDelta) {
        if (alreadyRendered) return;



        //region ==== rendering setup ====

        LazuliPen pen = new LazuliPen(new LazuliVertex().color(1f,1f,1f,1f).uv(0,0));
        Random r = new Random((long) Math.floor(RiftRendererManager.time / 5));
        LazuliBufferBuilder bb = null;

        Vec3d cameraPos = camera.getPos();
        //endregion


            MinecraftClient client = MinecraftClient.getInstance();

            if (client.world != null) {

                pen.eraseAll();
                LapisRenderer.setShader(LazuliShaderRegistry.getShader(VoidedHopesShaders.RIFT_CRACK_LAZULI_SHADER));
                //LapisRenderer.setShader(GameRenderer.getPositionColorProgram());
                LapisRenderer.setShaderColor(1f, 1f, 1f,1f);
                LapisRenderer.setShaderTexture(0, PureVoidBlockRenderer.SKY_TEXTURE);
                LapisRenderer.setShaderTexture(1, PureVoidBlockRenderer.PORTAL_TEXTURE);
                LapisRenderer.disableCull();

                boolean started = false;

                   for (Entity entity : client.world.getEntities()) {
                       if (entity instanceof LivingEntity livingEntity) {

                           StatusEffectInstance effectInstance = livingEntity.getStatusEffect(ModEffects.CORRUPTION);

                           if (effectInstance != null) {
                               int amplifier = effectInstance.getAmplifier();
                               int duration = effectInstance.getDuration();
                           }




                           if (corrupted.containsKey(entity.getUuid())){
                               int tt = corrupted.get(entity.getUuid());
                               Vec3d center = livingEntity.getEyePos().add(livingEntity.getPos()).multiply(0.5);
                               Vec3d dir = center.subtract(cameraPos).normalize();
                               Vec3d lateral = dir.rotateY(1.5755f);

                               int ll = (int) (tt * 0.2);

                               for(int i = 0; i < ll; i++) {
                                   if (!started){
                                       started = true;
                                       bb = new LazuliBufferBuilder(tess, VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
                                       bb.setCamera(camera);
                                   } else {
                                       bb.drawAndReset();
                                   }

                                   Vec3d offset = LazuliMathUtils.ramdomVec3d(r).multiply(1, 1.005, 1);

                                   double t = (r.nextFloat() * 0.6) + 0.1;
                                   double l = (r.nextFloat() - 0.5) * 2.0;


                                   pen.screenPlaneMode(dir);
                                   pen.point(center.subtract(dir.multiply(1.5)).subtract(lateral.multiply(l)).add(offset), t);
                                   pen.point(center.subtract(dir.multiply(1.5)).add(lateral.multiply(l)).add(offset), t);
                                   pen.draw(bb);

                                   pen.eraseAll();
                               }
                           }
                       }
                   }
                if (started && bb != null) {
                    bb.draw();
                }
            }


        LapisRenderer.enableCull();




        alreadyRendered = true;

    }

}
