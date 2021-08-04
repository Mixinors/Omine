package dev.vini2003.bodyshufflelite.client.screen

import dev.vini2003.blade.client.handler.BaseScreen
import dev.vini2003.blade.common.miscellaneous.Position
import dev.vini2003.blade.common.miscellaneous.Size
import dev.vini2003.bodyshufflelite.client.model.IdentifiableModelPart
import dev.vini2003.bodyshufflelite.client.model.RootPartProvider
import dev.vini2003.bodyshufflelite.client.util.getParts
import dev.vini2003.bodyshufflelite.client.widget.BodyPartButtonWidget
import draylar.identity.registry.Components
import net.minecraft.client.MinecraftClient
import net.minecraft.client.model.ModelPart
import net.minecraft.client.render.entity.LivingEntityRenderer
import net.minecraft.client.render.entity.model.CowEntityModel
import net.minecraft.client.render.entity.model.SinglePartEntityModel
import net.minecraft.text.LiteralText
import net.minecraft.text.Text

class BodyPartScreen() : BaseScreen(LiteralText.EMPTY) {
	override fun initialize(width: Int, height: Int) {
		val client = MinecraftClient.getInstance()
		val dispatcher = client.entityRenderDispatcher
		val player = client.player
		
		val currentIdentity = Components.CURRENT_IDENTITY.get(player)
		
		if (currentIdentity.identity == null) {
			onClose()
		}
		
		val renderer = dispatcher.getRenderer(currentIdentity.identity ?: player)
		
		if (renderer is LivingEntityRenderer<*, *>) {
			val modelParts = when (val model = renderer.model) {
				is SinglePartEntityModel<*> -> getParts(model.part)
				is RootPartProvider -> getParts(model.bsl_root)
				else -> emptyList()
			}
			
			val parts = mutableMapOf<String, MutableList<IdentifiableModelPart>>()
			
			var dX = 0
			var dY = 0
			
			modelParts.forEach { part ->
				val prefix = part.id.replace('_', ' ').takeWhile { it.isLetter() || it == ' ' }
				
				parts.computeIfAbsent(prefix) { mutableListOf() }.add(part)
			}
			
			parts.keys.sortedWith(IdentifiableModelPart.Comparator).forEachIndexed { index, name ->
				val parts = parts[name]!!
				
				val x = 18 + dX
				val y = 18 + dY
				
				val part = parts.first()
				
				val label = Text.of(
					if (part.id.any(Char::isDigit)) {
						"${name.capitalize()}s"
					} else {
						name.capitalize()
					}
				)
				
				val button = BodyPartButtonWidget()
				
				button.size = Size.of(72, 16)
				button.position = Position.of(x, y)
				button.label = label
				button.parts = parts
				button.enabled = parts.first().part.visible
				
				addWidget(button)
				
				dX += 72 + 2
				
				if ((index + 1) % 3 == 0) {
					dX = 0
					dY += 24
				}
			}
		}
	}
}