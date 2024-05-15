package com.chailotl.particular.mixin;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Particle.class)
public interface AccessorParticle
{
	@Accessor("field_21507")
	void setField_21507(boolean val);
}
