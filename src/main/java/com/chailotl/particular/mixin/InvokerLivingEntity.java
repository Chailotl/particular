package com.chailotl.particular.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface InvokerLivingEntity
{
	@Invoker("spawnItemParticles")
	void spawnParticles(ItemStack stack, int count);
}