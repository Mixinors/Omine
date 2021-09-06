package dev.vini2003.skinshuffle

import dev.vini2003.skinshuffle.registry.client.SSCommands
import net.fabricmc.api.ClientModInitializer

object SSClient : ClientModInitializer {
	override fun onInitializeClient() {
		SSCommands.init()
	}
}