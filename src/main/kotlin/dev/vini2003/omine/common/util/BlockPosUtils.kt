package dev.vini2003.omine.common.util

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos

object BlockPosUtils {
    fun minimumSquaredDistance(players: List<PlayerEntity>, pos: BlockPos): Double {
        var minimumSquaredDistance = Double.MAX_VALUE
        for (player in players) {
            if (minimumSquaredDistance > squaredDistance(player, pos)) {
                minimumSquaredDistance = squaredDistance(player, pos)
            }
        }
        return minimumSquaredDistance
    }

    fun squaredDistance(player: PlayerEntity, pos: BlockPos): Double {
        return player.squaredDistanceTo(pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5)
    }
}