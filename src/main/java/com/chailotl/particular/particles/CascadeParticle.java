package com.chailotl.particular.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class CascadeParticle extends SpriteBillboardParticle
{
	protected final SpriteProvider provider;

	protected CascadeParticle(ClientWorld clientWorld, double x, double y, double z, SpriteProvider provider)
	{
		super(clientWorld, x, y, z);
		this.provider = provider;
		maxAge = 9;
		scale = 1f;
		gravityStrength = 0.4f;
		setVelocity(random.nextDouble() * 0.25f - 0.125f, 0, random.nextDouble() * 0.25f - 0.125f);
		setSpriteForAge(provider);
		removeIfInsideSolidBlock();
	}

	@Override
	public void tick()
	{
		super.tick();

		removeIfInsideSolidBlock();

		setSpriteForAge(provider);
	}

	private void removeIfInsideSolidBlock()
	{
		BlockPos pos = BlockPos.ofFloored(new Vec3d(x, y, z));
		if (world.getBlockState(pos).isSolidBlock(world, pos))
		{
			alpha = 0;
			markDead();
		}
	}

	@Override
	public ParticleTextureSheet getType()
	{
		return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
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
			return new CascadeParticle(world, x, y, z, provider);
		}
	}
}