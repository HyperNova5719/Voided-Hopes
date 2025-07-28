package hypernova.voidedhopes.mixin.client;

import com.mojang.authlib.GameProfile;
import hypernova.voidedhopes.accessors.VoidedPlayerEntity;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity implements VoidedPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile)
    {
        super(world, profile);
    }
    float screenshake = 0f;

    @Override
    public float getScreenShake()
    {
        return screenshake;
    }

    @Override
    public void addScreenshake(float val)
    {
        screenshake += val;
    }
}
