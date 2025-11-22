package net.astronomy.coloredpots.datagen;

import net.astronomy.coloredpots.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BlockLootTableProvider extends BlockLootSubProvider {
    protected BlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.EXAMPLE_POT.get());

        for (DyeColor color : DyeColor.values()) {
            dropSelf(ModBlocks.COLORED_DECORATED_POTS.get(color).get());
            dropSelf(ModBlocks.COLORED_FLOWER_POTS.get(color).get());

            for (DeferredBlock<Block> pottedBlock : ModBlocks.COLORED_FLOWER_POTTED.get(color).values()) {
                Block block = pottedBlock.get();
                if (block instanceof FlowerPotBlock) {
                    dropPottedContents((FlowerPotBlock) block);
                } else {
                    dropSelf(block);
                }
            }
        }
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}