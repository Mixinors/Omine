package dev.vini2003.bodyshufflelite.client.widget

import dev.vini2003.blade.client.utilities.Drawings
import dev.vini2003.blade.client.utilities.Layers
import dev.vini2003.blade.client.utilities.Texts
import dev.vini2003.blade.common.miscellaneous.Color
import dev.vini2003.blade.common.widget.base.ButtonWidget
import dev.vini2003.bodyshufflelite.client.model.IdentifiableModelPart
import dev.vini2003.bodyshufflelite.common.component.BodyPartComponent
import dev.vini2003.bodyshufflelite.registry.common.BSLComponents
import dev.vini2003.bodyshufflelite.registry.common.BSLNetworks
import draylar.identity.registry.Components
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.network.PacketByteBuf

class BodyPartButtonWidget : ButtonWidget() {
	var part: IdentifiableModelPart? = null
		set(value) {
			if (value != null) {
				parts.clear()
				parts.add(value)
				
				field = value
			}
		}
	
	var parts: MutableList<IdentifiableModelPart> = mutableListOf()
	
	var enabled = true
	
	init {
		clickAction = {
			if (extended!!.client) {
				enabled = !enabled
				
				parts.forEach { part ->
					val buf = PacketByteBuf(Unpooled.buffer())
					
					buf.writeString(part.id)
					buf.writeBoolean(enabled)
					
					ClientPlayNetworking.send(BSLNetworks.TogglePart, buf)
				}
			}
		}
	}
	
	override fun drawWidget(matrices: MatrixStack, provider: VertexConsumerProvider) {
		if (hidden) {
			return
		}
		
		if (provider is VertexConsumerProvider.Immediate) provider.draw()
		
		if (focused) {
			Drawings.drawQuad(matrices, provider, Layers.Interface, x - 1, y + height, width + 2, 1.0F, Color.of(0xFFFFFFFF))
			Drawings.drawQuad(matrices, provider, Layers.Interface, x - 1, y - 1, width + 2, 1.0F, Color.of(0xFFFFFFFF))
			
			Drawings.drawQuad(matrices, provider, Layers.Interface, x - 1, y, 1.0F, height, Color.of(0xFFFFFFFF))
			Drawings.drawQuad(matrices, provider, Layers.Interface, x + width, y, 1.0F, height, Color.of(0xFFFFFFFF))
		}
		
		Drawings.drawQuad(matrices, provider, Layers.Interface, x, y, width, height, Color.of(0x0000007E))
		
		
		label?.also {
			val color = if (enabled) 0x55FF55 else 0xFF5555
			
			Drawings.getTextRenderer()?.drawWithShadow(matrices, label, position.x + (size.width / 2 - Texts.width(label!!) / 2), position.y + (size.height / 2 - Texts.height() / 2), color)
		}
	}
}