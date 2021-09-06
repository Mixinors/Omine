package dev.vini2003.skinshuffle

import dev.vini2003.skinshuffle.registry.common.SSCallbacks
import dev.vini2003.skinshuffle.registry.common.SSNetworks
import net.fabricmc.api.ModInitializer
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Identifier

val minecraftClient
	get() = MinecraftClient.getInstance()

object SS : ModInitializer {
	const val Id = "skinshuffle"
		
	fun id(path: String) = Identifier(Id, path)

	override fun onInitialize() {
		SSNetworks.init()
		SSCallbacks.init()
	}
}