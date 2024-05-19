package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FlowableFluid.class)
public class InjectFlowableFluid
{
	@Inject(
		method = "onScheduledTick",
		at = @At("TAIL"))
	protected void spawnCascades(World world, BlockPos pos, FluidState state, CallbackInfo ci)
	{
		if (!Main.CONFIG.cascades()) { return; }

		Main.updateCascade(world, pos, state);
	}
}
