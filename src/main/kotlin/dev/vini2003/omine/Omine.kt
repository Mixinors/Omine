package dev.vini2003.omine

import dev.vini2003.omine.registry.common.HarmfulGasCallbacks
import dev.vini2003.omine.registry.common.HarmfulGasCommands
import dev.vini2003.omine.registry.common.HarmfulGasComponents
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

object Omine : ModInitializer {
	const val Id = "omine"
	
	fun identifier(path: String) = Identifier(Id, path)
	
	override fun onInitialize() {
		HarmfulGasCommands.initialize();
		HarmfulGasComponents.initialize();
		HarmfulGasCallbacks.initialize();
	}
}