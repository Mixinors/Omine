package dev.vini2003.omine.registry.common

import dev.vini2003.omine.common.component.WorldGasComponent
import me.shedaniel.cloth.api.common.events.v1.PlayerLeaveCallback
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents.AfterPlayerChange
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents.AfterRespawn
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World
import dev.vini2003.omine.registry.client.HarmfulGasNetworking.sendRefreshGasCloudPacket

object HarmfulGasCallbacks {
    private fun playerChangeWorld(player: PlayerEntity, origin: World, destination: World) {
        val gasComponent = WorldGasComponent[origin]
        gasComponent!!.getParticles().remove(player.uuid)
        gasComponent.getCooldowns().put(player.uuid, 150)
        sendRefreshGasCloudPacket(player)
    }

    private fun playerRespawn(oldPlayer: PlayerEntity, newPlayer: PlayerEntity, alive: Boolean) {
        val gasComponent = WorldGasComponent[newPlayer.entityWorld]
        gasComponent!!.getParticles().remove(newPlayer.uuid)
        gasComponent.getCooldowns().put(newPlayer.uuid, 150)
        sendRefreshGasCloudPacket(newPlayer)
    }

    private fun playerLeave(player: PlayerEntity) {
        val gasComponent = WorldGasComponent[player.entityWorld]
        gasComponent!!.getParticles().remove(player.uuid)
    }

    fun initialize() {
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(AfterPlayerChange { player: ServerPlayerEntity, origin: ServerWorld, destination: ServerWorld ->
            playerChangeWorld(
                player,
                origin,
                destination
            )
        })
        ServerPlayerEvents.AFTER_RESPAWN.register(AfterRespawn { oldPlayer: ServerPlayerEntity, newPlayer: ServerPlayerEntity, alive: Boolean ->
            playerRespawn(
                oldPlayer,
                newPlayer,
                alive
            )
        })
        PlayerLeaveCallback.EVENT.register { player: PlayerEntity ->
            playerLeave(
                player
            )
        }
    }
}