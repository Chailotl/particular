package com.chailotl.particular.mixin;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Particle.class)
public interface AccessorParticle
{
	@Accessor("stopped")
	void setStopped(boolean val);
}
