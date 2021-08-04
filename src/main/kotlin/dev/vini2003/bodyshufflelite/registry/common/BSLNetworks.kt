package dev.vini2003.bodyshufflelite.registry.common

import dev.vini2003.bodyshufflelite.BSL
import dev.vini2003.bodyshufflelite.common.component.BodyPartComponent
import draylar.identity.registry.Components
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking

object BSLNetworks {
	val TogglePart = BSL.id("toggle_part")
	
	fun init() {
		ServerPlayNetworking.registerGlobalReceiver(TogglePart) { _, player, _, buf, _ ->
			val id = buf.readString()
			val state = buf.readBoolean()
			
			val identity = Components.CURRENT_IDENTITY.get(player)
			
			if (identity.identity != null) {
				val comp = BodyPartComponent.get(identity.identity)
				
				comp[id] = state
				
				BodyPartComponent.sync(identity.identity)
			}
		}
	}
}