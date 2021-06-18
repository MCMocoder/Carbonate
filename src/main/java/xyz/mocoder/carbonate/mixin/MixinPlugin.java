package xyz.mocoder.carbonate.mixin;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        if(targetClassName.equals("net.minecraft.recipe.BrewingRecipeRegistry")) {
            for(MethodNode method:targetClass.methods) {
                if(method.name.equals("registerDefaults")) {
                    addPotionRecipeInvoker(method,"SUGAR_DRINK","xyz/mocoder/carbonate/CarbonateMain","CDOBOTTLE","Lxyz/mocoder/carbonate/CarbonDioxideBottleItem;","AWKWARD_COLA");
                    addPotionRecipeInvoker(method,"AWKWARD_COLA","net/minecraft/item/Items","COCOA_BEANS","Lnet/minecraft/item/Item;","COLA");
                    addPotionRecipeInvoker(method,"COLA","net/minecraft/item/Items","EMERALD","Lnet/minecraft/item/Item;","CHERRY_COLA");
                    addPotionRecipeInvoker(method,"AWKWARD_COLA","net/minecraft/item/Items","APPLE","Lnet/minecraft/item/Item","SPRITE");
                    addPotionRecipeInvoker(method,"AWKWARD_COLA","net/minecraft/item/Items","MELON_SLICE","Lnet/minecraft/item/Item","MIRINDA");
                }
            }
        }
    }

    private void addPotionRecipeInvoker(MethodNode mv, String source, String ingredientOwner,String ingredient,String ingredientDesc,String output) {
        InsnList insns=new InsnList();
        insns.add(new FieldInsnNode(Opcodes.GETSTATIC,"xyz/mocoder/carbonate/CarbonateMain",source,"Lnet/minecraft/potion/Potion;"));
        insns.add(new FieldInsnNode(Opcodes.GETSTATIC,ingredientOwner,ingredient,ingredientDesc));
        insns.add(new FieldInsnNode(Opcodes.GETSTATIC,"xyz/mocoder/carbonate/CarbonateMain",output,"Lnet/minecraft/potion/Potion;"));
        insns.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"net/minecraft/recipe/BrewingRecipeRegistry","registerPotionRecipe","(Lnet/minecraft/potion/Potion;Lnet/minecraft/item/Item;Lnet/minecraft/potion/Potion;)V",false));
        for(AbstractInsnNode node:mv.instructions) {
            if(node instanceof InsnNode) {
                if(node.getOpcode()==Opcodes.RETURN) {
                    mv.instructions.insertBefore(node,insns);
                }
            }
        }
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
