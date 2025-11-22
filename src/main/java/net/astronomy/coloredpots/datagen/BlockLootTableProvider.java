package net.astronomy.coloredpots.datagen;

import net.astronomy.coloredpots.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class BlockLootTableProvider extends BlockLootSubProvider {
    protected BlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        for (DyeColor color : DyeColor.values()) {
            dropSelf(ModBlocks.COLORED_DECORATED_POTS.get(color).get());
            dropSelf(ModBlocks.COLORED_FLOWER_POTS.get(color).get());

            for (Map.Entry<String, DeferredBlock<Block>> entry :
                    ModBlocks.COLORED_FLOWER_POTTED.get(color).entrySet()) {

                Block potted = entry.getValue().get();
                Block emptyPot = ModBlocks.COLORED_FLOWER_POTS.get(color).get();

                dropModPottedContents(potted, emptyPot);
            }
        }
    }

    protected void dropModPottedContents(Block potBlock, Block emptyPotBlock) {
        if (!(potBlock instanceof FlowerPotBlock pot)) {
            throw new IllegalArgumentException("dropModPottedContentsWithPot called on non-FlowerPotBlock: " + potBlock);
        }

        this.add(potBlock, b -> LootTable.lootTable()
                .withPool(applyExplosionCondition(b,
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                // Drop the pot item
                                .add(LootItem.lootTableItem(emptyPotBlock))
                ))
                .withPool(applyExplosionCondition(b,
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                // Drop the plant
                                .add(LootItem.lootTableItem(pot.getPotted()))
                )));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}