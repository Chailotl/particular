package com.chailotl.particular.compat;

import com.chailotl.particular.Main;
import io.github.uhq_games.regions_unexplored.block.RuBlocks;

import java.awt.*;

public class RegionsUnexplored
{
	public static void addLeaves()
	{
		// Apple oak leaves, flowering leaves, small oak leaves
		Main.registerLeaves(RuBlocks.ALPHA_LEAVES, new Main.LeafData(null)); // Classic leaves had no particles
		Main.registerLeaves(RuBlocks.ASHEN_LEAVES, new Main.LeafData(null)); // Burnt to ash
		Main.registerLeaves(RuBlocks.BLUE_MAGNOLIA_LEAVES, new Main.LeafData(null));
		Main.registerLeaves(RuBlocks.PINK_MAGNOLIA_LEAVES, new Main.LeafData(null));
		Main.registerLeaves(RuBlocks.WHITE_MAGNOLIA_LEAVES, new Main.LeafData(null));
		Main.registerLeaves(RuBlocks.ORANGE_MAPLE_LEAVES, new Main.LeafData(null));
		Main.registerLeaves(RuBlocks.RED_MAPLE_LEAVES, new Main.LeafData(null));
		Main.registerLeaves(RuBlocks.MAUVE_LEAVES, new Main.LeafData(null));
		Main.registerLeaves(RuBlocks.SILVER_BIRCH_LEAVES, new Main.LeafData(null));
		Main.registerLeaves(RuBlocks.ENCHANTED_BIRCH_LEAVES, new Main.LeafData(null));
		Main.registerLeaves(RuBlocks.DEAD_LEAVES, new Main.LeafData(Main.WHITE_OAK_LEAF, new Color(0x865D40)));
		Main.registerLeaves(RuBlocks.DEAD_PINE_LEAVES, new Main.LeafData(Main.WHITE_SPRUCE_LEAF, new Color(0x7D5C46)));
		Main.registerLeaves(RuBlocks.BLACKWOOD_LEAVES, new Main.LeafData(Main.WHITE_SPRUCE_LEAF, new Color(0x2D4519)));
		Main.registerLeaves(RuBlocks.MAPLE_LEAVES, new Main.LeafData(Main.MAPLE_LEAF));
		Main.registerLeaves(RuBlocks.BRIMWOOD_LEAVES, new Main.LeafData(Main.BRIMWOOD_LEAF));
		Main.registerLeaves(RuBlocks.BAOBAB_LEAVES, new Main.LeafData(Main.BAOBAB_LEAF));
		Main.registerLeaves(RuBlocks.KAPOK_LEAVES, new Main.LeafData(Main.KAPOK_LEAF));
	}
}