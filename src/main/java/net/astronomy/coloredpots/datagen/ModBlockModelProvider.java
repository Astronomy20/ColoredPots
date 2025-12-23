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

                String textureName = switch (plantId) {
                    case "cactus" -> "cactus_side";
                    case "bamboo" -> "bamboo_stalk";
                    default -> plantId;
                };

                ResourceLocation plantTextureLoc = ResourceLocation.fromNamespaceAndPath(
                        plantKey.getNamespace(),
                        "block/" + textureName
                );

                pottedModel(pottedName, color, plantTextureLoc);
            }
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
}