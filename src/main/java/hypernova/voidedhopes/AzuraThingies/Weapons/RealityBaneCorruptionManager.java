package hypernova.voidedhopes.AzuraThingies.Weapons;


import hypernova.voidedhopes.AzuraThingies.LazuliLib.LazuliPostEffectShader;
import hypernova.voidedhopes.AzuraThingies.LazuliLib.LazuliShaderRegistry;
import hypernova.voidedhopes.AzuraThingies.RiftRendererManager;
import hypernova.voidedhopes.client.VoidedHopesShaders;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class RealityBaneCorruptionManager {

    public static float Corruption = 0;
    public static float UserCorruption = 0;

    public static void register(){
        ClientTickEvents.START_WORLD_TICK.register(t ->{

            Corruption = Corruption * 0.95f;
            UserCorruption = UserCorruption * 0.95f;


            //System.out.println(Corruption + UserCorruption);

        });

        HudRenderCallback.EVENT.register(new HudRenderCallback() {
            @Override
            public void onHudRender(DrawContext drawContext, float v) {

                if (Corruption + UserCorruption > 0.01 && MinecraftClient.getInstance().currentScreen == null) {
                    LazuliPostEffectShader bp = LazuliShaderRegistry.getPostProcessor(VoidedHopesShaders.POST2);
                    bp.passes.get(0).getProgram().getUniformByNameOrDummy("TT").set((float) (RiftRendererManager.time / 1000f) % 1f);
                    bp.passes.get(0).getProgram().getUniformByNameOrDummy("Corruption").set(1f - (1f / (float) (1.0 + (Corruption + UserCorruption))));
                    bp.render(0);
                }
            }
        });
    }
}
