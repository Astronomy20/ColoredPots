package net.astronomy.coloredpots.datagen;

import net.astronomy.coloredpots.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class RecipesProvider extends RecipeProvider {

    public RecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        for (DyeColor color : DyeColor.values()) {
            ItemLike dye = getDyeFromColor(color);

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COLORED_FLOWER_POTS.get(color).get())
                    .pattern("   ")
                    .pattern("BDB")
                    .pattern(" B ")
                    .define('B', Items.BRICK)
                    .define('D', dye)
                    .unlockedBy("has_brick", has(Items.BRICK))
                    .save(output);

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COLORED_DECORATED_POTS.get(color).get())
                    .pattern(" B ")
                    .pattern("BDB")
                    .pattern(" B ")
                    .define('B', Items.BRICK)
                    .define('D', dye)
                    .unlockedBy("has_brick", has(Items.BRICK))
                    .save(output);
        }
    }

    private ItemLike getDyeFromColor(DyeColor color) {
        return switch (color) {
            case WHITE -> Items.WHITE_DYE;
            case ORANGE -> Items.ORANGE_DYE;
            case MAGENTA -> Items.MAGENTA_DYE;
            case LIGHT_BLUE -> Items.LIGHT_BLUE_DYE;
            case YELLOW -> Items.YELLOW_DYE;
            case LIME -> Items.LIME_DYE;
            case PINK -> Items.PINK_DYE;
            case GRAY -> Items.GRAY_DYE;
            case LIGHT_GRAY -> Items.LIGHT_GRAY_DYE;
            case CYAN -> Items.CYAN_DYE;
            case PURPLE -> Items.PURPLE_DYE;
            case BLUE -> Items.BLUE_DYE;
            case BROWN -> Items.BROWN_DYE;
            case GREEN -> Items.GREEN_DYE;
            case RED -> Items.RED_DYE;
            case BLACK -> Items.BLACK_DYE;
        };
    }
}