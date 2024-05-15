package com.chailotl.particular.particles;

import com.chailotl.particular.particles.LeafParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;

public class OakLeafParticle extends LeafParticle
{
	protected OakLeafParticle(ClientWorld world, double x, double y, double z, double r, double g, double b, SpriteProvider provider)
	{
		super(world, x, y, z, r, g, b, provider);
	}
}