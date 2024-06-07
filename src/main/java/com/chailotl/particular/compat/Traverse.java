package com.chailotl.particular.compat;

import com.chailotl.particular.Main;
import com.terraformersmc.traverse.block.TraverseBlocks;

import java.awt.*;

public class Traverse
{
	public static void addLeaves()
	{
		Main.registerLeaves(TraverseBlocks.BROWN_AUTUMNAL_LEAVES, new Main.LeafData(Main.WHITE_OAK_LEAF, new Color(0x734B27)));
		Main.registerLeaves(TraverseBlocks.RED_AUTUMNAL_LEAVES, new Main.LeafData(Main.WHITE_OAK_LEAF, new Color(0xB64430)));
		Main.registerLeaves(TraverseBlocks.ORANGE_AUTUMNAL_LEAVES, new Main.LeafData(Main.WHITE_OAK_LEAF, new Color(0xEF8F1D)));
		Main.registerLeaves(TraverseBlocks.YELLOW_AUTUMNAL_LEAVES, new Main.LeafData(Main.WHITE_OAK_LEAF, new Color(0xE9D131)));
		Main.registerLeaves(TraverseBlocks.FIR_LEAVES, new Main.LeafData(Main.WHITE_SPRUCE_LEAF, new Color(0x1B4719)));
	}
}