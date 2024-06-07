package com.chailotl.particular.particles.leaves;

import com.chailotl.particular.Main;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class LeafParticle extends SpriteBillboardParticle
{
	private static final int startAge = 10;
	protected final float rotateFactor;
	protected float gravityFactor = 0.075f;
	protected float angleFactor = 0f;
	protected final boolean flipped;
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
		flipped = Math.random() > 0.5;

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
			if (Main.CONFIG.fallingLeavesSettings.layFlatOnGround())
			{
				if (Main.CONFIG.fallingLeavesSettings.layFlatRightAngles())
				{
					angle = (float)(random.nextInt(4) / 2.0 * Math.PI);
				}
				else
				{
					angle = (float)(Math.random() * Math.PI * 2.0);
				}
			}
		}

		prevAngle = angle;

		BlockPos pos = BlockPos.ofFloored(x, y, z);
		FluidState fluidState = world.getFluidState(pos);
		if (fluidState.isIn(FluidTags.WATER))
		{
			if (gravityStrength > 0)
			{
				y = pos.getY() + fluidState.getHeight(world, pos);
				if (Main.CONFIG.fallingLeavesSettings.spawnRipples())
				{
					world.addParticle(Main.WATER_RIPPLE, x, y, z, 0, 0, 0);
				}
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
		Vec3d vec3d = camera.getPos();
		float f = (float)(MathHelper.lerp(tickDelta, prevPosX, x) - vec3d.getX());
		float g = (float)(MathHelper.lerp(tickDelta, prevPosY, y) - vec3d.getY());
		float h = (float)(MathHelper.lerp(tickDelta, prevPosZ, z) - vec3d.getZ());

		Vector3f[] vector3fs;
		float j = getSize(tickDelta);

		if (!expiring || !Main.CONFIG.fallingLeavesSettings.layFlatOnGround())
		{
			vector3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};

			Quaternionf quaternionf;
			if (angle == 0.0F)
			{
				quaternionf = camera.getRotation();
			}
			else
			{
				quaternionf = new Quaternionf(camera.getRotation());
				quaternionf.rotateZ(MathHelper.lerp(tickDelta, prevAngle, angle));
			}

			for (int k = 0; k < 4; ++k)
			{
				Vector3f vector3f = vector3fs[k];
				vector3f.rotate(quaternionf);
				vector3f.mul(j);
				vector3f.add(f, g, h);
			}
		}
		else
		{
			vector3fs = new Vector3f[]{new Vector3f(-1.0F, 0.0F, -1.0f), new Vector3f(-1.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, -1.0F)};

			for (int k = 0; k < 4; ++k)
			{
				Vector3f vector3f = vector3fs[k];
				vector3f.rotateAxis(angle, 0, 1, 0);
				vector3f.mul(j);
				vector3f.add(f, g, h);
			}
		}

		float l = getMinU();
		float m = getMaxU();
		float n = getMinV();
		float o = getMaxV();
		int p = getBrightness(tickDelta);
		if (flipped)
		{
			float temp = l;
			l = m;
			m = temp;
		}
		vertexConsumer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(m, o).color(red, green, blue, alpha).light(p).next();
		vertexConsumer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(m, n).color(red, green, blue, alpha).light(p).next();
		vertexConsumer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(l, n).color(red, green, blue, alpha).light(p).next();
		vertexConsumer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(l, o).color(red, green, blue, alpha).light(p).next();
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
			return new LeafParticle(world, x, y, z, velX, velY, velZ, provider);
		}
	}
}