package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WaterFluid.class)
public class InjectFlowableFluid
{
	@Inject(
		method = "randomDisplayTick",
		at = @At("TAIL"))
	protected void spawnCascades(World world, BlockPos pos, FluidState state, Random random, CallbackInfo ci)
	{
		if (!Main.CONFIG.cascades()) { return; }

		Main.updateCascade(world, pos, state);
	}
}
