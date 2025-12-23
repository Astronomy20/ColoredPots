package net.astronomy.coloredpots.datagen;

import net.astronomy.coloredpots.ColoredPots;
import net.astronomy.coloredpots.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Map;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ColoredPots.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        for (DyeColor color : DyeColor.values()) {

            DeferredBlock<DecoratedPotBlock> decoratedPotDef = ModBlocks.COLORED_DECORATED_POTS.get(color);
            String decoratedPotName = color.getName() + "_decorated_pot";

            simpleBlock(decoratedPotDef.get(), generatedModel("block/" + decoratedPotName));

            DeferredBlock<? extends Block> emptyPotDef = ModBlocks.COLORED_FLOWER_POTS.get(color);
            Block emptyPot = emptyPotDef.get();
            String emptyPotName = color.getName() + "_flower_pot";

            getVariantBuilder(emptyPot)
                    .partialState()
                    .setModels(new ConfiguredModel(
                            generatedModel("block/" + emptyPotName)
                    ));

            Map<Block, DeferredBlock<FlowerPotBlock>> pottedMap =
                    ModBlocks.COLORED_FLOWER_POTTED.get(color);

            for (Map.Entry<Block, DeferredBlock<FlowerPotBlock>> entry : pottedMap.entrySet()) {

                Block plant = entry.getKey();
                Block pottedBlock = entry.getValue().get();

                ResourceLocation plantKey = BuiltInRegistries.BLOCK.getKey(plant);
                if (plantKey == null) continue;

                String plantId = plantKey.getPath();
                String pottedName = color.getName() + "_potted_" + plantId;

                getVariantBuilder(pottedBlock)
                        .partialState()
                        .setModels(new ConfiguredModel(
                                generatedModel("block/" + pottedName)
                        ));
            }
        }
    }

    /**
     * References a generated block model only.
     * Resolves to:
     * src/generated/resources/assets/coloredpots/models/block/*.json
     */
    private ModelFile generatedModel(String path) {
        return models().getExistingFile(modLoc(path));
    }
}