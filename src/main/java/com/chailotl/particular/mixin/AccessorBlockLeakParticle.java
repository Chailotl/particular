package com.chailotl.particular.mixin;

import net.minecraft.client.particle.BlockLeakParticle;
import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockLeakParticle.class)
public interface AccessorBlockLeakParticle
{
	@Accessor("fluid")
	Fluid getFluid();
}