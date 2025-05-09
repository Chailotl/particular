package com.chailotl.particular.particles.splashes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class WaterSplashRingParticle extends SpriteBillboardParticle
{
	protected final SpriteProvider provider;
	private final float width;

	WaterSplashRingParticle(ClientWorld clientWorld, double x, double y, double z, float width, SpriteProvider provider)
	{
		super(clientWorld, x, y, z);
		gravityStrength = 0;
		maxAge = 18;
		this.width = width;
		this.provider = provider;
		setSpriteForAge(provider);
	}

	public ParticleTextureSheet getType()
	{
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void tick()
	{
		super.tick();

		setSpriteForAge(provider);

		if (!world.getFluidState(BlockPos.ofFloored(x, y, z)).isIn(FluidTags.WATER))
		{
			this.markDead();
		}
	}

	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta)
	{
		Vec3d vec3d = camera.getPos();
		float f = (float) (MathHelper.lerp(tickDelta, lastX, x) - vec3d.getX());
		float g = (float) (MathHelper.lerp(tickDelta, lastY, y) - vec3d.getY());
		float h = (float) (MathHelper.lerp(tickDelta, lastZ, z) - vec3d.getZ());

		Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, 0.0F, -1.0f), new Vector3f(-1.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, -1.0F)};
		float ageDelta = MathHelper.lerp(tickDelta, age - 1, (float)age);
		float progress = ageDelta / (float)maxAge;
		float scale = width * (0.8f + 0.2f * progress);

		for (int k = 0; k < 4; ++k)
		{
			Vector3f vector3f2 = vector3fs[k];
			vector3f2.mul(scale);
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

		@Nullable
		@Override
		public Particle createParticle(SimpleParticleType SimpleParticleType, ClientWorld clientWorld, double x, double y, double z, double g, double h, double i)
		{
			return new WaterSplashRingParticle(clientWorld, x, y, z, (float) g, provider);
		}
	}
}