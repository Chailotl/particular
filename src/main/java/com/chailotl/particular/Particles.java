package com.chailotl.particular;

import com.chailotl.particular.particles.*;
import com.chailotl.particular.particles.leaves.BigLeafParticle;
import com.chailotl.particular.particles.leaves.ConiferLeafParticle;
import com.chailotl.particular.particles.leaves.LeafParticle;
import com.chailotl.particular.particles.leaves.SpinningLeafParticle;
import com.chailotl.particular.particles.splashes.WaterSplashEmitterParticle;
import com.chailotl.particular.particles.splashes.WaterSplashFoamParticle;
import com.chailotl.particular.particles.splashes.WaterSplashParticle;
import com.chailotl.particular.particles.splashes.WaterSplashRingParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.BubblePopParticle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Particles
{
	// Vanilla leaves
	public static final DefaultParticleType OAK_LEAF = registerParticle("oak_leaf", LeafParticle.Factory::new);
	public static final DefaultParticleType BIRCH_LEAF = registerParticle("birch_leaf", SpinningLeafParticle.Factory::new);
	public static final DefaultParticleType SPRUCE_LEAF = registerParticle("spruce_leaf", ConiferLeafParticle.Factory::new);
	public static final DefaultParticleType JUNGLE_LEAF = registerParticle("jungle_leaf", LeafParticle.Factory::new);
	public static final DefaultParticleType ACACIA_LEAF = registerParticle("acacia_leaf", SpinningLeafParticle.Factory::new);
	public static final DefaultParticleType DARK_OAK_LEAF = registerParticle("dark_oak_leaf", LeafParticle.Factory::new);
	public static final DefaultParticleType AZALEA_LEAF = registerParticle("azalea_leaf", LeafParticle.Factory::new);
	public static final DefaultParticleType MANGROVE_LEAF = registerParticle("mangrove_leaf", LeafParticle.Factory::new);

	// Generic leaves
	public static final DefaultParticleType WHITE_OAK_LEAF = registerParticle("white_oak_leaf", LeafParticle.Factory::new);
	public static final DefaultParticleType WHITE_SPRUCE_LEAF = registerParticle("white_spruce_leaf", ConiferLeafParticle.Factory::new);

	// Regions Unexplored leaves
	public static final DefaultParticleType MAPLE_LEAF = registerParticle("maple_leaf", SpinningLeafParticle.Factory::new);
	public static final DefaultParticleType BRIMWOOD_LEAF = registerParticle("brimwood_leaf", SpinningLeafParticle.Factory::new);
	public static final DefaultParticleType RU_BAOBAB_LEAF = registerParticle("ru_baobab_leaf", LeafParticle.Factory::new);
	public static final DefaultParticleType KAPOK_LEAF = registerParticle("kapok_leaf", LeafParticle.Factory::new);
	public static final DefaultParticleType EUCALYPTUS_LEAF = registerParticle("eucalyptus_leaf", BigLeafParticle.Factory::new);
	public static final DefaultParticleType REDWOOD_LEAF = registerParticle("redwood_leaf", ConiferLeafParticle.Factory::new);
	public static final DefaultParticleType MAGNOLIA_LEAF = registerParticle("magnolia_leaf", LeafParticle.Factory::new);
	public static final DefaultParticleType RU_PALM_LEAF = registerParticle("ru_palm_leaf", BigLeafParticle.Factory::new);
	public static final DefaultParticleType LARCH_LEAF = registerParticle("larch_leaf", ConiferLeafParticle.Factory::new);
	public static final DefaultParticleType GOLDEN_LARCH_LEAF = registerParticle("golden_larch_leaf", ConiferLeafParticle.Factory::new);
	public static final DefaultParticleType SOCOTRA_LEAF = registerParticle("socotra_leaf", LeafParticle.Factory::new);
	public static final DefaultParticleType BAMBOO_LEAF = registerParticle("bamboo_leaf", LeafParticle.Factory::new);
	public static final DefaultParticleType WILLOW_LEAF = registerParticle("willow_leaf", LeafParticle.Factory::new);
	public static final DefaultParticleType RU_CYPRESS_LEAF = registerParticle("ru_cypress_leaf", ConiferLeafParticle.Factory::new);

	// Wilder World leaves
	public static final DefaultParticleType WW_BAOBAB_LEAF = registerParticle("ww_baobab_leaf", SpinningLeafParticle.Factory::new);
	public static final DefaultParticleType WW_CYPRESS_LEAF = registerParticle("ww_cypress_leaf", ConiferLeafParticle.Factory::new);
	public static final DefaultParticleType WW_PALM_LEAF = registerParticle("ww_palm_leaf", BigLeafParticle.Factory::new);

	// Other particles
	public static final DefaultParticleType WATER_RIPPLE = registerParticle("water_ripple", WaterRippleParticle.Factory::new);
	public static final DefaultParticleType ENDER_BUBBLE = registerParticle("ender_bubble", EnderBubbleParticle.Factory::new);
	public static final DefaultParticleType ENDER_BUBBLE_POP = registerParticle("ender_bubble_pop", BubblePopParticle.Factory::new);
	public static final DefaultParticleType REDSTONE_BUBBLE = registerParticle("redstone_bubble", RedstoneBubbleParticle.Factory::new);
	public static final DefaultParticleType REDSTONE_BUBBLE_POP = registerParticle("redstone_bubble_pop", BubblePopParticle.Factory::new);
	public static final DefaultParticleType CAVE_DUST = registerParticle("cave_dust", CaveDustParticle.Factory::new);
	public static final DefaultParticleType FIREFLY = registerParticle("firefly", FireflyParticle.Factory::new);
	public static final DefaultParticleType WATERFALL_SPRAY = registerParticle("waterfall_spray", WaterfallSprayParticle.Factory::new);
	public static final DefaultParticleType CASCADE = FabricParticleTypes.simple(true);

	// Water splash particles
	public static final DefaultParticleType WATER_SPLASH_EMITTER = FabricParticleTypes.simple(true);
	public static final DefaultParticleType WATER_SPLASH = FabricParticleTypes.simple(true);
	public static final DefaultParticleType WATER_SPLASH_FOAM = FabricParticleTypes.simple(true);
	public static final DefaultParticleType WATER_SPLASH_RING = FabricParticleTypes.simple(true);

	public static void register()
	{
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "cascade"), Particles.CASCADE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "water_splash_emitter"), Particles.WATER_SPLASH_EMITTER);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "water_splash"), Particles.WATER_SPLASH);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "water_splash_foam"), Particles.WATER_SPLASH_FOAM);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "water_splash_ring"), Particles.WATER_SPLASH_RING);

		ParticleFactoryRegistry.getInstance().register(Particles.CASCADE, CascadeParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(Particles.WATER_SPLASH_EMITTER, WaterSplashEmitterParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(Particles.WATER_SPLASH, WaterSplashParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(Particles.WATER_SPLASH_FOAM, WaterSplashFoamParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(Particles.WATER_SPLASH_RING, WaterSplashRingParticle.Factory::new);
	}

	public static DefaultParticleType registerParticle(String name, ParticleFactoryRegistry.PendingParticleFactory<DefaultParticleType> constructor)
	{
		DefaultParticleType particle = FabricParticleTypes.simple();
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(Main.MOD_ID, name), particle);
		ParticleFactoryRegistry.getInstance().register(particle, constructor);
		return particle;
	}
}