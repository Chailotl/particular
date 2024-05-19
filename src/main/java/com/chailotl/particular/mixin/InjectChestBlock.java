package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
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

@Mixin(ChestBlock.class)
public class InjectChestBlock
{
	@Inject(
		method = "onUse",
		at = @At("HEAD"))
	private void releaseBubbles(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir)
	{
		if (!Main.CONFIG.chestBubbles()) { return; }

		if (!state.get(Properties.WATERLOGGED) || ChestBlock.getInventory((ChestBlock) Blocks.CHEST, state, world, pos, false) == null)
		{
			return;
		}

		if (state.get(Properties.CHEST_TYPE) == ChestType.SINGLE)
		{
			Main.spawnChestBubbles(ParticleTypes.BUBBLE_COLUMN_UP, world, pos);
		}
		else
		{
			Main.spawnDoubleChestBubbles(ParticleTypes.BUBBLE_COLUMN_UP, world, pos, state);
		}
	}
}