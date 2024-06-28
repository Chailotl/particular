package com.chailotl.particular;

import com.chailotl.sushi_bar.owo.config.SushiModmenu;
import io.wispforest.owo.config.annotation.*;
import io.wispforest.owo.ui.core.Color;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

@SushiModmenu(modId = Main.MOD_ID)
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
	public boolean redstoneBubbles = false;
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
	@Nest
	public FallingLeavesSettings fallingLeavesSettings = new FallingLeavesSettings();
	@Nest
	public CaveDustSettings caveDustSettings = new CaveDustSettings();

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
		@RangeConstraint(min = 0, max = 1)
		public float grass = 1/6f;
		@RangeConstraint(min = 0, max = 1)
		public float tallGrass = 1/12f;
		@RangeConstraint(min = 0, max = 1)
		public float flowers = 1f;
		@RangeConstraint(min = 0, max = 1)
		public float tallFlowers = 0.5f;
	}

	public static class FallingLeavesSettings
	{
		@PredicateConstraint("minOne")
		public int spawnChance = 60;
		public boolean spawnRipples = true;
		public boolean layFlatOnGround = true;
		public boolean layFlatRightAngles = false;

		public static boolean minOne(int num)
		{
			return num >= 1;
		}
	}

	public static class CaveDustSettings
	{
		@PredicateConstraint("minOne")
		public int spawnChance = 700;
		@PredicateConstraint("minOne")
		public int baseMaxAge = 200;
		public Color color = Color.ofRgb(0x808080);
		@PredicateConstraint("minZero")
		public int fadeDuration = 20;
		public float maxAcceleration = 0.03f;
		@PredicateConstraint("minOne")
		public int accelChangeChance = 180;
		public List<Identifier> excludeBiomes = Arrays.asList(
			new Identifier("minecraft:lush_caves"),
			new Identifier("minecraft:dripstone_caves"),
			new Identifier("minecraft:deep_dark")
		);

		public static boolean minOne(int num)
		{
			return num >= 1;
		}

		public static boolean minZero(int num)
		{
			return num >= 0;
		}
	}
}