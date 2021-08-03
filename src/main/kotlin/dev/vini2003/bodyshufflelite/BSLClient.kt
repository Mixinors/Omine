package dev.vini2003.bodyshufflelite

import dev.vini2003.bodyshufflelite.registry.client.BSLKeybinds
import dev.vini2003.bodyshufflelite.registry.client.BSLNetworks
import net.fabricmc.api.ClientModInitializer

class BSLClient : ClientModInitializer {
	override fun onInitializeClient() {
		BSLKeybinds.init()
		BSLNetworks.init()
	}
}