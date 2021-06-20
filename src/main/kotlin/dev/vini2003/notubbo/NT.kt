package dev.vini2003.notubbo


import dev.vini2003.notubbo.registry.common.NTCallbacks
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

class NT : ModInitializer {
	companion object {
		@SuppressWarnings
		const val Id = "notubbo"
	}

	override fun onInitialize() {
		NTCallbacks.init()
	}
}