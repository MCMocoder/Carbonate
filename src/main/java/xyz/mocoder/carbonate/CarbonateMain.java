package xyz.mocoder.carbonate;

import net.fabricmc.api.ModInitializer;
import net.minecraft.MinecraftVersion;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CarbonateMain implements ModInitializer {

	public static final CarbonDioxideBottleItem CDOBOTTLE = new CarbonDioxideBottleItem(
			new Item.Settings().group(ItemGroup.BREWING).maxCount(1));

	public static final Potion AWKWARD_COLA = Registry.register(
			Registry.POTION,
			new Identifier("carbonate","awkward_drink"),
			new Potion()
	);


	public static final Potion SUGAR_DRINK = Registry.register(
			Registry.POTION,
			new Identifier("carbonate","sugar_drink"),
			new Potion()
	);

	public static final Potion COLA = Registry.register(
			Registry.POTION,
			new Identifier("carbonate","cola"),
			new Potion(
					new StatusEffectInstance[]{
							new StatusEffectInstance(StatusEffects.MINING_FATIGUE,12800),
							new StatusEffectInstance(StatusEffects.SLOWNESS,12800),
							new StatusEffectInstance(StatusEffects.REGENERATION,400),
							new StatusEffectInstance(StatusEffects.STRENGTH,12800)
					}
			)
	);

	public static final Potion CHERRY_COLA = Registry.register(
			Registry.POTION,
			new Identifier("carbonate","cherry_cola"),
			new Potion(
					new StatusEffectInstance[]{
						new StatusEffectInstance(StatusEffects.REGENERATION,400,2),
						new StatusEffectInstance(StatusEffects.ABSORPTION,1200),
						new StatusEffectInstance(StatusEffects.LUCK,120),
						new StatusEffectInstance(StatusEffects.STRENGTH,12800)
					}
			)
	);

	public static final Potion SPRITE = Registry.register(
			Registry.POTION,
			new Identifier("carbonate","sprite"),
			new Potion(
					new StatusEffectInstance[]{
							new StatusEffectInstance(StatusEffects.SLOWNESS,12800),
							new StatusEffectInstance(StatusEffects.REGENERATION,400),
							new StatusEffectInstance(StatusEffects.HASTE,12800)
					}
			)
	);

	public static final Potion MIRINDA = Registry.register(
			Registry.POTION,
			new Identifier("carbonate","mirinda"),
			new Potion(
					new StatusEffectInstance[]{
							new StatusEffectInstance(StatusEffects.SLOWNESS,12800),
							new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,12800)
					}
			)
	);


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		Registry.register(
				Registry.ITEM,
				new Identifier("carbonate","carbon_dioxide_bottle"),
				CDOBOTTLE
				);
	}
}
