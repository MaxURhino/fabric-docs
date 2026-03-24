package com.example.docs.datagen;

import com.example.docs.fluid.FluidTags;

import com.example.docs.fluid.ModFluids;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ExampleModFluidTagProvider extends FabricTagProvider.FluidTagProvider {
	public ExampleModFluidTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		valueLookupBuilder(FluidTags.ACID).add(ModFluids.ACID_STILL, ModFluids.ACID_FLOWING);
	}
}
