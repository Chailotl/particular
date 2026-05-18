package com.chailotl.particular.sushi_bar.owo.config;

import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.ui.ConfigScreen;
import io.wispforest.owo.config.ui.ConfigScreenProviders;
import io.wispforest.owo.config.ui.OptionComponentFactory;
import io.wispforest.owo.util.ReflectionUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;

public class SushiConfigScreen extends ConfigScreen
{
	public SushiConfigScreen(Identifier modelId, ConfigWrapper<?> config, @Nullable Screen parent)
	{
		super(modelId, config, parent);

		extraFactories.put(option -> isIdentifierList(option.backingField().field()), (model, option) -> {
			var layout = new IdentifierListOptionContainer(option);
			return new OptionComponentFactory.Result(layout, layout);
		});
	}

	public SushiConfigScreen(ConfigWrapper<?> config, @Nullable Screen parent)
	{
		this(DEFAULT_MODEL_ID, config, parent);
	}

	public static SushiConfigScreen createWithCustomModel(Identifier modelId, ConfigWrapper<?> config, @Nullable Screen parent)
	{
		return new SushiConfigScreen(modelId, config, parent);
	}

	public static void registerConfig(String modId, ConfigWrapper<?> config) {
		ConfigScreenProviders.register(modId, screen -> new SushiConfigScreen(config, screen));
	}

	private static boolean isIdentifierList(Field field)
	{
		if (field.getType() != List.class) { return false; }

		return Identifier.class == ReflectionUtils.getTypeArgument(field.getGenericType(), 0);
	}
}
