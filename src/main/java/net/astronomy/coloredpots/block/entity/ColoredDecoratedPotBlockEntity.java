package net.astronomy.coloredpots.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ColoredDecoratedPotBlockEntity extends DecoratedPotBlockEntity {

    public ColoredDecoratedPotBlockEntity(BlockPos pos, BlockState state) {
        // CORRECTED: Pass only pos and state.
        // The vanilla class does not accept the Type in the constructor.
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        // This override is CRITICAL. It tells the game (and renderer)
        // that this is actually your custom entity, not the vanilla one.
        return ModBlockEntities.COLORED_DECORATED_POT.get();
    }
}