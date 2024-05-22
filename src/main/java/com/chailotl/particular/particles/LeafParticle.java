package com.chailotl.particular.particles;

import com.chailotl.particular.Main;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class LeafParticle extends SpriteBillboardParticle
{
	private static final int startAge = 10;
	protected final float rotateFactor;
	protected float gravityFactor = 0.075f;
	protected float angleFactor = 0f;
	protected boolean expiring = false;

	protected LeafParticle(ClientWorld world, double x, double y, double z, double r, double g, double b, SpriteProvider provider)
	{
		super(world, x, y, z, r, g, b);
		setSprite(provider);

		collidesWithWorld = true;
		gravityStrength = gravityFactor;
		maxAge = 200;

		velocityX = 0;
		velocityY = 0;
		velocityZ = 0;

		alpha = 0;
		red = (float) r;
		green = (float) g;
		blue = (float) b;
		rotateFactor = 4f + ((float) Math.random() * 3f);

		scale = 5f / 32f;
	}

	protected float getAngle()
	{
		return angleFactor + (float) Math.sin(age / (rotateFactor + (maxAge - age) / 100f)) / 2f;
	}

	@Override
	public void tick()
	{
		super.tick();

		if (age <= startAge)
		{
			alpha += 0.1f;
			velocityY = 0;
		}
		else if (expiring)
		{
			if (alpha > 0.01f)
			{
				alpha -= 0.01f;
			}
			else
			{
				markDead();
			}
		}
		else if (onGround || velocityY == 0)
		{
			expiring = true;
			y += 0.01d;
			angle = (float) (Math.random() * 360f);
		}

		prevAngle = angle;

		BlockPos pos = BlockPos.ofFloored(x, y, z);
		FluidState fluidState = world.getFluidState(pos);
		if (fluidState.isIn(FluidTags.WATER))
		{
			if (gravityStrength > 0)
			{
				y = pos.getY() + fluidState.getHeight(world, pos);
				world.addParticle(Main.WATER_RIPPLE, x, y, z, 0, 0, 0);
			}

			// Float on top of water
			velocityY = 0;
			gravityStrength = 0;
			// investigate this
		}
		else
		{
			gravityStrength = gravityFactor;
			if (!onGround)
			{
				angle = getAngle();
			}
		}
	}

	@Override
	public ParticleTextureSheet getType()
	{
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta)
	{
		if (!expiring)
		{
			super.buildGeometry(vertexConsumer, camera, tickDelta);
			return;
		}

		Vec3d vec3d = camera.getPos();
		float f = (float)(MathHelper.lerp(tickDelta, prevPosX, x) - vec3d.getX());
		float g = (float)(MathHelper.lerp(tickDelta, prevPosY, y) - vec3d.getY());
		float h = (float)(MathHelper.lerp(tickDelta, prevPosZ, z) - vec3d.getZ());

		Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, 0.0F, -1.0f), new Vector3f(-1.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, -1.0F)};
		float j = getSize(tickDelta);

		for (int k = 0; k < 4; ++k)
		{
			Vector3f vector3f = vector3fs[k];
			vector3f.rotateAxis(angle, 0, 1, 0);
			vector3f.mul(j);
			vector3f.add(f, g, h);
		}

		float l = getMinU();
		float m = getMaxU();
		float n = getMinV();
		float o = getMaxV();
		int p = getBrightness(tickDelta);
		vertexConsumer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(m, o).color(red, green, blue, alpha).light(p).next();
		vertexConsumer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(m, n).color(red, green, blue, alpha).light(p).next();
		vertexConsumer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(l, n).color(red, green, blue, alpha).light(p).next();
		vertexConsumer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(l, o).color(red, green, blue, alpha).light(p).next();
	}

	@Environment(EnvType.CLIENT)
	public static class DefaultFactory implements ParticleFactory<SimpleParticleType>
	{
		private final SpriteProvider provider;

		public DefaultFactory(SpriteProvider provider)
		{
			this.provider = provider;
		}

		@Nullable
		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ)
		{
			return new LeafParticle(world, x, y, z, velocityX, velocityY, velocityZ, provider);
		}
	}
}