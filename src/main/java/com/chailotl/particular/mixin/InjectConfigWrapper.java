package com.chailotl.particular.mixin;

import com.chailotl.particular.sushi_bar.owo.config.SushiConfigScreen;
import com.chailotl.particular.sushi_bar.owo.config.SushiModmenu;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.ui.ConfigScreenProviders;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConfigWrapper.class)
public class InjectConfigWrapper
{
	@Definition(id = "getAnnotation", method = "Ljava/lang/Class;getAnnotation(Ljava/lang/Class;)Ljava/lang/annotation/Annotation;")
	@Definition(id = "ModMenu", type = Modmenu.class)
	@Expression("?.getAnnotation(ModMenu.class)")
	@Inject(
			method = "<init>(Ljava/lang/Class;Lio/wispforest/owo/config/ConfigWrapper$BuilderConsumer;)V",
			at = @At(value = "MIXINEXTRAS:EXPRESSION")
	)
	private <C> void injectSushiModmenu(Class clazz, ConfigWrapper.BuilderConsumer consumer, CallbackInfo ci)
	{
		if (clazz.isAnnotationPresent(SushiModmenu.class))
		{
			SushiModmenu annotation = (SushiModmenu) clazz.getAnnotation(SushiModmenu.class);
			ConfigScreenProviders.register(
					annotation.modId(),
					screen -> SushiConfigScreen.createWithCustomModel(Identifier.of(annotation.uiModelId()), (ConfigWrapper<C>)(Object)this, screen)
			);
		}
	}
}