package xyz.mocoder.carbonate.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.mocoder.carbonate.CarbonateMain;

import java.lang.reflect.*;
import java.util.List;

import static xyz.mocoder.carbonate.CarbonateMain.*;

@Mixin(BrewingRecipeRegistry.class)
public abstract class BrewingRecipeMixin {
    private static final String registerPotionRecipeMapping="method_8074";
    private static final String POTION_RECIPESMapping="field_8956";
    private static final String recipeClassMapping="net.minecraft.class_1845$class_1846";

    private static Class brr=BrewingRecipeRegistry.class;
    private static Method addPotion;

    static {
        try {
            addPotion = brr.getDeclaredMethod(registerPotionRecipeMapping, Potion.class, Item.class,Potion.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static Field pr;

    static {
        try {
            pr = brr.getDeclaredField(POTION_RECIPESMapping);
            pr.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static Class recp;

    static {
        try {
            recp = Class.forName(recipeClassMapping);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Class listClass= List.class;

    private static Method add;

    static {
        try {
            add = listClass.getDeclaredMethod("add",Object.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author MCMocoder
     * @reason It's not what you can manage, Mixin!
     */
    @Overwrite
    private static void registerPotionRecipe(Potion input, Item item, Potion output) throws Exception{
        if(item==Items.SUGAR)
        {
            add.invoke(pr.get(new Object()),recp.getDeclaredConstructors()[0].newInstance(input, Ingredient.ofItems(Items.SUGAR), SUGAR_DRINK));
        }
        else
        {
            add.invoke(pr.get(new Object()),recp.getDeclaredConstructors()[0].newInstance(input, Ingredient.ofItems(item), output));
        }
    }

    @Inject(method="registerDefaults",at=@At("RETURN"))
    private static void rdInj(CallbackInfo ci) throws Exception{
        addPotion.invoke(null,SUGAR_DRINK,CDOBOTTLE,AWKWARD_COLA);
        addPotion.invoke(null,AWKWARD_COLA,Items.COCOA_BEANS,COLA);
        addPotion.invoke(null,COLA,Items.EMERALD,CHERRY_COLA);
        addPotion.invoke(null,AWKWARD_COLA,Items.APPLE,SPRITE);
        addPotion.invoke(null,AWKWARD_COLA,Items.MELON_SLICE,MIRINDA);
    }
}
