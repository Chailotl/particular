package com.chailotl.particular.particles;

import com.chailotl.particular.Main;
import com.chailotl.particular.mixin.AccessorBillboardParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class WaterSplashEmitterParticle extends NoRenderParticle
{
	private final float speed;
	private final float width;
	private final float height;

	WaterSplashEmitterParticle(ClientWorld clientWorld, double x, double y, double z, float width, float speed)
	{
		super(clientWorld, x, y, z);
		speed = Math.min(2, speed);
		gravityStrength = 0;
		maxAge = 24;
		this.speed = speed;
		this.width = width;
		this.height = (speed / 2f + width / 3f);

		clientWorld.addParticle(Main.WATER_SPLASH, true, x, y, z, width, this.height, 0);
		clientWorld.addParticle(Main.WATER_SPLASH_FOAM, true, x, y, z, width, this.height, 0);
		clientWorld.addParticle(Main.WATER_SPLASH_RING, true, x, y, z, width, 0, 0);

		if (speed > 0.5)
		{
			splash(width, (1.5f/8f + speed * 1/8f) + (width / 6f), 0.15f);
		}
		else
		{
			markDead();
		}
	}

	@Override
	public void tick()
	{
		super.tick();

		if (age == 9)
		{
			world.addParticle(Main.WATER_SPLASH, true, x, y, z, width * 0.66f, height * 1.75f, 0);
			world.addParticle(Main.WATER_SPLASH_FOAM, true, x, y, z, width * 0.66f, height * 1.75f, 0);
			world.addParticle(Main.WATER_SPLASH_RING, true, x, y, z, width * 0.66f, 0, 0);
			splash(width * 0.66f, (3f/8f + speed * 1/8f) + (width / 6f), 0.05f);
		}
	}

	private void splash(float width, float speed, float spread)
	{
		for (int i = 0; i < width * 20f; ++i)
		{
			Particle droplet = MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.FALLING_WATER,
				x, y + 1/16f, z, 0, 0, 0);
			if (droplet != null)
			{
				double xVel = random.nextTriangular(0.0, spread);
				double yVel = speed * random.nextTriangular(1.0, 0.25);
				double zVel = random.nextTriangular(0.0, spread);
				droplet.setPos(x + xVel / spread * width, y + 1/16f, z + zVel / spread * width);
				droplet.setVelocity(xVel, yVel, zVel);
				droplet.setColor(1f, 1f, 1f);
				((AccessorBillboardParticle) droplet).setScale(1/8f);
			}
		}
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType>
	{
		public Factory(SpriteProvider provider) { }

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double g, double h, double i)
		{
			return new WaterSplashEmitterParticle(clientWorld, x, y, z, (float) g, (float) h);
		}
	}
}