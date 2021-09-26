package dev.vini2003.omine.registry.client

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.particle.ParticleTextureSheet
import net.minecraft.client.render.BufferBuilder
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.texture.TextureManager

object HarmfulGasTextureSheets {
    val GAS: ParticleTextureSheet = object : ParticleTextureSheet {
        override fun begin(bufferBuilder: BufferBuilder, textureManager: TextureManager) {
            textureManager.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE)
            RenderSystem.enableBlend()
            RenderSystem.depthMask(false)
            RenderSystem.blendFuncSeparate(
                GlStateManager.SrcFactor.SRC_ALPHA,
                GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SrcFactor.ONE,
                GlStateManager.DstFactor.ZERO
            )
            //RenderSystem.alphaFunc(516, 0.003921569f)
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT)
        }

        override fun draw(tessellator: Tessellator) {
            tessellator.draw()
        }
    }

    fun initialize() {}
}