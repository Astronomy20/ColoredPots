package net.astronomy.coloredpots;

import net.astronomy.coloredpots.block.ModBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod(value = ColoredPots.MOD_ID, dist = Dist.CLIENT)
public class ColoredPotsClient {
    public ColoredPotsClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        List<Block> pottedFerns = new ArrayList<>();

        for (DyeColor color : DyeColor.values()) {
            Map<Block, DeferredBlock<FlowerPotBlock>> pottedMap = ModBlocks.COLORED_FLOWER_POTTED.get(color);

            for (Map.Entry<Block, DeferredBlock<FlowerPotBlock>> entry : pottedMap.entrySet()) {
                Block plant = entry.getKey();
                ResourceLocation plantKey = BuiltInRegistries.BLOCK.getKey(plant);

                if (plantKey.getPath().equals("fern")) {
                    pottedFerns.add(entry.getValue().get());
                }
            }
        }

        if (!pottedFerns.isEmpty()) {
            event.register(
                    (state, level, pos, tintIndex) -> level != null && pos != null
                            ? BiomeColors.getAverageGrassColor(level, pos)
                            : GrassColor.getDefaultColor(),
                    pottedFerns.toArray(new Block[0])
            );
        }
    }
}