package com.github.vini2003.blade.common.widget

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory

interface OriginalWidgetCollection : WidgetCollection {
    fun getInventory(inventoryNumber: Int): Inventory?

    fun addInventory(inventoryNumber: Int, inventory: Inventory)

    fun getPlayer(): PlayerEntity
}