package dev.vini2003.bodyshufflelite.client.model

import net.minecraft.client.model.ModelPart

data class IdentifiableModelPart(val id: String, val part: ModelPart) {
	companion object {
		val Comparator = object : Comparator<String> {
			override fun compare(o1: String, o2: String): Int {
				val o1 = o1.replace("left ", "")
				val o2 = o2.replace("right ", "")
				
				val o1StringPart = o1.replace("\\d".toRegex(), "")
				val o2StringPart = o2.replace("\\d".toRegex(), "")
				return if (o1StringPart.equals(o2StringPart, ignoreCase = true)) {
					extractInt(o1) - extractInt(o2)
				} else o1.compareTo(o2)
			}
			
			fun extractInt(s: String): Int {
				val num = s.replace("\\D".toRegex(), "")
				
				return if (num.isEmpty()) 0 else num.toInt()
			}
		}
	}
}