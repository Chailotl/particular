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
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Particles
{
	// Vanilla leaves
	public static final SimpleParticleType OAK_LEAF = registerParticle("oak_leaf", LeafParticle.Factory::new);
	public static final SimpleParticleType BIRCH_LEAF = registerParticle("birch_leaf", SpinningLeafParticle.Factory::new);
	public static final SimpleParticleType SPRUCE_LEAF = registerParticle("spruce_leaf", ConiferLeafParticle.Factory::new);
	public static final SimpleParticleType JUNGLE_LEAF = registerParticle("jungle_leaf", LeafParticle.Factory::new);
	public static final SimpleParticleType ACACIA_LEAF = registerParticle("acacia_leaf", SpinningLeafParticle.Factory::new);
	public static final SimpleParticleType DARK_OAK_LEAF = registerParticle("dark_oak_leaf", LeafParticle.Factory::new);
	public static final SimpleParticleType AZALEA_LEAF = registerParticle("azalea_leaf", LeafParticle.Factory::new);
	public static final SimpleParticleType MANGROVE_LEAF = registerParticle("mangrove_leaf", LeafParticle.Factory::new);

	// Generic leaves
	public static final SimpleParticleType WHITE_OAK_LEAF = registerParticle("white_oak_leaf", LeafParticle.Factory::new);
	public static final SimpleParticleType WHITE_SPRUCE_LEAF = registerParticle("white_spruce_leaf", ConiferLeafParticle.Factory::new);

	// Regions Unexplored leaves
	public static final SimpleParticleType MAPLE_LEAF = registerParticle("maple_leaf", SpinningLeafParticle.Factory::new);
	public static final SimpleParticleType BRIMWOOD_LEAF = registerParticle("brimwood_leaf", SpinningLeafParticle.Factory::new);
	public static final SimpleParticleType RU_BAOBAB_LEAF = registerParticle("ru_baobab_leaf", LeafParticle.Factory::new);
	public static final SimpleParticleType KAPOK_LEAF = registerParticle("kapok_leaf", LeafParticle.Factory::new);
	public static final SimpleParticleType EUCALYPTUS_LEAF = registerParticle("eucalyptus_leaf", BigLeafParticle.Factory::new);
	public static final SimpleParticleType REDWOOD_LEAF = registerParticle("redwood_leaf", ConiferLeafParticle.Factory::new);
	public static final SimpleParticleType MAGNOLIA_LEAF = registerParticle("magnolia_leaf", LeafParticle.Factory::new);
	public static final SimpleParticleType RU_PALM_LEAF = registerParticle("ru_palm_leaf", BigLeafParticle.Factory::new);
	public static final SimpleParticleType LARCH_LEAF = registerParticle("larch_leaf", ConiferLeafParticle.Factory::new);
	public static final SimpleParticleType GOLDEN_LARCH_LEAF = registerParticle("golden_larch_leaf", ConiferLeafParticle.Factory::new);
	public static final SimpleParticleType SOCOTRA_LEAF = registerParticle("socotra_leaf", LeafParticle.Factory::new);
	public static final SimpleParticleType BAMBOO_LEAF = registerParticle("bamboo_leaf", LeafParticle.Factory::new);
	public static final SimpleParticleType WILLOW_LEAF = registerParticle("willow_leaf", LeafParticle.Factory::new);
	public static final SimpleParticleType RU_CYPRESS_LEAF = registerParticle("ru_cypress_leaf", ConiferLeafParticle.Factory::new);

	// Wilder World leaves
	public static final SimpleParticleType WW_BAOBAB_LEAF = registerParticle("ww_baobab_leaf", SpinningLeafParticle.Factory::new);
	public static final SimpleParticleType WW_CYPRESS_LEAF = registerParticle("ww_cypress_leaf", ConiferLeafParticle.Factory::new);
	public static final SimpleParticleType WW_PALM_LEAF = registerParticle("ww_palm_leaf", BigLeafParticle.Factory::new);

	// Pearfection leaves
	public static final SimpleParticleType PEAR_CALLERY_LEAF = registerParticle("pear_callery_leaf", LeafParticle.Factory::new);

	// Verdant leaves
	public static final SimpleParticleType VERD_MULBERRY_LEAF = registerParticle("verd_mulberry_leaf", SpinningLeafParticle.Factory::new);

	// Charm leaves
	public static final SimpleParticleType CHARM_EBONY_LEAF = registerParticle("charm_ebony_leaf", LeafParticle.Factory::new);


	// Other particles
	public static final SimpleParticleType WATER_RIPPLE = registerParticle("water_ripple", WaterRippleParticle.Factory::new);
	public static final SimpleParticleType ENDER_BUBBLE = registerParticle("ender_bubble", EnderBubbleParticle.Factory::new);
	public static final SimpleParticleType ENDER_BUBBLE_POP = registerParticle("ender_bubble_pop", BubblePopParticle.Factory::new);
	public static final SimpleParticleType CAVE_DUST = registerParticle("cave_dust", CaveDustParticle.Factory::new);
	public static final SimpleParticleType FIREFLY = registerParticle("firefly", FireflyParticle.Factory::new);
	public static final SimpleParticleType WATERFALL_SPRAY = registerParticle("waterfall_spray", WaterfallSprayParticle.Factory::new);
	public static final SimpleParticleType CASCADE = FabricParticleTypes.simple(true);

	// Water splash particles
	public static final SimpleParticleType WATER_SPLASH_EMITTER = FabricParticleTypes.simple(true);
	public static final SimpleParticleType WATER_SPLASH = FabricParticleTypes.simple(true);
	public static final SimpleParticleType WATER_SPLASH_FOAM = FabricParticleTypes.simple(true);
	public static final SimpleParticleType WATER_SPLASH_RING = FabricParticleTypes.simple(true);

	public static void register()
	{
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Main.MOD_ID, "cascade"), Particles.CASCADE);
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Main.MOD_ID, "water_splash_emitter"), Particles.WATER_SPLASH_EMITTER);
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Main.MOD_ID, "water_splash"), Particles.WATER_SPLASH);
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Main.MOD_ID, "water_splash_foam"), Particles.WATER_SPLASH_FOAM);
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Main.MOD_ID, "water_splash_ring"), Particles.WATER_SPLASH_RING);

		ParticleFactoryRegistry.getInstance().register(Particles.CASCADE, CascadeParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(Particles.WATER_SPLASH_EMITTER, WaterSplashEmitterParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(Particles.WATER_SPLASH, WaterSplashParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(Particles.WATER_SPLASH_FOAM, WaterSplashFoamParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(Particles.WATER_SPLASH_RING, WaterSplashRingParticle.Factory::new);
	}

	public static SimpleParticleType registerParticle(String name, ParticleFactoryRegistry.PendingParticleFactory<SimpleParticleType> constructor)
	{
		SimpleParticleType particle = FabricParticleTypes.simple();
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Main.MOD_ID, name), particle);
		ParticleFactoryRegistry.getInstance().register(particle, constructor);
		return particle;
	}
}