package dev.vini2003.omine.client.util

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext
import net.minecraft.util.math.Vec3f

object GasParticleUtils {
    lateinit var rotatedVertices: Array<Vec3f>
    var shouldClear = false
    fun recalculateRotatedVertices(context: WorldRenderContext) {
        val newRotatedVertices: Array<Vec3f> = arrayOf<Vec3f>(
            Vec3f(-6.0f, -6.0f, 0.0f),
            Vec3f(-6.0f, 6.0f, 0.0f),
            Vec3f(6.0f, 6.0f, 0.0f),
            Vec3f(6.0f, -6.0f, 0.0f)
        )
        for (i in 0..3) {
            val vertex: Vec3f = newRotatedVertices[i]
            vertex.rotate(context.camera().rotation)
        }
        rotatedVertices = newRotatedVertices
    }
}