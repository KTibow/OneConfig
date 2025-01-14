package cc.polyfrost.oneconfig.internal.plugin;

import cc.polyfrost.oneconfig.internal.plugin.asm.tweakers.VigilantTransformer;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class OneConfigMixinPlugin implements IMixinConfigPlugin {
    private static boolean isVigilance = false;

    @Override
    public void onLoad(String mixinPackage) {
        try {
            Class.forName("gg.essential.vigilance.Vigilant");
            isVigilance = true;
        } catch (Exception e) {
            isVigilance = false;
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return !targetClassName.contains("vigilance") || isVigilance;
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

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        System.out.println(mixinClassName);
        if (mixinClassName.equals("cc.polyfrost.oneconfig.internal.mixin.VigilantMixin")) {
            System.out.println("A");
            VigilantTransformer.transform(targetClass);
        }
    }
}
