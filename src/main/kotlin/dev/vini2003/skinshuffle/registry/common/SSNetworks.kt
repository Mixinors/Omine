package dev.vini2003.skinshuffle.registry.common

import dev.vini2003.skinshuffle.SS
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking

object SSNetworks {
	val TriggerSkinUpdate = SS.id("trigger_skin_update")
	val SkinUpdate = SS.id("skin_update")
	
	fun init() {
		ServerPlayNetworking.registerGlobalReceiver(TriggerSkinUpdate) { server, player, _, buf, _ ->
			val uuid = player.uuid
			val target = buf.readString()
			
			server.playerManager.playerList.forEach {
				val buf = PacketByteBufs.create()
				buf.writeUuid(uuid)
				buf.writeString(target)
				
				ServerPlayNetworking.send(it, SkinUpdate, buf)
				
				SSComponents.Skin.get(player).target = target
				SSComponents.Skin.sync(player)
			}
		}
	}
}