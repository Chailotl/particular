package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import com.chailotl.particular.Particles;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.tag.FluidTags;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class InjectWorldRenderer
{
	@Inject(
			method = "tickRainSplashing",
			slice = @Slice(
					from = @At(value = "INVOKE", target = "Lnet/minecraft/block/CampfireBlock;isLitCampfire(Lnet/minecraft/block/BlockState;)Z"),
					to = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V")
			),
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/client/render/WorldRenderer;client:Lnet/minecraft/client/MinecraftClient;",
					opcode = Opcodes.GETFIELD))
	private void modifyParticleEffect(Camera camera, CallbackInfo ci, @Local FluidState fluidState, @Local LocalRef<ParticleEffect> particleEffect)
	{
		if (fluidState.isIn(FluidTags.WATER) && Main.CONFIG.rainRipples())
		{
			particleEffect.set(Particles.WATER_RIPPLE);
		}
	}
}