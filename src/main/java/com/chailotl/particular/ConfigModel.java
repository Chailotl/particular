package com.chailotl.particular;

import io.wispforest.owo.config.annotation.*;

import java.util.Arrays;
import java.util.List;

@Modmenu(modId = Main.MOD_ID)
@Config(name = Main.MOD_ID, wrapperName = "ParticularConfig")
public class ConfigModel
{
	@SectionHeader("enabledEffects")
	public boolean waterSplash = true;
	public boolean cascades = true;
	public boolean waterfallSpray = true;
	public boolean fireflies = true;
	public boolean fallingLeaves = true;
	public boolean caveDust = true;
	public boolean chestBubbles = true;
	public boolean soulSandBubbles = true;
	public boolean barrelBubbles = true;
	public boolean poppingBubbles = true;
	public boolean rainRipples = true;
	public boolean waterDripRipples = true;
	public boolean cakeEatingParticles = true;
	public boolean emissiveLavaDrips = true;

	@SectionHeader("advancedSettings")
	@Nest
	public FireflySettings fireflySettings = new FireflySettings();

	public static class FireflySettings
	{
		@RangeConstraint(min = 0, max = 23999)
		public int startTime = 12000;
		@RangeConstraint(min = 0, max = 23999)
		public int endTime = 23000;
		public float minTemp = 0.5f;
		public float maxTemp = 0.99f;
		public boolean canSpawnInRain = false;
		@SectionHeader("frequencyModifiers")
		public List<Float> dailyRandom = Arrays.asList(
			0f, 0f, 0f, 0.33f, 0.66f, 1f
		);
		public float grass = 1/6f;
		public float tallGrass = 1/12f;
		public float flowers = 1f;
		public float tallFlowers = 0.5f;
	}
	public List<String> excludeCaveDust = Arrays.asList(
		"minecraft:lush_caves",
		"minecraft:dripstone_caves",
		"minecraft:deep_dark"
	);
}