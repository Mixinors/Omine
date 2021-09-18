package dev.vini2003.omine.common.util

import net.minecraft.block.BlockState
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

object GasUtils {
    fun isTraversableForPropagation(
        world: World?,
        centerState: BlockState,
        centerPos: BlockPos?,
        sideState: BlockState,
        sidePos: BlockPos?,
        direction: Direction
    ): Boolean {
        return if (world == null) false else sideState.fluidState.isEmpty &&
                !(Registry.BLOCK.getId(sideState.block).toString() == "astromine:airlock" && !sideState.get(
                    Properties.POWERED
                ))
                && (sideState.isAir || !sideState.isSideSolidFullSquare(world, sidePos, direction.opposite))
                && (centerState.isAir || !centerState.isSideSolidFullSquare(world, centerPos, direction)
                && !sideState.isOpaqueFullCube(world, centerPos))
    }
}