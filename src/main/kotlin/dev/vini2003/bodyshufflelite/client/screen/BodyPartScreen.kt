package dev.vini2003.bodyshufflelite.client.screen

import dev.vini2003.blade.client.handler.BaseScreen
import draylar.identity.registry.Components
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.entity.LivingEntityRenderer
import net.minecraft.text.Text

class BodyPartScreen(title: Text) : BaseScreen(title) {
	override fun initialize(width: Int, height: Int) {
		val client = MinecraftClient.getInstance()
		val dispatcher = client.entityRenderDispatcher
		val player = client.player
		
		val currentIdentity = Components.CURRENT_IDENTITY.get(player)
		
		val renderer = dispatcher.getRenderer(currentIdentity.identity)
		
		if (renderer is LivingEntityRenderer<*, *>) {
			val model = renderer.getModel()
		}
	}
}