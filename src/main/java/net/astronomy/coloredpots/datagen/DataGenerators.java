package net.astronomy.coloredpots.datagen;

import net.astronomy.coloredpots.ColoredPots;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.core.HolderLookup;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ColoredPots.MOD_ID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        generator.addProvider(event.includeServer(),
                new LootTableProvider(
                        packOutput,
                        Collections.emptySet(),
                        List.of(new LootTableProvider.SubProviderEntry(
                                BlockLootTableProvider::new,
                                LootContextParamSets.BLOCK
                        )),
                        lookup
                )
        );

        if (false) {
            generator.addProvider(
                    event.includeClient(),
                    new ModTextureGenerator(packOutput, fileHelper)
            );
        }

        generator.addProvider(
                event.includeClient(),
                new ModBlockModelProvider(packOutput, fileHelper)
        );

        generator.addProvider(
                event.includeClient(),
                new ModBlockStateProvider(packOutput, fileHelper)
        );

        generator.addProvider(
                event.includeClient(),
                new ModItemModelProvider(packOutput, fileHelper)
        );
    }
}