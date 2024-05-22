package com.chailotl.particular.particles;

import com.chailotl.particular.Main;
import com.chailotl.particular.mixin.AccessorBillboardParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.BubbleColumnUpParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class EnderBubbleParticle extends BubbleColumnUpParticle
{
	protected EnderBubbleParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i)
	{
		super(clientWorld, d, e, f, g, h, i);
	}

	@Override
	public void tick()
	{
		super.tick();

		if (!Main.CONFIG.poppingBubbles()) { return; }

		if (this.dead)
		{
			Particle bubble = MinecraftClient.getInstance().particleManager.addParticle(Main.ENDER_BUBBLE_POP, x, y, z, 0, 0, 0);
			if (bubble != null)
			{
				((AccessorBillboardParticle) bubble).setScale(this.scale * 2f);
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
			EnderBubbleParticle enderBubbleParticle = new EnderBubbleParticle(world, x, y, z, velocityX, velocityY, velocityZ);
			enderBubbleParticle.setSprite(this.spriteProvider);
			return enderBubbleParticle;
		}
	}
}