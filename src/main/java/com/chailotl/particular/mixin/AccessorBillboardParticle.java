package com.chailotl.particular.mixin;

import net.minecraft.client.particle.BillboardParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BillboardParticle.class)
public interface AccessorBillboardParticle
{
	@Accessor("scale")
	void setScale(float scale);
}