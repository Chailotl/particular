package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderChestBlockEntity.class)
public class InjectEnderChestBlockEntity extends BlockEntity
{
	@Unique
	private int ticksUntilNextSwitch = 20;
	@Unique
	private boolean isOpen = false;

	@Unique
	private static final int minClosedTime = 20 * 8;
	@Unique
	private static final int maxClosedTime = 20 * 24;
	@Unique
	private static final int minOpenTime = 20 * 2;
	@Unique
	private static final int maxOpenTime = 20 * 3;

	public InjectEnderChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
	{
		super(type, pos, state);
	}

	@SuppressWarnings("InvalidInjectorMethodSignature")
	@Inject(
		method = "clientTick",
		at = @At("TAIL"))
	private static void randomlyOpen(World world, BlockPos pos, BlockState state, InjectEnderChestBlockEntity blockEntity, CallbackInfo ci)
	{
		if (!Main.CONFIG.chestBubbles()) { return; }

		if (!state.get(Properties.WATERLOGGED) ||
			world.getBlockState(pos.down()).getBlock() != Blocks.SOUL_SAND ||
			world.getBlockState(pos.up()).isSolidBlock(world, pos.up()))
		{
			return;
		}

		if (--blockEntity.ticksUntilNextSwitch <= 0)
		{
			if (blockEntity.isOpen)
			{
				blockEntity.isOpen = false;
				blockEntity.ticksUntilNextSwitch = world.random.nextBetween(minClosedTime, maxClosedTime);
				((AccessorEnderChestBlockEntity) blockEntity).getStateManager().closeContainer(null, world, pos, blockEntity.getCachedState());
			}
			else
			{
				blockEntity.isOpen = true;
				blockEntity.ticksUntilNextSwitch = world.random.nextBetween(minOpenTime, maxOpenTime);
				((AccessorEnderChestBlockEntity) blockEntity).getStateManager().openContainer(null, world, pos, blockEntity.getCachedState());
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundCategory.AMBIENT, 1f, 1f, true);
			}
		}

		if (blockEntity.isOpen &&
			blockEntity.ticksUntilNextSwitch > 10 &&
			blockEntity.ticksUntilNextSwitch % 2 == 0)
		{
			Main.spawnBubble(Main.ENDER_BUBBLE, world, pos);
		}
	}
}