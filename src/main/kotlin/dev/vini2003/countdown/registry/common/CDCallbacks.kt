package dev.vini2003.countdown.registry.common

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.server.world.ServerWorld
import net.minecraft.structure.Structure
import net.minecraft.structure.StructurePlacementData
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.joda.time.*

class CDCallbacks {
	companion object {
		var End = DateTime(2021, 7, 21, 0, 0)
		
		var Structures = mutableMapOf<Char, Structure>()
		
		fun init() {
			ServerTickEvents.END_WORLD_TICK.register { world ->
				if (Structures.isEmpty()) {
					Structures['0'] = world.structureManager.getStructure(Identifier("zero")).get()
					Structures['1'] = world.structureManager.getStructure(Identifier("one")).get()
					Structures['2'] = world.structureManager.getStructure(Identifier("two")).get()
					Structures['3'] = world.structureManager.getStructure(Identifier("three")).get()
					Structures['4'] = world.structureManager.getStructure(Identifier("four")).get()
					Structures['5'] = world.structureManager.getStructure(Identifier("five")).get()
					Structures['6'] = world.structureManager.getStructure(Identifier("six")).get()
					Structures['7'] = world.structureManager.getStructure(Identifier("seven")).get()
					Structures['8'] = world.structureManager.getStructure(Identifier("eight")).get()
					Structures['9'] = world.structureManager.getStructure(Identifier("nine")).get()
					Structures[':'] = world.structureManager.getStructure(Identifier("comma")).get()
				}
				
				if (world.registryKey == World.OVERWORLD) {
					val now = DateTime.now()
					val then = End
					
					val days = Days.daysBetween(now.toLocalDateTime(), then.toLocalDateTime()).days
					val hours = Hours.hoursBetween(now.toLocalDateTime(), then.toLocalDateTime()).hours % 24
					val minutes = Minutes.minutesBetween(now.toLocalDateTime(), then.toLocalDateTime()).minutes % 60
					val seconds = Seconds.secondsBetween(now.toLocalDateTime(), then.toLocalDateTime()).seconds % 60
					
					val daysString = if (days <= 9) "0${days}" else "$days"
					val hoursString = if (hours <= 9) "0${hours}" else "$hours"
					val minutesString = if (minutes <= 9) "0${minutes}" else "$minutes"
					val secondsString = if (seconds <= 9) "0${seconds}" else "$seconds"
					
					place(secondsString[1], world, BlockPos(-248 - 5, 123, -158))
					place(secondsString[0], world, BlockPos(-254 - 5, 123, -158))
					
					place(':', world, BlockPos(-256 - 5, 123, -158))
					
					place(minutesString[1], world, BlockPos(-262 - 5, 123, -158))
					place(minutesString[0], world, BlockPos(-268 - 5, 123, -158))
					
					place(':', world, BlockPos(-270 - 5, 123, -158))
					
					place(hoursString[1], world, BlockPos(-276 - 5, 123, -158))
					place(hoursString[0], world, BlockPos(-282 - 5, 123, -158))
					
					place(':', world, BlockPos(-284 - 5, 123, -158))
					
					place(daysString[1], world, BlockPos(-290 - 5, 123, -158))
					place(daysString[0], world, BlockPos(-296 - 5, 123, -158))
				}
			}
		}
		
		private fun place(digit: Char, world: ServerWorld, pos: BlockPos) {
			val structure = Structures[digit]!!
			
			structure.place(world, pos, pos, StructurePlacementData(), world.random, 2)
		}
	}
}