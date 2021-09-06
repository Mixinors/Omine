package dev.vini2003.skinshuffle.registry.client

import dev.vini2003.skinshuffle.common.util.extension.loadSkin
import dev.vini2003.skinshuffle.registry.common.SSNetworks.SkinUpdate
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.network.AbstractClientPlayerEntity
import net.minecraft.text.Text

object SSNetworks {
	fun init() {
		ClientPlayNetworking.registerGlobalReceiver(SkinUpdate) { client, _, buf, _ ->
			val uuid = buf.readUuid()
			val target = buf.readString()
			
			val player = (client.world?.getPlayerByUuid(uuid)) as AbstractClientPlayerEntity
			
			player.loadSkin(target)
		}
	}
}