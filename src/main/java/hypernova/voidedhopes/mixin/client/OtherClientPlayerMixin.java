package hypernova.voidedhopes.mixin.client;

import com.mojang.authlib.GameProfile;
import hypernova.voidedhopes.accessors.VoidedPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OtherClientPlayerEntity.class)
public abstract class OtherClientPlayerMixin extends PlayerEntity implements VoidedPlayerEntity {
    public OtherClientPlayerMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Override
    public void addScreenshake(float val)
    {

    }
}
