package com.example.docs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.level.material.FogType;

// :::renderer
@Mixin(FogType.class)
public interface FogTypeInvoker {
	@Invoker("<init>")
	static FogType init(String name, int index) {
		return null;
	}
}
// :::renderer
