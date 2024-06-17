package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import com.chailotl.particular.Particles;
import net.minecraft.block.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class InjectBlock
{
	@Inject(at = @At("TAIL"), method = "randomDisplayTick")
	public void spawnParticles(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci)
	{
		Block block = state.getBlock();

		if (Main.CONFIG.fireflies())
		{
			// Fireflies
			double val = random.nextDouble();
			if ((block == Blocks.GRASS && val < Main.CONFIG.fireflySettings.grass()) ||
				(block == Blocks.TALL_GRASS && val < Main.CONFIG.fireflySettings.tallGrass()) ||
				(block instanceof FlowerBlock && val < Main.CONFIG.fireflySettings.flowers()) ||
				(block instanceof TallFlowerBlock && val < Main.CONFIG.fireflySettings.tallFlowers()))
			{
				Main.spawnFirefly(world, pos, random);
				return;
			}
		}
	}
}