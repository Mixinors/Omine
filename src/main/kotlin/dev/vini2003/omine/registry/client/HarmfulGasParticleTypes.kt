package dev.vini2003.omine.registry.client

import dev.vini2003.omine.Omine
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes
import net.minecraft.particle.DefaultParticleType
import net.minecraft.util.registry.Registry

object HarmfulGasParticleTypes {
    val GAS: DefaultParticleType =
        Registry.register(Registry.PARTICLE_TYPE, Omine.identifier("gas"), FabricParticleTypes.simple(true))

    fun initialize() {}
}