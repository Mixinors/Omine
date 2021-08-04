package dev.vini2003.bodyshufflelite.common.component

import dev.onyxstudios.cca.api.v3.component.ComponentProvider
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent
import dev.vini2003.bodyshufflelite.registry.common.BSLComponents
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import java.util.HashMap
import java.util.function.Consumer

class BodyPartComponent(val entity: LivingEntity) : PlayerComponent<BodyPartComponent>, AutoSyncedComponent {
	companion object {
		@JvmStatic
		fun <V> has(provider: V) = provider is ComponentProvider && BSLComponents.BodyParts.isProvidedBy(provider)
		
		@JvmStatic
		fun <V> get(provider: V) = BSLComponents.BodyParts.get(provider)
		
		@JvmStatic
		fun <V> sync(provider: V) = BSLComponents.BodyParts.sync(provider)
	}
	
	private val parts: MutableMap<String, Boolean> = HashMap()
	
	operator fun set(part: String, state: Boolean) {
		parts[part] = state
	}
	
	operator fun get(part: String): Boolean {
		return parts[part]!!
	}
	
	fun has(part: String) = parts.containsKey(part)
	
	override fun writeToNbt(tag: NbtCompound) {
		parts.forEach(tag::putBoolean)
	}
	
	override fun readFromNbt(tag: NbtCompound) {
		tag.keys.forEach(Consumer { key: String ->
			set(key, tag.getBoolean(key))
		})
	}
}