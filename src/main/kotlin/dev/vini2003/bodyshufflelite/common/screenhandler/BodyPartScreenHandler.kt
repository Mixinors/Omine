package dev.vini2003.bodyshufflelite.common.screenhandler

import dev.vini2003.blade.common.handler.BaseScreenHandler
import dev.vini2003.bodyshufflelite.registry.common.BSLScreenHandlers
import draylar.identity.registry.Components
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry
import net.fabricmc.fabric.impl.client.renderer.registry.EntityRendererRegistryImpl
import net.minecraft.client.render.entity.EntityRenderers
import net.minecraft.client.render.entity.SkeletonEntityRenderer
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType

class BodyPartScreenHandler(
	syncId: Int,
	inv: PlayerInventory
) : BaseScreenHandler(
	BSLScreenHandlers.BodyPartSelector,
	syncId,
	inv.player
) {
	override fun initialize(width: Int, height: Int) {
		val identity = Components.CURRENT_IDENTITY.get(player)
		

	}
	
	override fun canUse(player: PlayerEntity): Boolean {
		return player.isAlive
	}
}