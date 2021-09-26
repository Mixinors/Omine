package dev.vini2003.omine.registry.common

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import dev.vini2003.omine.common.component.WorldGasComponent
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.TranslatableText
import net.minecraft.world.World
import dev.vini2003.omine.registry.client.HarmfulGasNetworking.sendRefreshGasCloudPacket

object HarmfulGasCommands {
    @Throws(CommandSyntaxException::class)
    fun start(context: CommandContext<ServerCommandSource>): Int {
        val world: World = context.source.world
        val player: PlayerEntity = context.source.player
        val gasComponent = WorldGasComponent[world]
        gasComponent!!.add((world as ServerWorld).spawnPos)
        gasComponent.originPos = world.spawnPos
        gasComponent.getCooldowns()[player.uuid] = 300
        context.source.sendFeedback(TranslatableText("text.omine.start"), true)
        return 1
    }

    @Throws(CommandSyntaxException::class)
    fun pause(context: CommandContext<ServerCommandSource>): Int {
        val world: World = context.source.world
        val gasComponent = WorldGasComponent[world]
        gasComponent!!.isPaused = true
        context.source.sendFeedback(TranslatableText("text.omine.paused"), true)
        return 1
    }

    @Throws(CommandSyntaxException::class)
    fun resume(context: CommandContext<ServerCommandSource>): Int {
        val world: World = context.source.world
        val gasComponent = WorldGasComponent[world]
        gasComponent!!.isPaused = false
        context.source.sendFeedback(TranslatableText("text.omine.resumed"), true)
        return 1
    }

    @Throws(CommandSyntaxException::class)
    fun speed(context: CommandContext<ServerCommandSource>): Int {
        val world: World = context.source.world
        val gasComponent = WorldGasComponent[world]
        val speed = IntegerArgumentType.getInteger(context, "speed")
        context.source.sendFeedback(TranslatableText("text.omine.speed", gasComponent!!.speed, speed), true)
        gasComponent.speed = speed
        return 1
    }

    @Throws(CommandSyntaxException::class)
    fun refresh(context: CommandContext<ServerCommandSource>): Int {
        val world: World = context.source.world
        val player: PlayerEntity = context.source.player
        val gasComponent = WorldGasComponent[world]
        sendRefreshGasCloudPacket(player)
        gasComponent!!.getParticles().remove(player.uuid)
        gasComponent.getCooldowns()[player.uuid] = 150
        context.source.sendFeedback(TranslatableText("text.omine.refresh"), true)
        return 1
    }

    fun initialize() {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource>, dedicated: Boolean ->
            val harmfulGasRoot = CommandManager.literal("omine").build()
            val harmfulGasPlace = CommandManager.literal("start")
                .requires { source: ServerCommandSource ->
                    source.hasPermissionLevel(
                        2
                    )
                }
                .executes { context: CommandContext<ServerCommandSource> -> start(context) }
                .build()
            val harmfulGasPause = CommandManager.literal("pause")
                .requires { source: ServerCommandSource ->
                    source.hasPermissionLevel(
                        2
                    )
                }
                .executes { context: CommandContext<ServerCommandSource> -> pause(context) }
                .build()
            val harmfulGasResume = CommandManager.literal("resume")
                .requires { source: ServerCommandSource ->
                    source.hasPermissionLevel(
                        2
                    )
                }
                .executes { context: CommandContext<ServerCommandSource> -> resume(context) }
                .build()
            val harmfulGasSpeed = CommandManager.literal("speed")
                .requires { source: ServerCommandSource ->
                    source.hasPermissionLevel(
                        2
                    )
                }
                .then(
                    CommandManager.argument("speed", IntegerArgumentType.integer(1, 100))
                        .executes { context: CommandContext<ServerCommandSource> -> speed(context) }
                        .build()
                )
                .executes { context: CommandContext<ServerCommandSource> -> pause(context) }
                .build()
            val harmfulGasRefresh = CommandManager.literal("refresh")
                .requires { source: ServerCommandSource ->
                    source.hasPermissionLevel(
                        2
                    )
                }
                .executes { context: CommandContext<ServerCommandSource> -> refresh(context) }
                .build()
            harmfulGasRoot.addChild(harmfulGasPlace)
            harmfulGasRoot.addChild(harmfulGasPause)
            harmfulGasRoot.addChild(harmfulGasResume)
            harmfulGasRoot.addChild(harmfulGasSpeed)
            harmfulGasRoot.addChild(harmfulGasRefresh)
            dispatcher.root.addChild(harmfulGasRoot)
        })
    }
}