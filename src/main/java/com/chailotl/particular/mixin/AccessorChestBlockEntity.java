package com.chailotl.particular.mixin;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChestBlockEntity.class)
public interface AccessorChestBlockEntity
{
	@Accessor("stateManager")
	ViewerCountManager getStateManager();
}