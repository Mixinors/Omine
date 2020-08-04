package com.github.vini2003.blade.common.widget

import com.github.vini2003.blade.common.widget.base.AbstractWidget
import com.google.common.collect.Lists
import net.minecraft.inventory.Inventory

interface WidgetCollection {
    val widgets: ArrayList<AbstractWidget>

    fun addWidget(widget: AbstractWidget) {
        widgets.add(widget)
        if (this is AbstractWidget) this.onLayoutChanged()
    }

    fun removeWidget(widget: AbstractWidget) {
        widgets.remove(widget)
        if (this is AbstractWidget) this.onLayoutChanged()
    }
}