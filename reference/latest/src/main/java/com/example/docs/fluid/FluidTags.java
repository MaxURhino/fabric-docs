package com.example.docs.fluid;

import com.example.docs.ExampleMod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class FluidTags {
	public static TagKey<Fluid> ACID = TagKey.create(Registries.FLUID, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "acid"));
}
