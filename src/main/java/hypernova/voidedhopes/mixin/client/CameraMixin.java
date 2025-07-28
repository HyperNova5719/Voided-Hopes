package hypernova.voidedhopes.mixin.client;

import hypernova.voidedhopes.accessors.VoidedPlayerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow
    public abstract Vec3d getPos();

    @Shadow public abstract float getYaw();

    @Shadow @Final
    private Vector3f horizontalPlane;

    @Shadow @Final private Vector3f verticalPlane;

    @Shadow @Final private Vector3f diagonalPlane;

    @Shadow protected abstract void setPos(Vec3d pos);

    @Shadow private Vec3d pos;

    @Shadow protected abstract void setRotation(float yaw, float pitch);

    @Shadow private float yaw;

    @Shadow private float cameraY;
    @Shadow private float lastCameraY;
    @Shadow private boolean ready;
    @Shadow private BlockView area;
    @Shadow private Entity focusedEntity;
    @Shadow private boolean thirdPerson;
    @Shadow private float pitch;

    float shakeTime;

    @Inject(method = "update", at = @At("TAIL"))
    void onUpdate(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci)
    {
        if(!(focusedEntity instanceof PlayerEntity player))
            return;
        if(!(focusedEntity instanceof OtherClientPlayerEntity) && focusedEntity instanceof VoidedPlayerEntity winged)
        {
            if(winged.getScreenShake() > 0.001f)
            {
                if(shakeTime == 0)
                    shakeTime = focusedEntity.getWorld().getRandom().nextFloat() * 6f;
                applyScreenShake(tickDelta, winged.getScreenShake());
                winged.addScreenshake(-tickDelta * winged.getScreenShake() / 5f);
            }
            else
                shakeTime = 0f;
        }
    }


    void applyScreenShake(float tickDelta, float strength)
    {
        float delta = MinecraftClient.getInstance().getLastFrameDuration();
        shakeTime += delta + strength * 0.65f;
        setRotation(yaw + MathHelper.lerpAngleDegrees(delta * 4f, 0,
                        (float)Math.sin(shakeTime + strength * 2.13f) * strength),
                pitch + MathHelper.lerpAngleDegrees(delta * 2f, 0,
                        (float)Math.sin(shakeTime + 1.43f + strength * 1.71f) * strength));
        Vec3d dir = new Vec3d(0f, 0f, -1).rotateX((float)Math.toRadians(-pitch)).rotateY((float)Math.toRadians(-yaw));
        setPos(pos.add(dir.multiply(strength / 4f)));
    }
}

