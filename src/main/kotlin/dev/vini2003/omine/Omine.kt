package dev.vini2003.omine

import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

object Omine : ModInitializer {
	const val Id = "template"
	
	fun id(path: String) = Identifier(Id, path)
	
	override fun onInitialize() {
	}
}