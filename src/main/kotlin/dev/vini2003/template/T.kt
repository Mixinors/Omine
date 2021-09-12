package dev.vini2003.template

import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

object T : ModInitializer {
	const val Id = "template"
	
	fun id(path: String) = Identifier(Id, path)
	
	override fun onInitialize() {
	}
}