package com.chailotl.particular.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CakeBlock.class)
public class InjectCakeBlock
{
	@Inject(at = @At("TAIL"), method = "tryEat")
	private static void makeSounds(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<ActionResult> cir)
	{
		ItemStack cake = Items.CAKE.getDefaultStack();
		player.playSound(player.getEatSound(cake), 1f, 1f);
		((InvokerLivingEntity) player).spawnParticles(cake, 5);
	}
}