package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.BubbleColumnUpParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BubbleColumnUpParticle.class)
public abstract class InjectBubbleColumnUpParticle extends SpriteBillboardParticle
{
	protected InjectBubbleColumnUpParticle(ClientWorld clientWorld, double d, double e, double f)
	{
		super(clientWorld, d, e, f);
	}

	@Inject(
		method = "tick",
		at = @At("TAIL"))
	private void releaseBubbles(CallbackInfo ci)
	{
		if (!Main.CONFIG.poppingBubbles()) { return; }

		if (this.dead)
		{
			Particle bubble = MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.BUBBLE_POP, x, y, z, 0, 0, 0);
			if (bubble != null)
			{
				((AccessorBillboardParticle) bubble).setScale(this.scale * 2f);
			}

			//world.playSound(x, y, z, SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.AMBIENT, 1f, 1f, true);
		}
	}
}