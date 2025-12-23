package net.astronomy.coloredpots.datagen;

import net.astronomy.coloredpots.ColoredPots;
import net.astronomy.coloredpots.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Map;

public class ModBlockModelProvider extends BlockModelProvider {

    public ModBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ColoredPots.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        for (DyeColor color : DyeColor.values()) {

            String emptyPotName = color.getName() + "_flower_pot";
            emptyPotModel(emptyPotName, color);

            Map<Block, DeferredBlock<FlowerPotBlock>> pottedMap = ModBlocks.COLORED_FLOWER_POTTED.get(color);

            for (Map.Entry<Block, DeferredBlock<FlowerPotBlock>> entry : pottedMap.entrySet()) {

                Block plant = entry.getKey();
                ResourceLocation plantKey = BuiltInRegistries.BLOCK.getKey(plant);
                if (plantKey == null) continue;

                String plantId = plantKey.getPath();
                String pottedName = color.getName() + "_potted_" + plantId;

                // Handle special plant types
                switch (plantId) {
                    case "bamboo" -> pottedBambooModel(pottedName, color);
                    case "cactus" -> pottedCactusModel(pottedName, color);
                    case "fern" -> pottedFernModel(pottedName, color);
                    case "azalea" -> pottedAzaleaModel(pottedName, color, false);
                    case "flowering_azalea" -> pottedAzaleaModel(pottedName, color, true);
                    default -> {
                        String textureName = plantId;
                        ResourceLocation plantTextureLoc = ResourceLocation.fromNamespaceAndPath(
                                plantKey.getNamespace(),
                                "block/" + textureName
                        );
                        pottedModel(pottedName, color, plantTextureLoc);
                    }
                }
            }

            String decoratedPotName = color.getName() + "_decorated_pot";
            decoratedPotModel(decoratedPotName, color);
        }
    }

    private void emptyPotModel(String name, DyeColor color) {
        withExistingParent(name, mcLoc("block/flower_pot"))
                .texture("particle", modLoc("block/" + color.getName() + "_flower_pot"))
                .texture("flowerpot", modLoc("block/" + color.getName() + "_flower_pot"));
    }

    private void pottedModel(String name, DyeColor color, ResourceLocation plantTexture) {
        withExistingParent(name, mcLoc("block/flower_pot_cross"))
                .renderType("cutout")
                .texture("particle", modLoc("block/" + color.getName() + "_flower_pot"))
                .texture("flowerpot", modLoc("block/" + color.getName() + "_flower_pot"))
                .texture("plant", plantTexture);
    }

    private void pottedBambooModel(String name, DyeColor color) {
        withExistingParent(name, mcLoc("block/potted_bamboo"))
                .renderType("cutout")
                .texture("particle", modLoc("block/" + color.getName() + "_flower_pot"))
                .texture("flowerpot", modLoc("block/" + color.getName() + "_flower_pot"))
                .texture("dirt", mcLoc("block/dirt"))
                .texture("bamboo", mcLoc("block/bamboo_stalk"))
                .texture("leaf", mcLoc("block/bamboo_singleleaf"));
    }

    private void pottedCactusModel(String name, DyeColor color) {
        withExistingParent(name, mcLoc("block/potted_cactus"))
                .renderType("cutout")
                .texture("particle", modLoc("block/" + color.getName() + "_flower_pot"))
                .texture("flowerpot", modLoc("block/" + color.getName() + "_flower_pot"))
                .texture("cactus_top", mcLoc("block/cactus_top"))
                .texture("cactus", mcLoc("block/cactus_side"));
    }

    private void pottedFernModel(String name, DyeColor color) {
        withExistingParent(name, mcLoc("block/tinted_flower_pot_cross"))
                .renderType("cutout")
                .texture("plant", mcLoc("block/fern"))
                .texture("particle", modLoc("block/" + color.getName() + "_flower_pot"))
                .texture("flowerpot", modLoc("block/" + color.getName() + "_flower_pot"));
    }

    private void pottedAzaleaModel(String name, DyeColor color, boolean flowering) {
        String prefix = flowering ? "potted_flowering_azalea_bush" : "potted_azalea_bush";

        withExistingParent(name, mcLoc("block/potted_azalea_bush"))
                .renderType("cutout")
                .texture("particle", modLoc("block/" + color.getName() + "_flower_pot"))
                .texture("flowerpot", modLoc("block/" + color.getName() + "_flower_pot"))
                .texture("plant", mcLoc("block/" + prefix + "_plant"))
                .texture("side", mcLoc("block/" + prefix + "_side"))
                .texture("top", mcLoc("block/" + prefix + "_top"));
    }

    private void decoratedPotModel(String name, DyeColor color) {
        ResourceLocation coloredTerracotta = mcLoc("block/" + color.getName() + "_terracotta");
        ResourceLocation coloredBase = modLoc("entity/colored_decorated_pot/" + color + "/" + color.getName() + "_decorated_pot_base");
        ResourceLocation coloredSide = modLoc("entity/colored_decorated_pot/" + color + "/" + color.getName() + "_decorated_pot_side");

        withExistingParent(name, mcLoc("block/decorated_pot"))
                .texture("particle", coloredTerracotta)
                .texture("base", coloredBase)
                .texture("side", coloredSide);
    }
}