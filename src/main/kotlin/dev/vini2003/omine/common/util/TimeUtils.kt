package dev.vini2003.omine.common.util

import net.minecraft.util.math.BlockPos
import net.minecraft.world.Heightmap
import net.minecraft.world.World

object TimeUtils {
    fun calculateMaximumHeight(world: World, pos: BlockPos?, age: Long, speed: Int): Int {
        return Math.min(
            world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos).y + 2,
            (96 + age / (1500.0f / (21 - speed))).toInt()
        )
    }

    fun calculateMinimumHeight(age: Long, speed: Int): Int {
        return (48 - age / (3000.0f / (21 - speed))).toInt()
    }

    fun calculateMinimumDistance(age: Long, speed: Int): Int {
        return (64 + age / (375.0f / (21 - speed))).toInt()
    }

    fun calculateFollowTime(age: Long): Int {
        return if (age < 600) 200 else 600
    }
}