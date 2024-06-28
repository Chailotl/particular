package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public class InjectChestBlockEntity extends BlockEntity
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

	public InjectChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
	{
		super(type, pos, state);
	}

	@Unique
	private static boolean getSoulSand(World world, BlockPos pos, BlockState state)
	{
		pos = pos.down();

		if (world.getBlockState(pos).getBlock() == Blocks.SOUL_SAND) { return true; }

		if (state.get(Properties.CHEST_TYPE) == ChestType.RIGHT)
		{
			BlockPos pos2 = pos.add(state.get(Properties.HORIZONTAL_FACING).rotateCounterclockwise(Direction.Axis.Y).getVector());

			return world.getBlockState(pos2).getBlock() == Blocks.SOUL_SAND;
		}

		return false;
	}

	@SuppressWarnings("InvalidInjectorMethodSignature")
	@Inject(
		method = "clientTick",
		at = @At("TAIL"))
	private static void randomlyOpen(World world, BlockPos pos, BlockState state, InjectChestBlockEntity blockEntity, CallbackInfo ci)
	{
		if (!Main.CONFIG.soulSandBubbles()) { return; }

		Block block = state.getBlock();
		if(!(block instanceof ChestBlock chest)) { return; }

		if (!state.get(Properties.WATERLOGGED) ||
			state.get(Properties.CHEST_TYPE) == ChestType.LEFT ||
			!getSoulSand(world, pos, state) ||
			ChestBlock.getInventory(chest, state, world, pos, false) == null)
		{
			if(blockEntity.isOpen) {
				blockEntity.isOpen = false;
				((AccessorChestBlockEntity) blockEntity).getStateManager().closeContainer(null, world, pos, blockEntity.getCachedState());
			}
			return;
		}

		if (--blockEntity.ticksUntilNextSwitch <= 0)
		{
			if (blockEntity.isOpen)
			{
				blockEntity.isOpen = false;
				blockEntity.ticksUntilNextSwitch = world.random.nextBetween(minClosedTime, maxClosedTime);
				((AccessorChestBlockEntity) blockEntity).getStateManager().closeContainer(null, world, pos, blockEntity.getCachedState());
			}
			else
			{
				blockEntity.isOpen = true;
				blockEntity.ticksUntilNextSwitch = world.random.nextBetween(minOpenTime, maxOpenTime);
				((AccessorChestBlockEntity) blockEntity).getStateManager().openContainer(null, world, pos, blockEntity.getCachedState());
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundCategory.AMBIENT, 1f, 1f, true);
			}
		}

		if (blockEntity.isOpen &&
			blockEntity.ticksUntilNextSwitch > 10 &&
			blockEntity.ticksUntilNextSwitch % 2 == 0)
		{
			if (state.get(Properties.CHEST_TYPE) == ChestType.SINGLE)
			{
				Main.spawnBubble(ParticleTypes.BUBBLE_COLUMN_UP, world, pos);
			}
			else
			{
				Main.spawnDoubleBubbles(ParticleTypes.BUBBLE_COLUMN_UP, world, pos, state);
			}
		}
	}
}