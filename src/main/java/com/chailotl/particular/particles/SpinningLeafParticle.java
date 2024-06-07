package com.chailotl.particular.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SpinningLeafParticle extends LeafParticle
{
	private final int flip = random.nextBoolean() ? 1 : -1;

	protected SpinningLeafParticle(ClientWorld world, double x, double y, double z, double r, double g, double b, SpriteProvider provider)
	{
		super(world, x, y, z, r, g, b, provider);

		angleFactor = (float) (Math.random() * Math.PI * 2.0);
	}

	@Override
	protected float getAngle()
	{
		return (angleFactor + age / (rotateFactor + (maxAge - age) / 100f) / 2f) * flip;
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType>
	{
		private final SpriteProvider provider;

		public Factory(SpriteProvider provider)
		{
			this.provider = provider;
		}

		@Override
		public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ)
		{
			return new SpinningLeafParticle(world, x, y, z, velX, velY, velZ, provider);
		}
	}
}