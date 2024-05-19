package com.chailotl.particular;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = Main.MOD_ID)
@Config(name = Main.MOD_ID, wrapperName = "ParticularConfig")
public class ConfigModel
{
	public boolean waterSplash = true;
	public boolean cascades = true;
	public boolean waterfallSpray = true;
	public boolean fireflies = true;
	public boolean fallingLeaves = true;
	public boolean caveDust = true;
	public boolean chestBubbles = true;
	public boolean barrelBubbles = true;
	public boolean poppingBubbles = true;
	public boolean rainRipples = true;
	public boolean waterDripRipples = true;
	public boolean cakeEatingParticles = true;
	public boolean emissiveLavaDrips = true;
}