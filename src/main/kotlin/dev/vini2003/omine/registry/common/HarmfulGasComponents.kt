package dev.vini2003.omine.registry.common

import dev.onyxstudios.cca.api.v3.component.ComponentRegistry
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer
import dev.vini2003.omine.Omine
import dev.vini2003.omine.common.component.WorldGasComponent
import net.minecraft.world.World

class HarmfulGasComponents : WorldComponentInitializer {
    override fun registerWorldComponentFactories(worldComponentFactoryRegistry: WorldComponentFactoryRegistry) {
        worldComponentFactoryRegistry.register(
            WORLD_GAS_COMPONENT
        ) { world: World? ->
            WorldGasComponent(
                world
            )
        }
    }

    companion object {
        val WORLD_GAS_COMPONENT = ComponentRegistry.getOrCreate(
            Omine.identifier("world_gas_component"),
            WorldGasComponent::class.java
        )

        fun initialize() {}
    }
}