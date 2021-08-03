package dev.vini2003.bodyshufflelite.registry.common

import dev.vini2003.blade.common.handler.BaseScreenHandler
import dev.vini2003.bodyshufflelite.BSL
import dev.vini2003.bodyshufflelite.common.screenhandler.BodyPartScreenHandler
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralText

object BSLScreenHandlers {
	val BodyPartSelector = register("part_selector", ::BodyPartScreenHandler)
	
	fun init() {
	}
	
	private fun <S : BaseScreenHandler> register(path: String, factory: (syncId: Int, inv: PlayerInventory) -> S): ScreenHandlerType<S> {
		return register(path) { syncId, inv, _ -> factory.invoke(syncId, inv) }
	}
	
	private fun <S : BaseScreenHandler> register(path: String, factory: (syncId: Int, inv: PlayerInventory, buf: PacketByteBuf) -> S): ScreenHandlerType<S> {
		return ScreenHandlerRegistry.registerExtended(BSL.id(path), factory)
	}
	
	private fun <S : BaseScreenHandler> createFactory(factory: (syncId: Int, inv: PlayerInventory) -> S): ExtendedScreenHandlerFactory {
		return object : ExtendedScreenHandlerFactory {
			override fun writeScreenOpeningData(player: ServerPlayerEntity, buf: PacketByteBuf) = Unit
			
			override fun getDisplayName() = LiteralText.EMPTY
			
			override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = factory.invoke(syncId, inv)
		}
	}
	
	val ScreenHandlerType<out BaseScreenHandler>.Factory
		get() = createFactory(this::create)
}