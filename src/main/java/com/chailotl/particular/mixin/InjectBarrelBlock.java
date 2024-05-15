package com.chailotl.particular.mixin;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BarrelBlock.class)
public class InjectBarrelBlock
{
	@Inject(
		method = "onUse",
		at = @At("HEAD"))
	private void releaseBubbles(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir)
	{
		Direction direction = state.get(Properties.FACING);

		if (direction == Direction.DOWN || !world.getFluidState(pos.add(direction.getVector())).isIn(FluidTags.WATER))
		{
			return;
		}

		for (int i = 0; i < 10; ++i)
		{
			ParticleUtil.spawnParticle(world, pos, direction, ParticleTypes.BUBBLE_COLUMN_UP, Vec3d.ZERO, 0.55);
		}
	}
}