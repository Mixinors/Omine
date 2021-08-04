package dev.vini2003.bodyshufflelite.client.util

import dev.vini2003.bodyshufflelite.client.model.IdentifiableModelPart
import dev.vini2003.bodyshufflelite.client.model.RootPartProvider
import net.minecraft.client.model.ModelPart
import net.minecraft.client.render.entity.model.AnimalModel

fun getParts(model: AnimalModel<*>): List<IdentifiableModelPart> {
	return if (model is RootPartProvider) {
		getParts(model.bsl_root)
	} else {
		emptyList()
	}
}

fun getParts(root: ModelPart): List<IdentifiableModelPart> {
	return getParts(root, mutableListOf())
}

fun getParts(root: ModelPart, list: MutableList<IdentifiableModelPart>): MutableList<IdentifiableModelPart> {
	list.addAll(root.children.entries.map { IdentifiableModelPart(it.key, it.value) })
	
	getParts(root.children.entries, list)
	
	return list
}

fun getParts(parts: Collection<Map.Entry<String, ModelPart>>, list: MutableList<IdentifiableModelPart>): MutableList<IdentifiableModelPart> {
	parts.forEach { (_, part) -> getParts(part, list) }
	
	return list
}