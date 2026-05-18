package com.chailotl.particular.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(ViewerCountManager.class)
public interface InvokerViewerCountManager {
    @Invoker
    void invokeOnContainerOpen(World world, BlockPos pos, BlockState state);

    @Invoker
    void invokeOnContainerClose(World world, BlockPos pos, BlockState state);

}
