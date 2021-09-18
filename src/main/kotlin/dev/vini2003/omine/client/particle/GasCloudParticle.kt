package dev.vini2003.omine.client.particle

import dev.vini2003.omine.client.util.ClientUtils
import dev.vini2003.omine.client.util.GasParticleUtils.rotatedVertices
import dev.vini2003.omine.registry.client.HarmfulGasTextureSheets
import net.minecraft.client.particle.*
import net.minecraft.client.render.Camera
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.world.ClientWorld
import net.minecraft.particle.DefaultParticleType

class GasCloudParticle(clientWorld: ClientWorld?, x: Double, y: Double, z: Double) :
    SpriteBillboardParticle(clientWorld, x, y, z) {
    override fun buildGeometry(vertexConsumer: VertexConsumer, camera: Camera, tickDelta: Float) {
        if (colorAlpha < 0.25f) {
            colorAlpha += 0.0005f
        } else {
            colorAlpha = 0.25f
        }
        if (ClientUtils.player?.squaredDistanceTo(x, y, z)!! > 192.0 * 192.0) {
            return
        }
        val cX = (x - camera.pos.getX()).toFloat()
        val cY = (y - camera.pos.getY()).toFloat()
        val cZ = (z - camera.pos.getZ()).toFloat()
        vertexConsumer.vertex(
            (rotatedVertices[0].x + cX).toDouble(),
            (rotatedVertices[0].y + cY).toDouble(),
            (rotatedVertices[0].z + cZ).toDouble()
        ).texture(
            maxU, maxV
        ).color(colorRed, colorGreen, colorBlue, colorAlpha).light(15728880).next()
        vertexConsumer.vertex(
            (rotatedVertices[1].x + cX).toDouble(),
            (rotatedVertices[1].y + cY).toDouble(),
            (rotatedVertices[1].z + cZ).toDouble()
        ).texture(
            maxU, minV
        ).color(colorRed, colorGreen, colorBlue, colorAlpha).light(15728880).next()
        vertexConsumer.vertex(
            (rotatedVertices[2].x + cX).toDouble(),
            (rotatedVertices[2].y + cY).toDouble(),
            (rotatedVertices[2].z + cZ).toDouble()
        ).texture(
            minU, minV
        ).color(colorRed, colorGreen, colorBlue, colorAlpha).light(15728880).next()
        vertexConsumer.vertex(
            (rotatedVertices[3].x + cX).toDouble(),
            (rotatedVertices[3].y + cY).toDouble(),
            (rotatedVertices[3].z + cZ).toDouble()
        ).texture(
            minU, maxV
        ).color(colorRed, colorGreen, colorBlue, colorAlpha).light(15728880).next()
    }

    override fun getType(): ParticleTextureSheet {
        return HarmfulGasTextureSheets.GAS
    }

    class Factory(private val spriteProvider: SpriteProvider) : ParticleFactory<DefaultParticleType?> {
        override fun createParticle(
            parameters: DefaultParticleType?,
            world: ClientWorld,
            x: Double,
            y: Double,
            z: Double,
            velocityX: Double,
            velocityY: Double,
            velocityZ: Double
        ): Particle {
            val gasCloudParticle = GasCloudParticle(world, x, y, z)
            gasCloudParticle.sprite = spriteProvider.getSprite(gasCloudParticle.random)
            gasCloudParticle.maxAge = Int.MAX_VALUE
            gasCloudParticle.scale = 6f
            gasCloudParticle.colorAlpha = 0f
            return gasCloudParticle
        }
    }
}