package com.chailotl.particular.particles;

import com.chailotl.particular.Main;
import com.chailotl.particular.Particles;
import com.chailotl.particular.mixin.AccessorBillboardParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.BubbleColumnUpParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class RedstoneBubbleParticle extends BubbleColumnUpParticle
{
	protected RedstoneBubbleParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i)
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
			Particle bubble = MinecraftClient.getInstance().particleManager.addParticle(Particles.REDSTONE_BUBBLE_POP, x, y, z, 0, 0, 0);
			if (bubble != null)
			{
				((AccessorBillboardParticle) bubble).setScale(this.scale * 2f);
			}
		}
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType>
	{
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider)
		{
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i)
		{
			RedstoneBubbleParticle redstoneBubbleParticle = new RedstoneBubbleParticle(clientWorld, d, e, f, g, h, i);
			redstoneBubbleParticle.setSprite(this.spriteProvider);
			return redstoneBubbleParticle;
		}
	}
}