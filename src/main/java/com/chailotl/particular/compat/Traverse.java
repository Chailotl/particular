package com.chailotl.particular.compat;

import com.chailotl.particular.Main;
import com.chailotl.particular.Particles;
import com.terraformersmc.traverse.block.TraverseBlocks;

import java.awt.*;

public class Traverse
{
	public static void addLeaves()
	{
		Main.registerLeafData(TraverseBlocks.BROWN_AUTUMNAL_LEAVES, new Main.LeafData(Particles.WHITE_OAK_LEAF, new Color(0x734B27)));
		Main.registerLeafData(TraverseBlocks.RED_AUTUMNAL_LEAVES, new Main.LeafData(Particles.WHITE_OAK_LEAF, new Color(0xB64430)));
		Main.registerLeafData(TraverseBlocks.ORANGE_AUTUMNAL_LEAVES, new Main.LeafData(Particles.WHITE_OAK_LEAF, new Color(0xEF8F1D)));
		Main.registerLeafData(TraverseBlocks.YELLOW_AUTUMNAL_LEAVES, new Main.LeafData(Particles.WHITE_OAK_LEAF, new Color(0xE9D131)));
		Main.registerLeafData(TraverseBlocks.FIR_LEAVES, new Main.LeafData(Particles.WHITE_SPRUCE_LEAF, new Color(0x1B4719)));
	}
}