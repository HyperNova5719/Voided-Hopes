package hypernova.voidedhopes.mixin.client;

import hypernova.voidedhopes.AzuraThingies.Weapons.ModEffects;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class LazuliEntityRendererMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderStart(Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        float v = 0.2f;

        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.hasStatusEffect(ModEffects.CORRUPTION)) {
                v = 1.0f;
                System.out.println("Meow");
            }
        }

        set(GameRenderer.getRenderTypeEntitySolidProgram(), v, yaw);
        set(GameRenderer.getRenderTypeEntityCutoutProgram(), v, yaw);
        set(GameRenderer.getRenderTypeEntityTranslucentProgram(), v, yaw);
        set(GameRenderer.getRenderTypeEntityTranslucentCullProgram(), v, yaw);
        set(GameRenderer.getRenderTypeEntitySmoothCutoutProgram(), v, yaw);
        set(GameRenderer.getRenderTypeEntityAlphaProgram(), v, yaw);
        set(GameRenderer.getRenderTypeEyesProgram(), v, yaw);
        set(GameRenderer.getRenderTypeOutlineProgram(), v, yaw);
    }

    private void set(ShaderProgram s, float v, float yaw) {
        if (s != null) {
            s.getUniformOrDefault("corruptionState").set(v, yaw);
        }
    }
}