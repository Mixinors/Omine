package dev.vini2003.bodyshufflelite.registry.common

import dev.vini2003.bodyshufflelite.BSL
import dev.vini2003.bodyshufflelite.registry.common.BSLScreenHandlers.Factory
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking

object BSLNetworks {
	val TogglePart = BSL.id("toggle_part")
	val PartSelector = BSL.id("part_selector")
	
	fun init() {
		ServerPlayNetworking.registerGlobalReceiver(PartSelector) { _, player, _, _, _ ->
			player.openHandledScreen(BSLScreenHandlers.BodyPartSelector.Factory)
		}
	}
}