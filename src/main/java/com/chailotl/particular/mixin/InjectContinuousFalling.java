package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import com.chailotl.particular.Particles;
import net.minecraft.client.particle.BlockLeakParticle;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockLeakParticle.ContinuousFalling.class)
public abstract class InjectContinuousFalling extends SpriteBillboardParticle
{
	@Shadow @Final protected ParticleEffect nextParticle;

	protected InjectContinuousFalling(ClientWorld clientWorld, double d, double e, double f)
	{
		super(clientWorld, d, e, f);
	}

	@Inject(
		method = "updateVelocity",
		at = @At("TAIL"))
	private void addRipples(CallbackInfo ci)
	{
		if (!Main.CONFIG.waterDripRipples()) { return; }

		if (nextParticle != ParticleTypes.SPLASH)
		{
			return;
		}

		BlockPos pos = BlockPos.ofFloored(x, y, z);
		FluidState fluidState = world.getFluidState(pos);
		if (fluidState.isIn(FluidTags.WATER))
		{
			float yWater = pos.getY() + fluidState.getHeight(world, pos);
			if (y < yWater)
			{
				world.addParticleClient(Particles.WATER_RIPPLE, x, yWater, z, 0, 0, 0);
			}
		}
	}
}