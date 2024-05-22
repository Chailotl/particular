package com.chailotl.particular.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class CaveDustParticle extends AscendingParticle
{
	protected CaveDustParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteProvider spriteProvider)
	{
		super(world, x, y, z, 0, 0, 0, velocityX, velocityY, velocityZ, scaleMultiplier, spriteProvider, 0, 200, 0, true);

		red = 0.5f;
		green = 0.5f;
		blue = 0.5f;

		gravityStrength = (random.nextFloat() - 0.5f) * 0.03f;
	}

	@Override
	public void tick()
	{
		super.tick();

		if (random.nextInt(180) == 0)
		{
			gravityStrength = (random.nextFloat() - 0.5f) * 0.03f;
		}

		if (age < 20)
		{
			alpha = age / 20f;
		}
		else if (age > maxAge - 20)
		{
			alpha = (maxAge - age) / 20f;
		}
	}

	public ParticleTextureSheet getType()
	{
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
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
			return new CaveDustParticle(world, x, y, z, 0, 0, 0, 1.0F, provider);
		}
	}
}