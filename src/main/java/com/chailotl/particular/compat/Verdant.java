package com.chailotl.particular.compat;

import com.chailotl.particular.Main;
import com.chailotl.particular.Particles;
import net.minecraft.util.Identifier;

public class Verdant
{
	private static String MOD_ID = "verdant";

	private static Identifier id(String path)
	{
		return Identifier.of(MOD_ID, path);
	}

	public static void addLeaves()
	{
		Main.registerLeafData(id("mulberry_leaves"), new Main.LeafData(Particles.VERD_MULBERRY_LEAF));
		Main.registerLeafData(id("flowering_mulberry_leaves"), new Main.LeafData(Particles.VERD_MULBERRY_LEAF));
	}
}
