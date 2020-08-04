package com.github.vini2003.blade.common.widget.base

import com.github.vini2003.blade.Blade
import com.github.vini2003.blade.client.data.PartitionedTexture
import com.github.vini2003.blade.client.utilities.Drawings
import com.github.vini2003.blade.client.utilities.Layers
import com.github.vini2003.blade.common.data.Color
import com.github.vini2003.blade.common.data.Position
import com.github.vini2003.blade.common.data.Size
import com.github.vini2003.blade.common.data.geometry.Rectangle
import com.github.vini2003.blade.common.utilities.Networks
import com.github.vini2003.blade.common.utilities.Positions
import com.github.vini2003.blade.common.widget.OriginalWidgetCollection
import com.github.vini2003.blade.common.widget.WidgetCollection
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.inventory.Inventory
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

class SlotListWidget(
        private val inventory: Inventory
) : AbstractWidget(), WidgetCollection {
    var textureScrollbar = PartitionedTexture(Blade.identifier("textures/widget/scrollbar.png"), 18F, 18F, 0.11111111111111111111F, 0.11111111111111111111F, 0.11111111111111111111F, 0.16666666666666666667F)
    var textureScroller = PartitionedTexture(Blade.identifier("textures/widget/scroller.png"), 18F, 18F, 0.11111111111111111111F, 0.11111111111111111111F, 0.11111111111111111111F, 0.11111111111111111111F)
    var textureScrollerFocus = PartitionedTexture(Blade.identifier("textures/widget/scroller_focus.png"), 18F, 18F, 0.11111111111111111111F, 0.11111111111111111111F, 0.11111111111111111111F, 0.11111111111111111111F)

    override val widgets: ArrayList<AbstractWidget> = ArrayList()

    private var row = 0

    private var scrollerHeld = false

    private var widthInSlots: Int = 0
    private var heightInSlots: Int = 0
    private var maximumSlots: Int = 0

    private var updateScrollerRectangle: Boolean = false
    private var updateScrollbarRectangle: Boolean = false

    private var scrollerRectangleCached: Rectangle = Rectangle.empty()
    private var scrollbarRectangleCached: Rectangle = Rectangle.empty()

    private val bottomRow: Int
        get() = maximumSlots - heightInSlots

    private val totalRows: Int
        get() = inventory.size() / widthInSlots

    private val scrollerHeight: Float
        get() = min(size.height - 2, heightInSlots.toFloat() / totalRows.toFloat() * (size.height - 2))

    private val scrollerY: Float
        get() = max(position.y + 2, min(position.y + size.height - scrollerHeight, row.toFloat() / totalRows.toFloat() * size.height + position.y + 1))

    private val scrollerRectangle: Rectangle
        get() {
            return if (updateScrollerRectangle) {
                scrollerRectangleCached = Rectangle(Position({ position.x + size.width - 1 - 16F }, { scrollerY - 1}), Size({ 16F }, { scrollerHeight }))
                updateScrollerRectangle = false
                return scrollerRectangleCached
            } else {
                scrollerRectangleCached
            }
        }

    private val scrollbarRectangle: Rectangle
        get() {
            return if (updateScrollbarRectangle) {
                scrollbarRectangleCached = Rectangle(Position({ position.x + size.width - 1 - 16F }, { position.y + 1 }), Size({ 16F }, { size.height - 2 }))
                updateScrollbarRectangle = false
                return scrollbarRectangleCached
            } else {
                scrollbarRectangleCached
            }
        }

    override fun addWidget(widget: AbstractWidget) {
        widgets.add(widget)
        original?.let { widget.onAdded(it, this) }

        widgets.forEach { _ ->
            widget.onLayoutChanged()
        }

        super.addWidget(widget)
    }

    override fun removeWidget(widget: AbstractWidget) {
        widgets.remove(widget)
        original?.let { widget.onRemoved(it, this) }

        widgets.forEach { _ ->
            widget.onLayoutChanged()
        }

        super.removeWidget(widget)
    }

    override fun onLayoutChanged() {
        super.onLayoutChanged()

        updateScrollerRectangle = true
        updateScrollbarRectangle = true
    }

    override fun onAdded(original: OriginalWidgetCollection, immediate: WidgetCollection) {
        super.onAdded(original, immediate)

        synchronize.add(Networks.MOUSE_SCROLL)
        synchronize.add(Networks.MOUSE_CLICK)

        widthInSlots = (size.width / 18 - 1).toInt()
        heightInSlots = (size.height / 18).toInt()
        maximumSlots = inventory.size() / widthInSlots

        for (h in 0 until heightInSlots) {
            for (w in 0 until widthInSlots) {
                if ((inventory.size() >= h + w)) {
                    val slot = SlotWidget(0 + h * widthInSlots + w, inventory)
                    slot.position = Position({ position.x + w * 18F }, { position.y + h * 18F })
                    slot.size = Size({ 18F }, { 18F })
                    widgets.add(slot)
                    immediate.addWidget(slot)
                }
            }
        }
    }

    override fun onMouseClicked(x: Float, y: Float, button: Int) {
        if (handler!!.client) {
            super.onMouseClicked(x, y, button)
        }

        if (scrollerRectangle.isWithin(x, y)) {
            scrollerHeld = true
        } else if (scrollbarRectangle.isWithin(x, y)) {
            if (y > scrollerY) {
                onMouseScrolled(x, y, -1.0)
            } else if (y < scrollerY) {
                onMouseScrolled(x, y, 1.0)
            }
        }
    }

    override fun onMouseReleased(x: Float, y: Float, button: Int) {
        if (handler!!.client) {
            super.onMouseReleased(x, y, button)
        }

        scrollerHeld = false
    }

    override fun onMouseDragged(x: Float, y: Float, button: Int, deltaX: Double, deltaY: Double) {
        if (handler!!.client) {
            super.onMouseDragged(x, y, button, deltaX, deltaY)
        }

        if (scrollerHeld) {
            if (deltaY > 0) {
                while (scrollerY < y - scrollerHeight / 2 && row < bottomRow) {
                    onMouseScrolled(x, y, -deltaY)
                }
            } else if (deltaY < 0) {
                while (scrollerY > y - scrollerHeight / 2 && row > 0) {
                    onMouseScrolled(x, y, -deltaY)
                }
            }
        }
    }

    override fun onMouseScrolled(x: Float, y: Float, deltaY: Double) {
        if (handler!!.client) {
            if (focused || scrollerHeld) {
                super.onMouseScrolled(x, y, deltaY)
            } else {
                return
            }
        }

        if (deltaY > 0 && row > 0) {
            --row

            if (!handler!!.client) {
                widgets.forEach {
                    it as SlotWidget

                    val slot = it.backendSlot!!

                    if (slot.index - widthInSlots >= 0) {
                        slot.canInsert = true
                        slot.index = slot.index - widthInSlots
                    }
                }
            }
        } else if (deltaY < 0 && row < bottomRow) {
            ++row

            if (!handler!!.client) {
                widgets.forEach {
                    it as SlotWidget

                    val slot = it.backendSlot!!

                    if (slot.index + widthInSlots <= inventory.size()) {
                        slot.canInsert = true
                        slot.index = slot.index + widthInSlots
                    }
                }
            }
        }

        updateScrollerRectangle = true
        updateScrollbarRectangle = true
    }

    override fun drawWidget(matrices: MatrixStack, provider: VertexConsumerProvider) {
        if (hidden) return

        textureScrollbar.draw(matrices, provider, position.x + size.width - 18F, position.y, 18F, size.height)

        val scrollerFocus = scrollerRectangle.isWithin(Positions.mouseX, Positions.mouseY)

        if (scrollerFocus || scrollerHeld) {
            textureScrollerFocus.draw(matrices, provider, position.x + size.width - 18F + 1F, scrollerY - 1, 16F, scrollerHeight)
        } else {
            textureScroller.draw(matrices, provider, position.x + size.width  - 18F + 1F, scrollerY - 1, 16F, scrollerHeight)
        }
    }
}