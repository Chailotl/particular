package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import com.chailotl.particular.Particles;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WaterFluid.class)
public class InjectWaterFluid
{
	@Inject(
		method = "randomDisplayTick",
		at = @At("TAIL"))
	private void waterParticles(World world, BlockPos pos, FluidState state, Random random, CallbackInfo ci)
	{
		if (!Main.CONFIG.waterfallSpray()) { return; }

		if (!state.isStill() &&
			world.getFluidState(pos.down()).isIn(FluidTags.WATER))
		{
			// Splishy splashy
			for (int i = 0; i < 2; ++i)
			{
				if (state.get(Properties.FALLING))
				{
					double x = pos.getX();
					double y = (double) pos.getY() + random.nextDouble();
					double z = pos.getZ();

					if (random.nextBoolean())
					{
						x += random.nextDouble();
						z += random.nextBetween(0, 1);
					}
					else
					{
						x += random.nextBetween(0, 1);
						z += random.nextDouble();
					}

					world.addParticleClient(Particles.WATERFALL_SPRAY, x, y, z, 0.0, 0.0, 0.0);
				}
				else
				{
					double x = (double) pos.getX() + random.nextDouble();
					double y = (double) pos.getY() + (random.nextDouble() * state.getHeight());
					double z = (double) pos.getZ() + random.nextDouble();
					Vec3d vel = state.getVelocity(world, pos).multiply(0.075);
					world.addParticleClient(Particles.WATERFALL_SPRAY, x, y, z, vel.x, 0.0, vel.z);
				}
			}
		}
	}
}