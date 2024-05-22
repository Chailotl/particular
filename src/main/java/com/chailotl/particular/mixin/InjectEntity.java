package com.chailotl.particular.mixin;

import com.chailotl.particular.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

@Mixin(Entity.class)
public abstract class InjectEntity
{
	@Shadow private Vec3d velocity;
	@Shadow private EntityDimensions dimensions;
	@Shadow public abstract boolean hasPassengers();
	@Shadow @Final protected Random random;
	@Shadow @Nullable public abstract LivingEntity getControllingPassenger();
	@Shadow public abstract double getX();
	@Shadow public abstract double getY();
	@Shadow public abstract double getZ();

	@Shadow public abstract World getWorld();
	@Shadow public abstract Vec3d getPos();
	@Shadow public abstract BlockPos getBlockPos();

	@Unique
	public Queue<Double> velocities = new LinkedList<>();

	@Inject(
		method = "tick",
		at = @At("TAIL")
	)
	private void onSetVelocity(CallbackInfo ci)
	{
		if (!Main.CONFIG.waterSplash()) { return; }

		velocities.offer(Math.abs(velocity.getY()));
		if (velocities.size() > 4)
		{
			velocities.poll();
		}
	}

	@Inject(
		method = "onSwimmingStart",
		at = @At("TAIL"))
	private void waterParticles(CallbackInfo ci)
	{
		if (!Main.CONFIG.waterSplash()) { return; }

		//noinspection ConstantConditions
		if ((Object) this instanceof ProjectileEntity || !getWorld().isClient) { return; }

		// Find water height
		float baseY = MathHelper.floor(getY());

		boolean foundSurface = false;
		FluidState prevState = Fluids.EMPTY.getDefaultState();
		for (int i = 0; i < 5; ++i)
		{
			FluidState nextState = getWorld().getFluidState(getBlockPos().add(0, i, 0));
			if (prevState.isOf(Fluids.WATER) && nextState.isOf(Fluids.EMPTY))
			{
				baseY += i - 1;
				foundSurface = true;
				break;
			}

			prevState = nextState;
		}

		if (!foundSurface) { return; }

		//Entity entity = hasPassengers() && getControllingPassenger() != null ? getControllingPassenger() : (Entity)(Object)this;
		//Vec3d vec3d = entity.getVelocity();

		// 3D splash
		getWorld().addParticle(Main.WATER_SPLASH_EMITTER, getX(), baseY + prevState.getHeight(), getZ(), dimensions.width(), Collections.max(velocities), 0.0);
	}
}