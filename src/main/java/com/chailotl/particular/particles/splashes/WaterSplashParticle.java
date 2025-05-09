package com.chailotl.particular.particles.splashes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
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

import java.awt.*;

public class WaterSplashParticle extends SpriteBillboardParticle
{
	protected final SpriteProvider provider;
	private final float width;
	private final float height;
	private final Color color;
	private final float unit;
	protected boolean colored = true;

	WaterSplashParticle(ClientWorld clientWorld, double x, double y, double z, float width, float height, SpriteProvider provider)
	{
		super(clientWorld, x, y, z);
		gravityStrength = 0;
		maxAge = 18;
		this.width = width;
		this.height = height;
		this.provider = provider;
		setSpriteForAge(provider);

		color = new Color(BiomeColors.getWaterColor(clientWorld, BlockPos.ofFloored(x, y, z)));
		unit = 2f / MinecraftClient.getInstance().particleManager.particleAtlasTexture.getWidth();
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
		float f = (float)(MathHelper.lerp(tickDelta, lastX, x) - vec3d.getX());
		float g = (float)(MathHelper.lerp(tickDelta, lastY, y) - vec3d.getY());
		float h = (float)(MathHelper.lerp(tickDelta, lastZ, z) - vec3d.getZ());

		Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, 0.0F, -1.0f), new Vector3f(-1.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, -1.0F)};
		float ageDelta = MathHelper.lerp(tickDelta, age - 1, (float)age);
		float progress = ageDelta / (float)maxAge;
		float scale = width * (0.8f + 0.2f * progress);

		for (int i = 0; i < 4; ++i)
		{
			Vector3f vector3f2 = vector3fs[i];
			vector3f2.mul(scale);
			vector3f2.add(f, g, h);
		}

		float l = getMinU() + unit;
		float m = getMaxU() - unit;
		float n = getMinV();
		float o = getMaxV();
		int light = getBrightness(tickDelta);
		int color = colored ? this.color.getRGB() : Color.white.getRGB();
		renderSide(vertexConsumer, vector3fs, 0, 1, height, l, m, n, o, light, color);
		renderSide(vertexConsumer, vector3fs, 1, 2, height, l, m, n, o, light, color);
		renderSide(vertexConsumer, vector3fs, 2, 3, height, l, m, n, o, light, color);
		renderSide(vertexConsumer, vector3fs, 3, 0, height, l, m, n, o, light, color);
	}
	private void renderSide(VertexConsumer vertexConsumer, Vector3f[] vector3fs, int a, int b, float height, float l, float m, float n, float o, int light, int color)
	{
		vertexConsumer.vertex(vector3fs[a].x(), vector3fs[a].y(), vector3fs[a].z()).texture(l, o).color(color).light(light);
		vertexConsumer.vertex(vector3fs[b].x(), vector3fs[b].y(), vector3fs[b].z()).texture(m, o).color(color).light(light);
		vertexConsumer.vertex(vector3fs[b].x(), vector3fs[b].y() + height, vector3fs[b].z()).texture(m, n).color(color).light(light);
		vertexConsumer.vertex(vector3fs[a].x(), vector3fs[a].y() + height, vector3fs[a].z()).texture(l, n).color(color).light(light);

		vertexConsumer.vertex(vector3fs[b].x(), vector3fs[b].y(), vector3fs[b].z()).texture(m, o).color(color).light(light);
		vertexConsumer.vertex(vector3fs[a].x(), vector3fs[a].y(), vector3fs[a].z()).texture(l, o).color(color).light(light);
		vertexConsumer.vertex(vector3fs[a].x(), vector3fs[a].y() + height, vector3fs[a].z()).texture(l, n).color(color).light(light);
		vertexConsumer.vertex(vector3fs[b].x(), vector3fs[b].y() + height, vector3fs[b].z()).texture(m, n).color(color).light(light);
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
			return new WaterSplashParticle(clientWorld, x, y, z, (float) g, (float) h, provider);
		}
	}
}