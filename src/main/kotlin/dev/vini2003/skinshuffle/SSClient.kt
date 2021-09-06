package dev.vini2003.skinshuffle

import dev.vini2003.skinshuffle.registry.client.SSCommands
import dev.vini2003.skinshuffle.registry.client.SSNetworks
import net.fabricmc.api.ClientModInitializer

object SSClient : ClientModInitializer {
	override fun onInitializeClient() {
		SSCommands.init()
		SSNetworks.init()
	}
}