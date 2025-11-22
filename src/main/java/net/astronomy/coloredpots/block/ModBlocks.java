package net.astronomy.coloredpots.block;

import net.astronomy.coloredpots.ColoredPots;
import net.astronomy.coloredpots.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.item.DyeColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(ColoredPots.MOD_ID);

    public static final Map<DyeColor, DeferredBlock<Block>> COLORED_DECORATED_POTS = new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, DeferredBlock<Block>> COLORED_FLOWER_POTS = new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, Map<String, DeferredBlock<Block>>> COLORED_FLOWER_POTTED = new EnumMap<>(DyeColor.class);

    static {
        registerColoredPots();
    }

    private static void registerColoredPots() {
        Object[][] plants = new Object[][] {
                {Blocks.OAK_SAPLING, "oak_sapling"},
                {Blocks.SPRUCE_SAPLING, "spruce_sapling"},
                {Blocks.BIRCH_SAPLING, "birch_sapling"},
                {Blocks.JUNGLE_SAPLING, "jungle_sapling"},
                {Blocks.ACACIA_SAPLING, "acacia_sapling"},
                {Blocks.CHERRY_SAPLING, "cherry_sapling"},
                {Blocks.DARK_OAK_SAPLING, "dark_oak_sapling"},
                {Blocks.MANGROVE_PROPAGULE, "mangrove_propagule"},
                {Blocks.FERN, "fern"},
                {Blocks.DANDELION, "dandelion"},
                {Blocks.POPPY, "poppy"},
                {Blocks.BLUE_ORCHID, "blue_orchid"},
                {Blocks.ALLIUM, "allium"},
                {Blocks.AZURE_BLUET, "azure_bluet"},
                {Blocks.RED_TULIP, "red_tulip"},
                {Blocks.ORANGE_TULIP, "orange_tulip"},
                {Blocks.WHITE_TULIP, "white_tulip"},
                {Blocks.PINK_TULIP, "pink_tulip"},
                {Blocks.OXEYE_DAISY, "oxeye_daisy"},
                {Blocks.CORNFLOWER, "cornflower"},
                {Blocks.LILY_OF_THE_VALLEY, "lily_of_the_valley"},
                {Blocks.WITHER_ROSE, "wither_rose"},
                {Blocks.RED_MUSHROOM, "red_mushroom"},
                {Blocks.BROWN_MUSHROOM, "brown_mushroom"},
                {Blocks.DEAD_BUSH, "dead_bush"},
                {Blocks.CACTUS, "cactus"}
        };

        for (DyeColor color : DyeColor.values()) {
            // --- Register empty flower pot ---
            String flowerPotName = color.getName() + "_flower_pot";
            DeferredBlock<Block> flowerPot = registerBlock(
                    flowerPotName,
                    () -> new FlowerPotBlock(
                            null,
                            () -> Blocks.AIR,
                            BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)
                    )
            );
            COLORED_FLOWER_POTS.put(color, flowerPot);

            // --- Register decorated pot ---
            String decoratedPotName = color.getName() + "_decorated_pot";
            DeferredBlock<Block> decoratedPot = registerBlock(
                    decoratedPotName,
                    () -> new DecoratedPotBlock(
                            BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT)
                    )
            );
            COLORED_DECORATED_POTS.put(color, decoratedPot);

            // --- Register all potted variants for this color ---
            Map<String, DeferredBlock<Block>> pottedMap = new java.util.HashMap<>();
            for (Object[] plantEntry : plants) {
                Block plant = (Block) plantEntry[0];
                String plantName = (String) plantEntry[1];

                String pottedName = color.getName() + "_potted_" + plantName;

                DeferredBlock<Block> pottedBlock = registerPottedBlock(
                        pottedName,
                        () -> new FlowerPotBlock(
                                null,
                                () -> plant,
                                BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)
                        )
                );

                pottedMap.put(plantName, pottedBlock);
            }
            COLORED_FLOWER_POTTED.put(color, pottedMap);
        }
    }

    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> blockSupplier) {
        DeferredBlock<T> block = BLOCKS.register(name, blockSupplier);
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static <T extends Block> DeferredBlock<T> registerPottedBlock(String name, Supplier<T> blockSupplier) {
        DeferredBlock<T> block = BLOCKS.register(name, blockSupplier);
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}