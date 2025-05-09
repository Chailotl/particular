package com.chailotl.particular.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class WaterRippleParticle extends SpriteBillboardParticle
{
	protected final SpriteProvider provider;

	protected WaterRippleParticle(ClientWorld world, double x, double y, double z, SpriteProvider provider)
	{
		super(world, x, y, z);
		maxAge = 7;
		alpha = 0.2f;
		scale = 0.25f;
		this.provider = provider;
		setSpriteForAge(provider);
	}

	@Override
	public void tick()
	{
		lastX = x;
		lastY = y;
		lastZ = z;
		if (age++ >= maxAge)
		{
			markDead();
		}
		else
		{
			setSpriteForAge(provider);
		}
	}

	@Override
	public ParticleTextureSheet getType()
	{
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta)
	{
		Vec3d vec3d = camera.getPos();
		float f = (float)(MathHelper.lerp(tickDelta, lastX, x) - vec3d.getX());
		float g = (float)(MathHelper.lerp(tickDelta, lastY, y) - vec3d.getY());
		float h = (float)(MathHelper.lerp(tickDelta, lastZ, z) - vec3d.getZ());

		Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, 0.0F, -1.0f), new Vector3f(-1.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, -1.0F)};
		float j = getSize(tickDelta);

		for (int k = 0; k < 4; ++k)
		{
			Vector3f vector3f2 = vector3fs[k];
			vector3f2.mul(j);
			vector3f2.add(f, g, h);
		}

		float l = getMinU();
		float m = getMaxU();
		float n = getMinV();
		float o = getMaxV();
		int p = getBrightness(tickDelta);
		vertexConsumer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(m, o).color(red, green, blue, alpha).light(p);
		vertexConsumer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(m, n).color(red, green, blue, alpha).light(p);
		vertexConsumer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(l, n).color(red, green, blue, alpha).light(p);
		vertexConsumer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(l, o).color(red, green, blue, alpha).light(p);
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<SimpleParticleType>
	{
		private final SpriteProvider provider;

		public Factory(SpriteProvider provider)
		{
			this.provider = provider;
		}

		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ)
		{
			return new WaterRippleParticle(world, x, y, z, provider);
		}
	}
}