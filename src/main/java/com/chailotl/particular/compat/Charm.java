package com.chailotl.particular.compat;

import com.chailotl.particular.Main;
import com.chailotl.particular.Particles;
import net.minecraft.util.Identifier;

public class Charm
{
	private static String MOD_ID = "charm";

	private static Identifier id(String path)
	{
		return Identifier.of(MOD_ID, path);
	}

	public static void addLeaves()
	{
		Main.registerLeafData(id("ebony_leaves"), new Main.LeafData(Particles.CHARM_EBONY_LEAF));
	}
}
