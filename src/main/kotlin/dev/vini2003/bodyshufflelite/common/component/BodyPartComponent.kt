package dev.vini2003.bodyshufflelite.common.component

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import java.util.HashMap
import java.util.function.Consumer

class BodyPartComponent(val player: PlayerEntity) : PlayerComponent<BodyPartComponent>, AutoSyncedComponent {
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