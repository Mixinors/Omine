package dev.vini2003.bodyshufflelite

import dev.vini2003.bodyshufflelite.registry.common.BSLComponents
import dev.vini2003.bodyshufflelite.registry.common.BSLNetworks
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

object BSL : ModInitializer {
	const val Id = "bodyshufflelite"
		
	fun id(path: String) = Identifier(Id, path)

	override fun onInitialize() {
		BSLComponents.init()
		BSLNetworks.init()
	}
}