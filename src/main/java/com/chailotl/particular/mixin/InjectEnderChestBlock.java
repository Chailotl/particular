package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderChestBlock.class)
public class InjectEnderChestBlock
{
	@Inject(
		method = "onUse",
		at = @At("HEAD"))
	private void releaseBubbles(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir)
	{
		if (!Main.CONFIG.chestBubbles()) { return; }

		if (!state.get(Properties.WATERLOGGED) || world.getBlockState(pos.up()).isSolidBlock(world, pos.up()))
		{
			return;
		}

		Main.spawnChestBubbles(Main.ENDER_BUBBLE, world, pos);
	}
}
