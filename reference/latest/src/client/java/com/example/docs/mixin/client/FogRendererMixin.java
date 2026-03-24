package com.example.docs.mixin.client;

import com.example.docs.fluid.fog.ModFogEnvironment;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

// :::1
@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Shadow @Final private static List<FogEnvironment> FOG_ENVIRONMENTS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addCustomFogEnvironment(CallbackInfo ci) {
        // Add our custom fog modifier at the beginning so it takes priority over default water
        FOG_ENVIRONMENTS.add(0, new ModFogEnvironment());
    }
}
// :::1
