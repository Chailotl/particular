package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.particle.Particle;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(LeavesBlock.class)
public class InjectLeavesBlock
{
	@Inject(
		method = "randomDisplayTick",
		at = @At("HEAD"))
	private void dropLeaves(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci)
	{
		if (random.nextInt(60) == 0)
		{
			BlockPos blockPos = pos.down();
			BlockState blockState = world.getBlockState(blockPos);
			if (!Block.isFaceFullSquare(blockState.getCollisionShape(world, blockPos), Direction.UP))
			{
				double x = pos.getX() + 0.02d + random.nextDouble() * 0.96d;
				double y = pos.getY() - 0.05d;
				double z = pos.getZ() + 0.02d + random.nextDouble() * 0.96d;

				ParticleEffect particle = null;
				Color color = new Color(BiomeColors.getFoliageColor(world, pos));

				Block block = state.getBlock();
				if (block == Blocks.OAK_LEAVES)
				{
					particle = Main.OAK_LEAF;
				}
				if (block == Blocks.BIRCH_LEAVES)
				{
					particle = Main.BIRCH_LEAF;
					color = new Color(FoliageColors.getBirchColor());
				}
				else if (block == Blocks.SPRUCE_LEAVES)
				{
					particle = Main.SPRUCE_LEAF;
					color = new Color(FoliageColors.getSpruceColor());
				}
				else if (block == Blocks.JUNGLE_LEAVES)
				{
					particle = Main.JUNGLE_LEAF;
				}
				else if (block == Blocks.ACACIA_LEAVES)
				{
					particle = Main.ACACIA_LEAF;
				}
				else if (block == Blocks.DARK_OAK_LEAVES)
				{
					particle = Main.DARK_OAK_LEAF;
				}
				else if (block == Blocks.AZALEA_LEAVES || block == Blocks.FLOWERING_AZALEA_LEAVES)
				{
					particle = Main.AZALEA_LEAF;
					color = Color.white;
				}
				else if (block == Blocks.MANGROVE_LEAVES)
				{
					particle = Main.MANGROVE_LEAF;
					//color = new Color(FoliageColors.getMangroveColor());
				}

				if (particle == null)
				{
					return;
				}

				Particle leaf = MinecraftClient.getInstance().particleManager.addParticle(particle, x, y, z, 0, 0, 0);
				if (leaf != null)
				{
					leaf.setColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
				}
			}
		}
	}
}