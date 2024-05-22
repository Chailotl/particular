package com.chailotl.particular.particles;

import com.chailotl.particular.mixin.AccessorParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import org.jetbrains.annotations.Nullable;

public class FireflyParticle extends SpriteBillboardParticle
{
	private static final int minOffTime = 20 * 2;
	private static final int maxOffTime = 20 * 4;
	private static final int minOnTime = 10;
	private static final int maxOnTime = 20;

	private final SimplexNoiseSampler noise;
	private int ageOffset = 0;
	private int ticksUntilNextSwitch = 40;
	private boolean isOn = false;

	protected FireflyParticle(ClientWorld world, double x, double y, double z, SpriteProvider provider)
	{
		super(world, x, y, z);
		setSprite(provider);

		gravityStrength = 0;
		velocityX = 0;
		velocityY = 0;
		velocityZ = 0;

		alpha = 0;
		red = 187f/255f;
		green = 1f;
		blue = 107f/255f;

		if (world.getRandom().nextInt(10) == 1)
		{
			red = 107f/255f;
			green = 250/255f;
			blue = 1f;

			if (world.getRandom().nextInt(10) == 1)
			{
				red = 1f;
				green = 124/255f;
				blue = 107/255f;
			}
		}

		maxAge = 200;
		scale = 1/4f;

		noise = new SimplexNoiseSampler(random);
	}

	@Override
	public void tick()
	{
		super.tick();

		if (onGround)
		{
			((AccessorParticle) this).setStopped(false);
			ageOffset += 5;
		}

		if (--ticksUntilNextSwitch <= 0)
		{
			if (isOn)
			{
				isOn = false;
				ticksUntilNextSwitch = random.nextBetween(minOffTime, maxOffTime);
			}
			else
			{
				isOn = true;
				ticksUntilNextSwitch = random.nextBetween(minOnTime, maxOnTime);
			}
		}

		alpha = isOn && (maxAge - age) > 3  ? Math.min(1, alpha + 0.33f) : Math.max(0, alpha - 0.33f);

		// Broad movement
		float speedFactor = 1 / 10f;
		float noiseFactor = 1 / 300f;
		velocityX = noise.sample(age * noiseFactor, age * noiseFactor) * speedFactor;
		velocityY = noise.sample((age + ageOffset) * noiseFactor - 50f, (age + ageOffset) * noiseFactor + 100f) * speedFactor * 0.5f;
		velocityZ = noise.sample(age * noiseFactor + 100f, age * noiseFactor - 50f) * speedFactor;

		// Granular movement
		speedFactor = 1 / (10f + (float) Math.sin(Math.PI + age / 30f) * 2f);
		noiseFactor = 1 / 30f;
		velocityX += noise.sample(age * noiseFactor, age * noiseFactor) * speedFactor * 0.5f;
		velocityY += noise.sample((age + ageOffset) * noiseFactor - 50f, (age + ageOffset) * noiseFactor + 100f) * speedFactor;
		velocityZ += noise.sample(age * noiseFactor + 100f, age * noiseFactor - 50f) * speedFactor * 0.5f;
	}

	@Override
	protected int getBrightness(float tint)
	{
		return 15728880;
	}

	@Override
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
			return new FireflyParticle(world, x, y, z, provider);
		}
	}
}