package com.chailotl.particular.mixin;

import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(EnderChestBlockEntity.class)
public interface AccessorEnderChestBlockEntity
{
	@Accessor("stateManager")
	ViewerCountManager getStateManager();
}