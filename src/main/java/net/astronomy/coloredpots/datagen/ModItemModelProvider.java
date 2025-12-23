package net.astronomy.coloredpots.datagen;

import net.astronomy.coloredpots.ColoredPots;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ColoredPots.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        for (DyeColor color : DyeColor.values()) {

            //Flower Pots
            String name = color.getName() + "_flower_pot";
            getBuilder(name)
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", modLoc("item/" + name));

            // Decorated Pots
            String decoratedPotName = color.getName() + "_decorated_pot";
            generateDecoratedPotItem(decoratedPotName, color);
        }
    }
    private void generateDecoratedPotItem(String name, DyeColor color) {
        getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .guiLight(BlockModel.GuiLight.FRONT)
                .texture("particle", modLoc("entity/colored_decorated_pot/" + color.getName() + "/" + color.getName() + "_decorated_pot_side"))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(0, 90, 0).translation(0, 2, 0.5f).scale(0.375f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, 90, 0).translation(0, 0, 0).scale(0.375f).end()
                .transform(ItemDisplayContext.GUI).rotation(30, 45, 0).translation(0, 0, 0).scale(0.60f).end()
                .transform(ItemDisplayContext.GROUND).rotation(0, 0, 0).translation(0, 1, 0).scale(0.25f).end()
                .transform(ItemDisplayContext.FIXED).rotation(0, 180, 0).translation(0, 0, 0).scale(0.5f).end()
                .end();
    }
}