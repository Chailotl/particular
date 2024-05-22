package com.chailotl.particular.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class BirchLeafParticle extends LeafParticle
{
	private final int flip = random.nextBoolean() ? 1 : -1;

	protected BirchLeafParticle(ClientWorld world, double x, double y, double z, double r, double g, double b, SpriteProvider provider)
	{
		super(world, x, y, z, r, g, b, provider);

		//gravityFactor = 0.075f;
		angleFactor = (float) (Math.random() * 360f);
	}

	@Override
	protected float getAngle()
	{
		return (angleFactor + age / (rotateFactor + (maxAge - age) / 100f) / 2f) * flip;
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<SimpleParticleType>
	{
		private final SpriteProvider provider;

		public Factory(SpriteProvider provider)
		{
			this.provider = provider;
		}

		@Nullable
		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ)
		{
			return new BirchLeafParticle(world, x, y, z, velocityX, velocityY, velocityZ, provider);
		}
	}
}