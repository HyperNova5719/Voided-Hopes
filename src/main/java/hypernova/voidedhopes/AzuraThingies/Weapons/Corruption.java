package hypernova.voidedhopes.AzuraThingies.Weapons;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3d;

public class Corruption extends StatusEffect {
	public Corruption(StatusEffectCategory type, int color) {
		super(type, color);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		int remainingDuration = entity.getStatusEffect(this).getDuration();

		if (entity.isPlayer() && entity.getWorld().isClient) {


			RealityBaneCorruptionManager.Corruption = remainingDuration * 0.04f;

		}

		RealityBaneCorruptionManager.addCorrupted(entity.getUuid(), remainingDuration);

		Vec3d speed = entity.getVelocity();
		if (!entity.isOnGround()){
			speed = speed.add(0,-0.04,0);
		}
        speed = speed.multiply(1.1, 1.1, 1.1);
		entity.setVelocity(speed);
		entity.disablesShield();

		entity.velocityDirty = true;
		entity.velocityModified = true;




		super.applyUpdateEffect(entity, amplifier);


	}
}
