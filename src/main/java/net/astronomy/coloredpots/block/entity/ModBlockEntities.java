package net.astronomy.coloredpots.block.entity;

import net.astronomy.coloredpots.ColoredPots;
import net.astronomy.coloredpots.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ColoredPots.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ColoredDecoratedPotBlockEntity>> COLORED_DECORATED_POT =
            BLOCK_ENTITIES.register("colored_decorated_pot", () ->
                    BlockEntityType.Builder.of(
                            ColoredDecoratedPotBlockEntity::new,
                            ModBlocks.COLORED_DECORATED_POTS.values().stream()
                                    .map(DeferredHolder::get)
                                    .toArray(Block[]::new)
                    ).build(null)
            );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}