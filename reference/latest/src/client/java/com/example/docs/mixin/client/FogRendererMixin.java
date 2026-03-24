package com.example.docs.mixin.client;

import com.example.docs.fluid.fog.AcidFogEnvironment;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

// :::1
@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Shadow @Final private static List<FogEnvironment> FOG_ENVIRONMENTS;
		static {
			FOG_ENVIRONMENTS.addFirst(new AcidFogEnvironment());
		}
}
// :::1
