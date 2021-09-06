package dev.vini2003.skinshuffle.registry.common

import dev.onyxstudios.cca.api.v3.component.ComponentRegistry
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer
import dev.vini2003.skinshuffle.SS
import dev.vini2003.skinshuffle.common.component.SkinComponent

object SSComponents : EntityComponentInitializer {
	val Skin = ComponentRegistry.getOrCreate(SS.id("skin"), SkinComponent::class.java)
	
	override fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
		registry.registerForPlayers(Skin, ::SkinComponent)
	}
}