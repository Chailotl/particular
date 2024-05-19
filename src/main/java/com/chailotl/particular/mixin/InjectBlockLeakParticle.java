package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import net.minecraft.client.particle.BlockLeakParticle;
import net.minecraft.fluid.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockLeakParticle.class)
public class InjectBlockLeakParticle
{
	@Inject(
		method = "getBrightness",
		at = @At("HEAD"),
		cancellable = true)
	public void getBrightness(float tint, CallbackInfoReturnable<Integer> cir)
	{
		if (!Main.CONFIG.emissiveLavaDrips()) { return; }

		if (((AccessorBlockLeakParticle) this).getFluid().matchesType(Fluids.LAVA))
		{
			cir.setReturnValue(15728880);
		}
	}
}