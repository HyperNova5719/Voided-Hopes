package hypernova.voidedhopes.particle.custom;

import hypernova.voidedhopes.item.TestItem;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class HeraldParticle extends SpriteBillboardParticle {
    int timer = 0;
    protected HeraldParticle(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);

    }

    @Override
    public void tick() {
        if (timer == 0) {
            timer++;
            AAALevel.addParticle(world, true, TestItem.VFX.clone().position(this.x, this.y, this.z));
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
    @Environment(EnvType.CLIENT)
    public static class HeraldParticleFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public HeraldParticleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            HeraldParticle fireflyParticle = new HeraldParticle(clientWorld, d, e, f);
            fireflyParticle.setSprite(this.spriteProvider);
            return fireflyParticle;
        }
    }
}
