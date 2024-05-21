package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import net.minecraft.block.*;
import net.minecraft.client.util.ParticleUtil;
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
	@Unique
	private static boolean isValidBiome(RegistryEntry<Biome> biome)
	{
		var key = biome.getKey();
		if (key.isEmpty()) { return true; }
		return !Main.EXCLUDE_CAVE_DUST.contains(key.get().getValue());
	}

	@Inject(at = @At("TAIL"), method = "randomDisplayTick")
	public void spawnParticles(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci)
	{
		Block block = state.getBlock();

		if (Main.CONFIG.fireflies())
		{
			// Fireflies
			boolean isGrass = block == Blocks.GRASS || block == Blocks.TALL_GRASS;
			if (isGrass ||
				block instanceof FlowerBlock ||
				block instanceof TallFlowerBlock)
			{
				if (isGrass && random.nextInt(6) != 0)
				{
					return;
				}

				Main.spawnFirefly(world, pos, random);
				return;
			}
		}

		if (Main.CONFIG.caveDust())
		{
			// Cave dust
			if (block == Blocks.AIR || block == Blocks.CAVE_AIR)
			{
				if (random.nextInt(700) == 0 && pos.getY() < world.getSeaLevel() && isValidBiome(world.getBiome(pos)))
				{
					float lightChance = 1f - Math.min(8, world.getLightLevel(LightType.SKY, pos)) / 8f;
					float depthChance = Math.min(1f, (world.getSeaLevel() - pos.getY()) / 96f);

					if (random.nextFloat() < lightChance * depthChance)
					{
						ParticleUtil.spawnParticle(world, pos, random, Main.CAVE_DUST);
					}
				}
			}
		}
	}
}