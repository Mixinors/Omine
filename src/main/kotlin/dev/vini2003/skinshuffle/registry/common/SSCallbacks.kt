package dev.vini2003.skinshuffle.registry.common

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents

object SSCallbacks {
	fun init() {
		ServerTickEvents.END_SERVER_TICK.register { server ->
			server.playerManager.playerList.forEach { player ->
				SSComponents.Skin.sync(player)
			}
		}
	}
}