package dev.vini2003.bodyshufflelite.component;

import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;

public class BodyParts {
	private final Map<String, Boolean> parts = new HashMap<>();
	
	public void set(String part, boolean state) {
		parts.put(part, state);
	}
	
	public boolean get(String part) {
		return parts.get(part);
	}
	
	public NbtCompound toNbt() {
		var nbt = new NbtCompound();
		
		parts.forEach(nbt::putBoolean);
		
		return nbt;
	}
	
	public void fromNbt(NbtCompound nbt) {
		nbt.getKeys().forEach(key -> {
			set(key, nbt.getBoolean(key));
		});
	}
}
