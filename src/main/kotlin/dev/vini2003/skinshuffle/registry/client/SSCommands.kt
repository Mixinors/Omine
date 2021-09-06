package dev.vini2003.skinshuffle.registry.client

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.word
import dev.vini2003.skinshuffle.common.util.extension.loadSkin
import dev.vini2003.skinshuffle.registry.common.SSNetworks.SkinUpdate
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.*
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs

object SSCommands {
	fun init() {
		DISPATCHER.register(
			literal("skin").then(
				argument("target", word()).executes { context ->
					val target = getString(context, "target")
					
					val buf = PacketByteBufs.create()
					buf.writeString(target)
						
					ClientPlayNetworking.send(SkinUpdate, buf)
					
					return@executes Command.SINGLE_SUCCESS
				}
			)
		)
	}
}