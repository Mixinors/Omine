package dev.vini2003.bodyshufflelite.registry.common

import dev.onyxstudios.cca.api.v3.component.ComponentRegistry
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer
import dev.vini2003.bodyshufflelite.BSL
import dev.vini2003.bodyshufflelite.common.component.BodyPartComponent

object BSLComponents : EntityComponentInitializer {
	val BodyParts = ComponentRegistry.getOrCreate(BSL.id("body_parts"), BodyPartComponent::class.java)
	
	fun init() {
	}
	
	override fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
		registry.registerForPlayers(BodyParts, ::BodyPartComponent)
	}
}