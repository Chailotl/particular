package com.chailotl.particular.compat;

import com.chailotl.particular.Main;
import com.chailotl.particular.Particles;
import net.minecraft.util.Identifier;

import java.awt.*;

public class Pearfection
{
	private static String MOD_ID = "pearfection";

	private static Identifier id(String path)
	{
		return Identifier.of(MOD_ID, path);
	}

	public static void addLeaves()
	{
		Main.registerLeafData(id("callery_leaves"), new Main.LeafData(Particles.PEAR_CALLERY_LEAF, Color.white));
		Main.registerLeafData(id("flowering_callery_leaves"), new Main.LeafData(Particles.PEAR_CALLERY_LEAF, Color.white));
	}
}
