package com.chailotl.particular;

import com.chailotl.particular.compat.RegionsUnexplored;
import com.chailotl.particular.compat.Traverse;
import com.chailotl.particular.compat.WilderWild;
import com.chailotl.particular.mixin.AccessorBiome;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.particle.Particle;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.FoliageColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Main implements ClientModInitializer
{
	public static final String MOD_ID = "particular";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final com.chailotl.particular.ParticularConfig CONFIG = com.chailotl.particular.ParticularConfig.createAndLoad();

	public static Identifier currentDimension;
	public static ConcurrentHashMap<BlockPos, Integer> cascades = new ConcurrentHashMap<>();
	private static float fireflyFrequency = 1f;

	private static Map<Block, LeafData> leavesData = new HashMap<>();

	@Override
	public void onInitializeClient()
	{
		LOGGER.info("I am quite particular about the effects I choose to add :3");

		// Populate leaves data
		leavesData.put(Blocks.OAK_LEAVES, new LeafData(Particles.OAK_LEAF));
		leavesData.put(Blocks.BIRCH_LEAVES, new LeafData(Particles.BIRCH_LEAF, new Color(FoliageColors.BIRCH)));
		leavesData.put(Blocks.SPRUCE_LEAVES, new LeafData(Particles.SPRUCE_LEAF, new Color(FoliageColors.SPRUCE)));
		leavesData.put(Blocks.JUNGLE_LEAVES, new LeafData(Particles.JUNGLE_LEAF));
		leavesData.put(Blocks.ACACIA_LEAVES, new LeafData(Particles.ACACIA_LEAF));
		leavesData.put(Blocks.DARK_OAK_LEAVES, new LeafData(Particles.DARK_OAK_LEAF));
		leavesData.put(Blocks.AZALEA_LEAVES, new LeafData(Particles.AZALEA_LEAF, Color.white));
		leavesData.put(Blocks.FLOWERING_AZALEA_LEAVES, new LeafData(Particles.AZALEA_LEAF, Color.white));
		leavesData.put(Blocks.MANGROVE_LEAVES, new LeafData(Particles.MANGROVE_LEAF));
		leavesData.put(Blocks.CHERRY_LEAVES, new LeafData(null));
		leavesData.put(Blocks.PALE_OAK_LEAVES, new LeafData(null));

		// Register
		Particles.register();

		// Mod compat
		if (FabricLoader.getInstance().isModLoaded("traverse"))
		{
			Traverse.addLeaves();
		}

		if (FabricLoader.getInstance().isModLoaded("regions_unexplored"))
		{
			RegionsUnexplored.addLeaves();
		}

		if (FabricLoader.getInstance().isModLoaded("wilderwild"))
		{
			WilderWild.addLeaves();
		}

		// Client events
		ClientChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
			if (!Main.CONFIG.cascades()) { return; }

			// Changing dimensions doesn't count as unloading chunks so I need to do this test
			Identifier newDimension = world.getDimension().effects();
			if (newDimension != currentDimension)
			{
				currentDimension = newDimension;
				cascades.clear();
			}

			chunk.forEachBlockMatchingPredicate(state -> state.getFluidState().isOf(Fluids.WATER), (pos, state) -> {
				updateCascade(world, pos, state.getFluidState());
			});
		});

		ClientChunkEvents.CHUNK_UNLOAD.register((world, chunk) -> {
			if (!Main.CONFIG.cascades()) { return; }

			cascades.forEach((pos, strength) -> {
				if (world.getChunk(pos).getPos().equals(chunk.getPos()))
				{
					cascades.remove(pos);
				}
			});
		});

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			cascades.clear();
		});

		ClientTickEvents.START_WORLD_TICK.register(world -> {
			Random random = world.random;

			// Set firefly frequency
			if (world.getTimeOfDay() == CONFIG.fireflySettings.startTime())
			{
				fireflyFrequency = CONFIG.fireflySettings.dailyRandom().get(random.nextInt(CONFIG.fireflySettings.dailyRandom().size()));

				//LOGGER.info(fireflyFrequency + "");
			}

			// Cascades
			if (!Main.CONFIG.cascades()) { return; }

			cascades.forEach((pos, strength) -> {
				float height = world.getFluidState(pos.up()).getHeight();
				double x = pos.getX();
				double y = (double) pos.getY() + random.nextDouble() * height + 1;
				double z =  pos.getZ();

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

				Particle cascade = MinecraftClient.getInstance().particleManager.addParticle(Particles.CASCADE, x, y, z, 0, 0, 0);
				if (cascade != null)
				{
					float size = strength / 4f * height;
					cascade.scale(1f - (1f - size) / 2f);
				}
			});
		});
	}

	public static void registerLeafData(Block block, LeafData leafData)
	{
		leavesData.put(block, leafData);
	}

	public static void registerLeafData(Identifier id, LeafData leafData)
	{
		Registries.BLOCK.getOptionalValue(id).ifPresent(block -> leavesData.put(block, leafData));
	}

	public static LeafData getLeafData(Block block)
	{
		return leavesData.getOrDefault(block, new Main.LeafData(Particles.OAK_LEAF));
	}

	public static class LeafData
	{
		private final ParticleEffect particle;
		private final BiFunction<World, BlockPos, Color> colorBiFunc;

		public LeafData(ParticleEffect particle, BiFunction<World, BlockPos, Color> colorBiFunc)
		{
			this.particle = particle;
			this.colorBiFunc = colorBiFunc;
		}

		public LeafData(ParticleEffect particle, Color color)
		{
			this(particle, (world, pos) -> color);
		}

		public LeafData(ParticleEffect particle)
		{
			this(particle, (world, pos) -> new Color(BiomeColors.getFoliageColor(world, pos)));
		}

		public ParticleEffect getParticle()
		{
			return particle;
		}

		public Color getColor(World world, BlockPos pos)
		{
			return colorBiFunc.apply(world, pos);
		}
	}

	public static void updateCascade(World world, BlockPos pos, FluidState state)
	{
		if (state.isOf(Fluids.WATER) &&
			world.getFluidState(pos.up()).isOf(Fluids.FLOWING_WATER) &&
			world.getFluidState(pos.down()).isOf(Fluids.WATER))
		{
			int strength = 0;
			if (world.getFluidState(pos.north()).isOf(Fluids.WATER)) { ++strength; }
			if (world.getFluidState(pos.east()).isOf(Fluids.WATER)) { ++strength; }
			if (world.getFluidState(pos.south()).isOf(Fluids.WATER)) { ++strength; }
			if (world.getFluidState(pos.west()).isOf(Fluids.WATER)) { ++strength; }

			if (strength > 0)
			{
				// Check if encased
				if (!world.getBlockState(pos.up().north()).isAir() &&
					!world.getBlockState(pos.up().east()).isAir() &&
					!world.getBlockState(pos.up().south()).isAir() &&
					!world.getBlockState(pos.up().west()).isAir())
				{
					return;
				}

				// This wouldn't be needed in Rust
				pos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

				if (cascades.contains(pos))
				{
					cascades.replace(pos, strength);
				}
				else
				{
					cascades.put(pos, strength);
				}
			}
			else
			{
				cascades.remove(pos);
			}
		}
		else
		{
			cascades.remove(pos);
		}
	}

	public static void spawnBubble(ParticleEffect particle, World world, BlockPos pos)
	{
		double x = pos.getX() + 0.25d + world.random.nextDouble() * 0.5d;
		double y = pos.getY() + 0.25d + world.random.nextDouble() * 0.5d;
		double z = pos.getZ() + 0.25d + world.random.nextDouble() * 0.5d;

		world.addParticleClient(particle, x, y, z, 0, 0, 0);
	}

	public static void spawnFirefly(World world, BlockPos pos, Random random)
	{
		if (random.nextDouble() > fireflyFrequency)
		{
			return;
		}

		Biome biome = world.getBiome(pos).value();
		float downfall = ((AccessorBiome)(Object) biome).getWeather().downfall();
		if ((!world.isRaining() || CONFIG.fireflySettings.canSpawnInRain()) &&
			random.nextInt(30 - (int)(10 * downfall)) == 0)
		{
			long time = world.getTimeOfDay();
			float temp = biome.getTemperature();
			if (time >= CONFIG.fireflySettings.startTime() &&
				time <= CONFIG.fireflySettings.endTime() &&
				temp >= CONFIG.fireflySettings.minTemp() &&
				temp <= CONFIG.fireflySettings.maxTemp())
			{
				world.addParticleClient(Particles.FIREFLY, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 0, 0, 0);
			}
		}
	}

	public static void spawnDoubleBubbles(ParticleEffect particle, World world, BlockPos pos, BlockState state)
	{
		ChestType chestType = state.get(Properties.CHEST_TYPE);

		int xLen = 0;
		int zLen = 0;
		int xOffset = 0;
		int zOffset = 0;

		switch (state.get(Properties.HORIZONTAL_FACING))
		{
			case NORTH ->
			{
				xLen = 1;

				if (chestType == ChestType.RIGHT)
				{
					xOffset = -1;
				}
			}
			case SOUTH ->
			{
				xLen = 1;

				if (chestType == ChestType.LEFT)
				{
					xOffset = -1;
				}
			}
			case EAST ->
			{
				zLen = 1;

				if (chestType == ChestType.RIGHT)
				{
					zOffset = -1;
				}
			}
			case WEST ->
			{
				zLen = 1;

				if (chestType == ChestType.LEFT)
				{
					zOffset = -1;
				}
			}
		}

		for (int i = 0; i < 2; ++i)
		{
			double x = pos.getX() + 0.25d + world.random.nextDouble() * (0.5d + xLen) + xOffset;
			double y = pos.getY() + 0.25d + world.random.nextDouble() * 0.5d;
			double z = pos.getZ() + 0.25d + world.random.nextDouble() * (0.5d + zLen) + zOffset;

			world.addParticleClient(particle, x, y, z, 0, 0, 0);
		}
	}

	public static void spawnChestBubbles(ParticleEffect particle, World world, BlockPos pos)
	{
		for (int i = 0; i < 10; ++i)
		{
			spawnBubble(particle, world, pos);
		}
	}

	public static void spawnDoubleChestBubbles(ParticleEffect particle, World world, BlockPos pos, BlockState state)
	{
		ChestType chestType = state.get(Properties.CHEST_TYPE);

		int xLen = 0;
		int zLen = 0;
		int xOffset = 0;
		int zOffset = 0;

		switch (state.get(Properties.HORIZONTAL_FACING))
		{
			case NORTH ->
			{
				xLen = 1;

				if (chestType == ChestType.RIGHT)
				{
					xOffset = -1;
				}
			}
			case SOUTH ->
			{
				xLen = 1;

				if (chestType == ChestType.LEFT)
				{
					xOffset = -1;
				}
			}
			case EAST ->
			{
				zLen = 1;

				if (chestType == ChestType.RIGHT)
				{
					zOffset = -1;
				}
			}
			case WEST ->
			{
				zLen = 1;

				if (chestType == ChestType.LEFT)
				{
					zOffset = -1;
				}
			}
		}

		for (int i = 0; i < 20; ++i)
		{
			double x = pos.getX() + 0.25d + world.random.nextDouble() * (0.5d + xLen) + xOffset;
			double y = pos.getY() + 0.25d + world.random.nextDouble() * 0.5d;
			double z = pos.getZ() + 0.25d + world.random.nextDouble() * (0.5d + zLen) + zOffset;

			world.addParticleClient(particle, x, y, z, 0, 0, 0);
		}
	}
}