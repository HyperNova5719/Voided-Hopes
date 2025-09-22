package hypernova.voidedhopes.AzuraThingies;

import hypernova.voidedhopes.AzuraThingies.LazuliLib.*;
import hypernova.voidedhopes.client.VoidedHopesShaders;
import hypernova.voidedhopes.client.renderers.block.PureVoidBlockRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.*;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class RiftRendererManager {
    public static boolean alreadyRendered = false;
    public static LazuliVertex template = new LazuliVertex().color(1f,1f,1f,1f);
    public static List<RiftRenderer> rifts = new ArrayList<>();
    public static float time = 0;

    public static void register() {
        WorldRenderEvents.START.register(context -> {
            alreadyRendered = false;
        });
    }

    public static void addRift(Vec3d pos, long seed){
        rifts.add(new RiftRenderer(time, seed, pos));
    }

    public static void render(Tessellator tess, Camera camera, float tickDelta) {
        if (alreadyRendered) return;
        ShaderProgram PureVoid = LazuliShaderRegistry.getShader(VoidedHopesShaders.PURE_VOID_LAZULI_SHADER);
        LapisRenderer.setShaderTexture(0, PureVoidBlockRenderer.SKY_TEXTURE);
        LapisRenderer.setShaderTexture(1, PureVoidBlockRenderer.PORTAL_TEXTURE);

        time += MinecraftClient.getInstance().getTickDelta();

        Iterator<RiftRenderer> iterator = rifts.iterator();
        while (iterator.hasNext()) {
            RiftRenderer rift = iterator.next();
            rift.render(tess, camera, PureVoid, time);
            if (rift.kill(time)) {
                iterator.remove();
            }
        }

    }
}
