package net.astronomy.coloredpots.item;

import net.astronomy.coloredpots.ColoredPots;
import net.astronomy.coloredpots.block.DecoratedPots;
import net.astronomy.coloredpots.block.FlowerPots;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ColoredPots.MOD_ID);

    public static final Supplier<CreativeModeTab> FLOWER_POTS_TAB = CREATIVE_MODE_TAB.register("flower_pots_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(FlowerPots.ORANGE_POT))
                    .title(Component.translatable("creativetab.coloredpots.flower_pots"))
                    .displayItems((itemDisplayParameters, output) -> {

                    }).build());

    public static final Supplier<CreativeModeTab> DECORATED_POTS_TAB = CREATIVE_MODE_TAB.register("decorated_pots_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(DecoratedPots.ORANGE_DECORATED_POT))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(ColoredPots.MOD_ID, "flower_pots_tab"))
                    .title(Component.translatable("creativetab.coloredpots.decorated_pots"))
                    .displayItems((itemDisplayParameters, output) -> {
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
