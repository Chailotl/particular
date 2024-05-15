package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AirBlock.class)
public class InjectAirBlock
{
	@Unique
	private static boolean isValidBiome(RegistryEntry<Biome> biome)
	{
		return !biome.matchesKey(BiomeKeys.LUSH_CAVES) &&
			!biome.matchesKey(BiomeKeys.DRIPSTONE_CAVES) &&
			!biome.matchesKey(BiomeKeys.DEEP_DARK);
	}

	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		if (random.nextInt(500) == 0 && pos.getY() < world.getSeaLevel() && isValidBiome(world.getBiome(pos)) && !state.isOf(Blocks.VOID_AIR))
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