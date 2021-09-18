package dev.vini2003.omine.registry.client

import dev.vini2003.omine.client.particle.GasCloudParticle
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry

object HarmfulGasParticleFactories {
    fun initialize() {
        ParticleFactoryRegistry.getInstance().register(
            HarmfulGasParticleTypes.GAS
        ) { spriteProvider: FabricSpriteProvider? ->
            GasCloudParticle.Factory(
                spriteProvider!!
            )
        }
    }
}