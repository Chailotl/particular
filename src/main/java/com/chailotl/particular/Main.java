package com.chailotl.particular;

import com.chailotl.particular.mixin.AccessorBiome;
import com.chailotl.particular.particles.*;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.networking.v1.C2SPlayChannelEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;

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
	public static Hashtable<BlockPos, Integer> cascades = new Hashtable<>();
	private static float fireflyFrequency = 1f;

	@Override
	public void onInitializeClient()
	{
		LOGGER.info("I am quite particular about the effects I choose to add :3");

		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "oak_leaf"), OAK_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "birch_leaf"), BIRCH_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "spruce_leaf"), SPRUCE_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "jungle_leaf"), JUNGLE_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "acacia_leaf"), ACACIA_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "dark_oak_leaf"), DARK_OAK_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "azalea_leaf"), AZALEA_LEAF);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "mangrove_leaf"), MANGROVE_LEAF);
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

		ParticleFactoryRegistry.getInstance().register(OAK_LEAF, OakLeafParticle.DefaultFactory::new);
		ParticleFactoryRegistry.getInstance().register(BIRCH_LEAF, BirchLeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(SPRUCE_LEAF, SpruceLeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(JUNGLE_LEAF, JungleLeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ACACIA_LEAF, AcaciaLeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(DARK_OAK_LEAF, DarkOakLeafParticle.DefaultFactory::new);
		ParticleFactoryRegistry.getInstance().register(AZALEA_LEAF, AzaleaLeafParticle.DefaultFactory::new);
		ParticleFactoryRegistry.getInstance().register(MANGROVE_LEAF, MangroveLeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(WATER_RIPPLE, WaterRippleParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ENDER_BUBBLE, EnderBubbleParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ENDER_BUBBLE_POP, EnderBubblePopParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(CAVE_DUST, CaveDustParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(FIREFLY, FireflyParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(WATERFALL_SPRAY, WaterfallSplashParticle.Factory::new);
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
				if (!world.isChunkLoaded(pos))
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
			if (world.getTimeOfDay() == 12000)
			{
				fireflyFrequency = switch(random.nextBetween(0, 5)) {
					default -> 0f;
					case 3 -> 0.33f;
					case 4 -> 0.66f;
					case 5 -> 1f;
				};

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

	public static void updateCascade(World world, BlockPos pos, FluidState state)
	{
		if (state.isOf(Fluids.WATER) &&
			world.getFluidState(pos.up()).isOf(Fluids.FLOWING_WATER) &&
			world.getFluidState(pos.down()).isOf(Fluids.WATER))
		{
			int strength = 0;
			if (world.getFluidState(pos.north()).isIn(FluidTags.WATER)) { ++strength; }
			if (world.getFluidState(pos.east()).isIn(FluidTags.WATER)) { ++strength; }
			if (world.getFluidState(pos.south()).isIn(FluidTags.WATER)) { ++strength; }
			if (world.getFluidState(pos.west()).isIn(FluidTags.WATER)) { ++strength; }

			if (strength > 0)
			{
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
		if (!world.isRaining() && random.nextInt(30 - (int)(10 * downfall)) == 0)
		{
			long time = world.getTimeOfDay();
			float temp = biome.getTemperature();
			if (time >= 12000 && time <= 23000 && temp >= 0.5 && temp < 1)
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