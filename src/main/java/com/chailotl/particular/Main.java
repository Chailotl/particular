package com.chailotl.particular;

import com.chailotl.particular.compat.Traverse;
import com.chailotl.particular.mixin.AccessorBiome;
import com.chailotl.particular.particles.*;
import com.chailotl.particular.particles.leaves.BigLeafParticle;
import com.chailotl.particular.particles.leaves.ConiferLeafParticle;
import com.chailotl.particular.particles.leaves.LeafParticle;
import com.chailotl.particular.particles.leaves.SpinningLeafParticle;
import com.chailotl.particular.particles.splashes.WaterSplashEmitterParticle;
import com.chailotl.particular.particles.splashes.WaterSplashFoamParticle;
import com.chailotl.particular.particles.splashes.WaterSplashParticle;
import com.chailotl.particular.particles.splashes.WaterSplashRingParticle;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.particle.BubblePopParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
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

	public static final DefaultParticleType OAK_LEAF = FabricParticleTypes.simple();
	public static final DefaultParticleType BIRCH_LEAF = FabricParticleTypes.simple();
	public static final DefaultParticleType SPRUCE_LEAF = FabricParticleTypes.simple();
	public static final DefaultParticleType JUNGLE_LEAF = FabricParticleTypes.simple();
	public static final DefaultParticleType ACACIA_LEAF = FabricParticleTypes.simple();
	public static final DefaultParticleType DARK_OAK_LEAF = FabricParticleTypes.simple();
	public static final DefaultParticleType AZALEA_LEAF = FabricParticleTypes.simple();
	public static final DefaultParticleType MANGROVE_LEAF = FabricParticleTypes.simple();
	public static final DefaultParticleType WHITE_OAK_LEAF = FabricParticleTypes.simple();
	public static final DefaultParticleType WHITE_SPRUCE_LEAF = FabricParticleTypes.simple();
	public static final DefaultParticleType WATER_RIPPLE = FabricParticleTypes.simple();
	public static final DefaultParticleType ENDER_BUBBLE = FabricParticleTypes.simple();
	public static final DefaultParticleType ENDER_BUBBLE_POP = FabricParticleTypes.simple();
	public static final DefaultParticleType CAVE_DUST = FabricParticleTypes.simple();
	public static final DefaultParticleType FIREFLY = FabricParticleTypes.simple();
	public static final DefaultParticleType WATERFALL_SPRAY = FabricParticleTypes.simple();
	public static final DefaultParticleType CASCADE = FabricParticleTypes.simple(true);
	public static final DefaultParticleType WATER_SPLASH_EMITTER = FabricParticleTypes.simple(true);
	public static final DefaultParticleType WATER_SPLASH = FabricParticleTypes.simple(true);
	public static final DefaultParticleType WATER_SPLASH_FOAM = FabricParticleTypes.simple(true);
	public static final DefaultParticleType WATER_SPLASH_RING = FabricParticleTypes.simple(true);

	public static Identifier currentDimension;
	public static ConcurrentHashMap<BlockPos, Integer> cascades = new ConcurrentHashMap<>();
	private static float fireflyFrequency = 1f;

	private static Map<Block, LeafData> leavesData = new HashMap<>(Map.of(
		Blocks.OAK_LEAVES, new LeafData(OAK_LEAF),
		Blocks.BIRCH_LEAVES, new LeafData(BIRCH_LEAF, new Color(FoliageColors.getBirchColor())),
		Blocks.SPRUCE_LEAVES, new LeafData(SPRUCE_LEAF, new Color(FoliageColors.getSpruceColor())),
		Blocks.JUNGLE_LEAVES, new LeafData(JUNGLE_LEAF),
		Blocks.ACACIA_LEAVES, new LeafData(ACACIA_LEAF),
		Blocks.DARK_OAK_LEAVES, new LeafData(DARK_OAK_LEAF),
		Blocks.AZALEA_LEAVES, new LeafData(AZALEA_LEAF, Color.white),
		Blocks.FLOWERING_AZALEA_LEAVES, new LeafData(AZALEA_LEAF, Color.white),
		Blocks.MANGROVE_LEAVES, new LeafData(MANGROVE_LEAF),
		Blocks.CHERRY_LEAVES, new LeafData(null)
	));

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

	@Override
	public void onInitializeClient()
	{
		LOGGER.info("I am quite particular about the effects I choose to add :3");

		if (FabricLoader.getInstance().isModLoaded("traverse"))
		{
			Traverse.addLeaves();
		}

		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "oak_leaf"), OAK_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "birch_leaf"), BIRCH_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "spruce_leaf"), SPRUCE_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "jungle_leaf"), JUNGLE_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "acacia_leaf"), ACACIA_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "dark_oak_leaf"), DARK_OAK_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "azalea_leaf"), AZALEA_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "mangrove_leaf"), MANGROVE_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "white_oak_leaf"), WHITE_OAK_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "white_spruce_leaf"), WHITE_SPRUCE_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "water_ripple"), WATER_RIPPLE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "ender_bubble"), ENDER_BUBBLE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "ender_bubble_pop"), ENDER_BUBBLE_POP);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "cave_dust"), CAVE_DUST);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "firefly"), FIREFLY);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "waterfall_spray"), WATERFALL_SPRAY);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "cascade"), CASCADE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "water_splash_emitter"), WATER_SPLASH_EMITTER);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "water_splash"), WATER_SPLASH);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "water_splash_foam"), WATER_SPLASH_FOAM);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "water_splash_ring"), WATER_SPLASH_RING);

		ParticleFactoryRegistry.getInstance().register(OAK_LEAF, LeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(BIRCH_LEAF, SpinningLeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(SPRUCE_LEAF, ConiferLeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(JUNGLE_LEAF, BigLeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ACACIA_LEAF, SpinningLeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(DARK_OAK_LEAF, LeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(AZALEA_LEAF, LeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(MANGROVE_LEAF, BigLeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(WHITE_OAK_LEAF, LeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(WHITE_SPRUCE_LEAF, ConiferLeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(WATER_RIPPLE, WaterRippleParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ENDER_BUBBLE, EnderBubbleParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ENDER_BUBBLE_POP, BubblePopParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(CAVE_DUST, CaveDustParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(FIREFLY, FireflyParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(WATERFALL_SPRAY, WaterfallSprayParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(CASCADE, CascadeParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(WATER_SPLASH_EMITTER, WaterSplashEmitterParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(WATER_SPLASH, WaterSplashParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(WATER_SPLASH_FOAM, WaterSplashFoamParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(WATER_SPLASH_RING, WaterSplashRingParticle.Factory::new);

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

				Particle cascade = MinecraftClient.getInstance().particleManager.addParticle(CASCADE, x, y, z, 0, 0, 0);
				if (cascade != null)
				{
					float size = strength / 4f * height;
					cascade.scale(1f - (1f - size) / 2f);
				}
			});
		});
	}

	public static void registerLeaves(Block block, LeafData leafData)
	{
		leavesData.put(block, leafData);
	}

	public static LeafData getLeafData(Block block)
	{
		return leavesData.getOrDefault(block, new Main.LeafData(Main.OAK_LEAF));
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

		world.addParticle(particle, x, y, z, 0, 0, 0);
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
				world.addParticle(Main.FIREFLY, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 0, 0, 0);
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

			world.addParticle(particle, x, y, z, 0, 0, 0);
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

			world.addParticle(particle, x, y, z, 0, 0, 0);
		}
	}
}