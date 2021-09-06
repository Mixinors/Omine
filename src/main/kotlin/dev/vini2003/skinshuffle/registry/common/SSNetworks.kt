package dev.vini2003.skinshuffle.registry.common

import dev.vini2003.skinshuffle.SS
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking

object SSNetworks {
	val SkinUpdate = SS.id("trigger_skin_update")
	
	fun init() {
		ServerPlayNetworking.registerGlobalReceiver(SkinUpdate) { server, player, _, buf, _ ->
			val target = buf.readString()
			
			server.playerManager.playerList.forEach { _ ->
				SSComponents.Skin.get(player).target = target
				SSComponents.Skin.sync(player)
			}
		}
	}
}