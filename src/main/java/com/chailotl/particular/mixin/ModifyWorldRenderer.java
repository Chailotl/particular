package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class ModifyWorldRenderer
{
	@ModifyArg(
		method = "tickRainSplashing",
		index = 0,
		at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/world/ClientWorld;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
	private ParticleEffect modifyArg(ParticleEffect original, @Local FluidState fluidState)
	{
		if (!Main.CONFIG.rainRipples()) { return original; }

		return fluidState.isIn(FluidTags.WATER) ? Main.WATER_RIPPLE : original;
	}
}