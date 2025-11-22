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

    public static final Map<DyeColor, DeferredBlock<Block>> COLORED_FLOWER_POTS = new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, DeferredBlock<Block>> COLORED_DECORATED_POTS = new EnumMap<>(DyeColor.class);

    static {
        registerColoredPots();
    }

    private static void registerColoredPots() {
        for (DyeColor color : DyeColor.values()) {

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


            String decoratedPotName = color.getName() + "_decorated_pot";
            DeferredBlock<Block> decoratedPot = registerBlock(
                    decoratedPotName,
                    () -> new DecoratedPotBlock(
                            BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT)
                    )
            );
            COLORED_DECORATED_POTS.put(color, decoratedPot);
        }
    }

    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> blockSupplier) {
        DeferredBlock<T> block = BLOCKS.register(name, blockSupplier);
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}