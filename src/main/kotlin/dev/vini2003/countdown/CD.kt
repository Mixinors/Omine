package dev.vini2003.countdown


import dev.vini2003.countdown.registry.common.CDCallbacks
import dev.vini2003.countdown.registry.common.CDCommands
import net.fabricmc.api.ModInitializer

class CD : ModInitializer {
	companion object {
		@SuppressWarnings
		const val Id = "notubbo"
	}

	override fun onInitialize() {
		CDCallbacks.init()
		CDCommands.init()
	}
}