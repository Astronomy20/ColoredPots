package net.astronomy.coloredpots.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

public class FlowerPots extends ModBlocks {
    public static final DeferredBlock<Block> WHITE_POT = registerBlock("white_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> GRAY_POT = registerBlock("gray_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> BROWN_POT = registerBlock("brown_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> ORANGE_POT = registerBlock("orange_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> LIME_POT = registerBlock("lime_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> CYAN_POT = registerBlock("cyan_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> BLUE_POT = registerBlock("blue_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> MAGENTA_POT = registerBlock("magenta_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> LIGHT_GRAY_POT = registerBlock("light_gray_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> BLACK_POT = registerBlock("black_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> RED_POT = registerBlock("red_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> YELLOW_POT = registerBlock("yellow_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> GREEN_POT = registerBlock("green_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> LIGHT_BLUE_POT = registerBlock("light_blue_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> PURPLE_POT = registerBlock("purple_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final DeferredBlock<Block> PINK_POT = registerBlock("pink_flower_pot",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));
}
