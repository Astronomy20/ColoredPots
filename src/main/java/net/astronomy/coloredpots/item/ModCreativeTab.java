package net.astronomy.coloredpots.item;

import net.astronomy.coloredpots.ColoredPots;
import net.astronomy.coloredpots.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ColoredPots.MOD_ID);

    public static final Supplier<CreativeModeTab> FLOWER_POTS_TAB =
            CREATIVE_MODE_TAB.register("flower_pots_tab", () ->
                CreativeModeTab.builder()
                        .icon(() -> new ItemStack(
                                ModBlocks.COLORED_FLOWER_POTS.get(DyeColor.ORANGE).get()
                        ))
                        .title(Component.translatable("creativetab.coloredpots.flower_pots"))
                        .displayItems((params, output) -> {
                            for (DyeColor color : DyeColor.values()) {
                                output.accept(ModBlocks.COLORED_FLOWER_POTS.get(color));
                            }
                        })
                        .build()
            );

    public static final Supplier<CreativeModeTab> DECORATED_POTS_TAB =
            CREATIVE_MODE_TAB.register("decorated_pots_tab", () ->
                CreativeModeTab.builder()
                        .icon(() -> new ItemStack(
                                ModBlocks.COLORED_DECORATED_POTS.get(DyeColor.ORANGE).get()
                        ))
                        .withTabsBefore(ResourceLocation.fromNamespaceAndPath(ColoredPots.MOD_ID, "flower_pots_tab"))
                        .title(Component.translatable("creativetab.coloredpots.decorated_pots"))
                        .displayItems((params, output) -> {
                            for (DyeColor color : DyeColor.values()) {
                                output.accept(ModBlocks.COLORED_DECORATED_POTS.get(color));
                            }
                        })
                        .build()
            );


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
