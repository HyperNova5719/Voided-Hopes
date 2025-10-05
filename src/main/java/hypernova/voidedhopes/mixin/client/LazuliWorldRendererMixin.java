package hypernova.voidedhopes.mixin.client;

import hypernova.voidedhopes.AzuraThingies.LazuliLib.LazuliFreeze;
import hypernova.voidedhopes.AzuraThingies.RiftRendererManager;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class LazuliWorldRendererMixin {

    @Inject(
            method = "render",
            at = @At("HEAD"),
            cancellable = true
    )
    private void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, org.joml.Matrix4f projectionMatrix, CallbackInfo ci) {
        if (!LazuliFreeze.doRender()) {
            LazuliFreeze.updateTime(tickDelta);
            ci.cancel();
        }
    }
}
