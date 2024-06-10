package com.chailotl.particular.compat;

import com.chailotl.particular.Main;
import com.chailotl.particular.Particles;
import net.minecraft.util.Identifier;

import java.awt.*;

public class RegionsUnexplored
{
	private static String MOD_ID = "regions_unexplored";

	private static Identifier id(String path)
	{
		return new Identifier(MOD_ID, path);
	}

	public static void addLeaves()
	{
		// Apple oak leaves, flowering leaves, small oak leaves
		Main.registerLeafData(id("alpha_leaves"), new Main.LeafData(null)); // Classic leaves had no particles
		Main.registerLeafData(id("ashen_leaves"), new Main.LeafData(null)); // Burnt to ash
		Main.registerLeafData(id("blue_magnolia_leaves"), new Main.LeafData(null));
		Main.registerLeafData(id("pink_magnolia_leaves"), new Main.LeafData(null));
		Main.registerLeafData(id("white_magnolia_leaves"), new Main.LeafData(null));
		Main.registerLeafData(id("orange_maple_leaves"), new Main.LeafData(null));
		Main.registerLeafData(id("red_maple_leaves"), new Main.LeafData(null));
		Main.registerLeafData(id("mauve_leaves"), new Main.LeafData(null));
		Main.registerLeafData(id("silver_birch_leaves"), new Main.LeafData(null));
		Main.registerLeafData(id("enchanted_birch_leaves"), new Main.LeafData(null));
		Main.registerLeafData(id("dead_leaves"), new Main.LeafData(Particles.WHITE_OAK_LEAF, new Color(0x865D40)));
		Main.registerLeafData(id("dead_pine_leaves"), new Main.LeafData(Particles.WHITE_SPRUCE_LEAF, new Color(0x7D5C46)));
		Main.registerLeafData(id("blackwood_leaves"), new Main.LeafData(Particles.WHITE_SPRUCE_LEAF, new Color(0x2D4519)));
		Main.registerLeafData(id("maple_leaves"), new Main.LeafData(Particles.MAPLE_LEAF));
		Main.registerLeafData(id("brimwood_leaves"), new Main.LeafData(Particles.BRIMWOOD_LEAF, Color.white));
		Main.registerLeafData(id("baobab_leaves"), new Main.LeafData(Particles.RU_BAOBAB_LEAF));
		Main.registerLeafData(id("kapok_leaves"), new Main.LeafData(Particles.KAPOK_LEAF));
		Main.registerLeafData(id("eucalyptus_leaves"), new Main.LeafData(Particles.EUCALYPTUS_LEAF));
		Main.registerLeafData(id("pine_leaves"), new Main.LeafData(Particles.SPRUCE_LEAF));
		Main.registerLeafData(id("redwood_leaves"), new Main.LeafData(Particles.REDWOOD_LEAF));
		Main.registerLeafData(id("magnolia_leaves"), new Main.LeafData(Particles.MAGNOLIA_LEAF));
		Main.registerLeafData(id("palm_leaves"), new Main.LeafData(Particles.RU_PALM_LEAF));
		Main.registerLeafData(id("larch_leaves"), new Main.LeafData(Particles.LARCH_LEAF, Color.white));
		Main.registerLeafData(id("golden_larch_leaves"), new Main.LeafData(Particles.GOLDEN_LARCH_LEAF, Color.white));
		Main.registerLeafData(id("socotra_leaves"), new Main.LeafData(Particles.SOCOTRA_LEAF));
		Main.registerLeafData(id("bamboo_leaves"), new Main.LeafData(Particles.BAMBOO_LEAF, Color.white));
		Main.registerLeafData(id("willow_leaves"), new Main.LeafData(Particles.WILLOW_LEAF));
		Main.registerLeafData(id("cypress_leaves"), new Main.LeafData(Particles.RU_CYPRESS_LEAF));
	}
}