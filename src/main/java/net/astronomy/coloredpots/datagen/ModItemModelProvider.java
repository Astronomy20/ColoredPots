package net.astronomy.coloredpots.datagen;

import net.astronomy.coloredpots.ColoredPots;
import net.astronomy.coloredpots.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ColoredPots.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        for (DyeColor color : DyeColor.values()) {

            DeferredBlock<? extends Block> emptyPotDeferred =
                    ModBlocks.COLORED_FLOWER_POTS.get(color);

            String name = color.getName() + "_flower_pot";
            getBuilder(name)
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", modLoc("item/" + name));
        }
    }
}