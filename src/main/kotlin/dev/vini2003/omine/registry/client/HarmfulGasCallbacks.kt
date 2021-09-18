package dev.vini2003.omine.registry.client

import dev.vini2003.omine.client.util.GasParticleUtils
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents

object HarmfulGasCallbacks {
    private fun recalculateRotatedVertices(context: WorldRenderContext) {
        GasParticleUtils.recalculateRotatedVertices(context)
    }

    fun initialize() {
        WorldRenderEvents.START.register(WorldRenderEvents.Start { recalculateRotatedVertices(it) })
    }
}