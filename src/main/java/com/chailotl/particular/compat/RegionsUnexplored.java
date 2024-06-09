package com.chailotl.particular.compat;

import com.chailotl.particular.Main;
import com.chailotl.particular.Particles;
import io.github.uhq_games.regions_unexplored.block.RuBlocks;

import java.awt.*;

public class RegionsUnexplored
{
	public static void addLeaves()
	{
		// Apple oak leaves, flowering leaves, small oak leaves
		Main.registerLeafData(RuBlocks.ALPHA_LEAVES, new Main.LeafData(null)); // Classic leaves had no particles
		Main.registerLeafData(RuBlocks.ASHEN_LEAVES, new Main.LeafData(null)); // Burnt to ash
		Main.registerLeafData(RuBlocks.BLUE_MAGNOLIA_LEAVES, new Main.LeafData(null));
		Main.registerLeafData(RuBlocks.PINK_MAGNOLIA_LEAVES, new Main.LeafData(null));
		Main.registerLeafData(RuBlocks.WHITE_MAGNOLIA_LEAVES, new Main.LeafData(null));
		Main.registerLeafData(RuBlocks.ORANGE_MAPLE_LEAVES, new Main.LeafData(null));
		Main.registerLeafData(RuBlocks.RED_MAPLE_LEAVES, new Main.LeafData(null));
		Main.registerLeafData(RuBlocks.MAUVE_LEAVES, new Main.LeafData(null));
		Main.registerLeafData(RuBlocks.SILVER_BIRCH_LEAVES, new Main.LeafData(null));
		Main.registerLeafData(RuBlocks.ENCHANTED_BIRCH_LEAVES, new Main.LeafData(null));
		Main.registerLeafData(RuBlocks.DEAD_LEAVES, new Main.LeafData(Particles.WHITE_OAK_LEAF, new Color(0x865D40)));
		Main.registerLeafData(RuBlocks.DEAD_PINE_LEAVES, new Main.LeafData(Particles.WHITE_SPRUCE_LEAF, new Color(0x7D5C46)));
		Main.registerLeafData(RuBlocks.BLACKWOOD_LEAVES, new Main.LeafData(Particles.WHITE_SPRUCE_LEAF, new Color(0x2D4519)));
		Main.registerLeafData(RuBlocks.MAPLE_LEAVES, new Main.LeafData(Particles.MAPLE_LEAF));
		Main.registerLeafData(RuBlocks.BRIMWOOD_LEAVES, new Main.LeafData(Particles.BRIMWOOD_LEAF, Color.white));
		Main.registerLeafData(RuBlocks.BAOBAB_LEAVES, new Main.LeafData(Particles.RU_BAOBAB_LEAF));
		Main.registerLeafData(RuBlocks.KAPOK_LEAVES, new Main.LeafData(Particles.KAPOK_LEAF));
		Main.registerLeafData(RuBlocks.EUCALYPTUS_LEAVES, new Main.LeafData(Particles.EUCALYPTUS_LEAF));
		Main.registerLeafData(RuBlocks.PINE_LEAVES, new Main.LeafData(Particles.SPRUCE_LEAF));
		Main.registerLeafData(RuBlocks.REDWOOD_LEAVES, new Main.LeafData(Particles.REDWOOD_LEAF));
		Main.registerLeafData(RuBlocks.MAGNOLIA_LEAVES, new Main.LeafData(Particles.MAGNOLIA_LEAF));
		Main.registerLeafData(RuBlocks.PALM_LEAVES, new Main.LeafData(Particles.RU_PALM_LEAF));
		Main.registerLeafData(RuBlocks.LARCH_LEAVES, new Main.LeafData(Particles.LARCH_LEAF, Color.white));
		Main.registerLeafData(RuBlocks.GOLDEN_LARCH_LEAVES, new Main.LeafData(Particles.GOLDEN_LARCH_LEAF, Color.white));
		Main.registerLeafData(RuBlocks.SOCOTRA_LEAVES, new Main.LeafData(Particles.SOCOTRA_LEAF));
		Main.registerLeafData(RuBlocks.BAMBOO_LEAVES, new Main.LeafData(Particles.BAMBOO_LEAF, Color.white));
		Main.registerLeafData(RuBlocks.WILLOW_LEAVES, new Main.LeafData(Particles.WILLOW_LEAF));
		Main.registerLeafData(RuBlocks.CYPRESS_LEAVES, new Main.LeafData(Particles.RU_CYPRESS_LEAF));
	}
}