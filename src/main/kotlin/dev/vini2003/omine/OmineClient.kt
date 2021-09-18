package dev.vini2003.omine

import dev.vini2003.omine.registry.client.*
import net.fabricmc.api.ClientModInitializer

object OmineClient : ClientModInitializer {
	override fun onInitializeClient() {
		HarmfulGasCallbacks.initialize();
		HarmfulGasParticleTypes.initialize();
		HarmfulGasParticleFactories.initialize();
		HarmfulGasNetworking.initialize();
		HarmfulGasTextureSheets.initialize();
	}
}