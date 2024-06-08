package com.chailotl.particular.compat;

import com.chailotl.particular.Main;
import com.chailotl.particular.Particles;
import net.frozenblock.wilderwild.registry.RegisterBlocks;

public class WilderWild
{
	public static void addLeaves()
	{
		Main.registerLeafData(RegisterBlocks.BAOBAB_LEAVES, new Main.LeafData(Particles.WW_BAOBAB_LEAF));
		Main.registerLeafData(RegisterBlocks.CYPRESS_LEAVES, new Main.LeafData(Particles.WW_CYPRESS_LEAF));
		Main.registerLeafData(RegisterBlocks.PALM_FRONDS, new Main.LeafData(Particles.WW_PALM_LEAF));
	}
}