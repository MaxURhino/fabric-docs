package com.example.docs.mixin.client;

import com.example.docs.fog.ExampleModFogTypes;
import com.example.docs.fluid.FluidTags;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.client.Camera;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class CameraMixin {
	@Inject(method = "getFluidInCamera", at = @At(value = "INVOKE:FIRST", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"), cancellable = true)
	private static void getAcidInCamera(CallbackInfoReturnable<FogType> cir, @Local FluidState fluidState) {
		if(!fluidState.is(FluidTags.ACID)) {
			return;
		}

		cir.setReturnValue(ExampleModFogTypes.ACID);
	}
}
