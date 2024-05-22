package com.chailotl.particular.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.RainSplashParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class WaterfallSplashParticle extends RainSplashParticle
{
	protected WaterfallSplashParticle(ClientWorld clientWorld, double d, double e, double f)
	{
		super(clientWorld, d, e, f);
	}

	@Override
	public void tick()
	{
		prevPosX = x;
		prevPosY = y;
		prevPosZ = z;

		if (maxAge-- <= 0)
		{
			markDead();
		}
		else
		{
			velocityY -= gravityStrength;
			move(velocityX, velocityY, velocityZ);
			velocityX *= 0.9800000190734863;
			velocityY *= 0.9800000190734863;
			velocityZ *= 0.9800000190734863;

			if (onGround)
			{
				if (Math.random() < 0.5)
				{
					markDead();
				}

				velocityX *= 0.699999988079071;
				velocityZ *= 0.699999988079071;
			}

			BlockPos blockPos = BlockPos.ofFloored(x, y, z);
			double d = world.getBlockState(blockPos).getCollisionShape(world, blockPos).getEndingCoord(Direction.Axis.Y, x - (double)blockPos.getX(), z - (double)blockPos.getZ());

			FluidState fluidState = world.getFluidState(blockPos);
			if (fluidState.isStill())
			{
				d = Math.max(d, fluidState.getHeight(world, blockPos));
			}

			if (d > 0.0 && y < (double)blockPos.getY() + d)
			{
				markDead();
			}
		}
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<SimpleParticleType>
	{
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider)
		{
			this.spriteProvider = spriteProvider;
		}

		@Nullable
		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ)
		{
			WaterfallSplashParticle waterfallSplashParticle = new WaterfallSplashParticle(world, x, y, z);
			waterfallSplashParticle.setSprite(this.spriteProvider);
			return waterfallSplashParticle;
		}
	}
}