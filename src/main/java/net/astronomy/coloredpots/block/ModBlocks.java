package net.astronomy.coloredpots.block;

import net.astronomy.coloredpots.ColoredPots;
import net.astronomy.coloredpots.block.entity.ColoredDecoratedPotBlockEntity;
import net.astronomy.coloredpots.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(ColoredPots.MOD_ID);

    public static final Map<DyeColor, DeferredBlock<DecoratedPotBlock>> COLORED_DECORATED_POTS =
            new EnumMap<>(DyeColor.class);

    public static final Map<DyeColor, DeferredBlock<FlowerPotBlock>> COLORED_FLOWER_POTS =
            new EnumMap<>(DyeColor.class);

    public static final Map<DyeColor, Map<Block, DeferredBlock<FlowerPotBlock>>> COLORED_FLOWER_POTTED =
            new EnumMap<>(DyeColor.class);

    static {
        for (DyeColor color : DyeColor.values()) {
            COLORED_FLOWER_POTTED.put(color, new HashMap<>());
        }
        registerColoredPots();
    }

    private static void registerColoredPots() {

        Block[] plants = {
                // Saplings
                Blocks.OAK_SAPLING,
                Blocks.SPRUCE_SAPLING,
                Blocks.BIRCH_SAPLING,
                Blocks.JUNGLE_SAPLING,
                Blocks.ACACIA_SAPLING,
                Blocks.DARK_OAK_SAPLING,
                Blocks.CHERRY_SAPLING,
                Blocks.MANGROVE_PROPAGULE,

                // Flowers
                Blocks.DANDELION,
                Blocks.POPPY,
                Blocks.BLUE_ORCHID,
                Blocks.ALLIUM,
                Blocks.AZURE_BLUET,
                Blocks.RED_TULIP,
                Blocks.ORANGE_TULIP,
                Blocks.WHITE_TULIP,
                Blocks.PINK_TULIP,
                Blocks.OXEYE_DAISY,
                Blocks.CORNFLOWER,
                Blocks.LILY_OF_THE_VALLEY,
                Blocks.WITHER_ROSE,
                Blocks.TORCHFLOWER,

                // Mushrooms
                Blocks.RED_MUSHROOM,
                Blocks.BROWN_MUSHROOM,

                // Fungi
                Blocks.CRIMSON_FUNGUS,
                Blocks.WARPED_FUNGUS,

                // Roots
                Blocks.CRIMSON_ROOTS,
                Blocks.WARPED_ROOTS,

                // Others
                Blocks.FERN,
                Blocks.DEAD_BUSH,
                Blocks.CACTUS,
                Blocks.BAMBOO,
                Blocks.AZALEA,
                Blocks.FLOWERING_AZALEA
        };

        for (DyeColor color : DyeColor.values()) {

            // Decorated Pots
            DeferredBlock<DecoratedPotBlock> decoratedPot = BLOCKS.register(
                    color.getName() + "_decorated_pot",
                    () -> new DecoratedPotBlock(
                            BlockBehaviour.Properties.of()
                                    .mapColor(color)
                                    .strength(0.0F, 0.0F)
                                    .noOcclusion()
                                    .pushReaction(PushReaction.DESTROY)
                    ) {
                        @Override
                        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
                            return new ColoredDecoratedPotBlockEntity(pos, state);
                        }
                    }
            );
            COLORED_DECORATED_POTS.put(color, decoratedPot);
            registerBlockItem(color.getName() + "_decorated_pot", decoratedPot);

            // Create a map to store potted variants for this color
            Map<Block, DeferredBlock<FlowerPotBlock>> pottedMap = COLORED_FLOWER_POTTED.get(color);

            // Flower Pots
            DeferredBlock<FlowerPotBlock> emptyPot = BLOCKS.register(
                    color.getName() + "_flower_pot",
                    () -> {
                        FlowerPotBlock pot = new FlowerPotBlock(
                                null,
                                () -> Blocks.AIR,
                                BlockBehaviour.Properties.of()
                                        .instabreak()
                                        .noOcclusion()
                                        .pushReaction(net.minecraft.world.level.material.PushReaction.DESTROY)
                        );

                        // Plant mappings
                        for (Block plant : plants) {
                            DeferredBlock<FlowerPotBlock> pottedBlock = pottedMap.get(plant);
                            if (pottedBlock != null) {
                                pot.addPlant(BuiltInRegistries.BLOCK.getKey(plant), pottedBlock);
                            }
                        }

                        return pot;
                    }
            );
            COLORED_FLOWER_POTS.put(color, emptyPot);
            registerBlockItem(color.getName() + "_flower_pot", emptyPot);

            // Flower Pot Potted Variants
            for (Block plant : plants) {
                String id = BuiltInRegistries.BLOCK.getKey(plant).getPath();
                DeferredBlock<FlowerPotBlock> potted = BLOCKS.register(
                        color.getName() + "_potted_" + id,
                        () -> new FlowerPotBlock(
                                emptyPot,
                                () -> plant,
                                BlockBehaviour.Properties.of()
                                        .instabreak()
                                        .noOcclusion()
                                        .pushReaction(net.minecraft.world.level.material.PushReaction.DESTROY)
                        )
                );
                pottedMap.put(plant, potted);
            }
        }
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);

        if (!name.contains("_potted_")) {
            registerBlockItem(name, toReturn);
        }

        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}