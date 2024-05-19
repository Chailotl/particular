package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.WaterBubbleParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WaterBubbleParticle.class)
public abstract class InjectWaterBubbleParticle extends SpriteBillboardParticle
{
	protected InjectWaterBubbleParticle(ClientWorld clientWorld, double d, double e, double f)
	{
		super(clientWorld, d, e, f);
	}

	@Inject(
		method = "tick",
		at = @At("TAIL"))
	private void releaseBubbles(CallbackInfo ci)
	{
		if (!Main.CONFIG.poppingBubbles()) { return; }

		if (this.dead || maxAge == 0)
		{
			Particle bubble = MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.BUBBLE_POP, x, y, z, 0, 0, 0);
			if (bubble != null)
			{
				((AccessorBillboardParticle) bubble).setScale(this.scale * 2f);
			}
		}
	}
}
