package com.chailotl.particular.compat;

import com.chailotl.particular.Main;
import com.chailotl.particular.Particles;
import net.minecraft.util.Identifier;

import java.awt.*;

public class Traverse
{
	private static String MOD_ID = "traverse";

	private static Identifier id(String path)
	{
		return new Identifier(MOD_ID, path);
	}

	public static void addLeaves()
	{
		Main.registerLeafData(id("brown_autumnal_leaves"), new Main.LeafData(Particles.WHITE_OAK_LEAF, new Color(0x734B27)));
		Main.registerLeafData(id("red_autumnal_leaves"), new Main.LeafData(Particles.WHITE_OAK_LEAF, new Color(0xB64430)));
		Main.registerLeafData(id("orange_autumnal_leaves"), new Main.LeafData(Particles.WHITE_OAK_LEAF, new Color(0xEF8F1D)));
		Main.registerLeafData(id("yellow_autumnal_leaves"), new Main.LeafData(Particles.WHITE_OAK_LEAF, new Color(0xE9D131)));
		Main.registerLeafData(id("fir_leaves"), new Main.LeafData(Particles.WHITE_SPRUCE_LEAF, new Color(0x1B4719)));
	}
}