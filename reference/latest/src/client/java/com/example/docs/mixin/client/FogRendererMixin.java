package com.example.docs.mixin.client;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;

import com.example.docs.fog.client.AcidFogEnvironment;

// :::renderer
@Mixin(FogRenderer.class)
public class FogRendererMixin {
	@Shadow
	@Final
	private static List<FogEnvironment> FOG_ENVIRONMENTS;
	static {
		FOG_ENVIRONMENTS.addFirst(new AcidFogEnvironment());
	}
}
// :::renderer
