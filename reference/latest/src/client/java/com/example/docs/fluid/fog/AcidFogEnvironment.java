package com.example.docs.fluid.fog;

import com.example.docs.fluid.ModFluids;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;

import org.jetbrains.annotations.Nullable;

// :::1
public class AcidFogEnvironment extends FogEnvironment {
    @Override
    public void setupFog(FogData data, Camera camera, ClientLevel level, float renderDistance, DeltaTracker delta) {
        data.environmentalStart = -8.0F;
        data.environmentalEnd = 6.0F;
    }

    @Override
    public boolean isApplicable(@Nullable FogType submersionType, Entity cameraEntity) {
        if (submersionType != FogType.WATER) return false;

        // Get camera from Minecraft
        Minecraft minecraft = Minecraft.getInstance();
        Camera camera = minecraft.gameRenderer.getMainCamera();
        BlockPos pos = camera.blockPosition();

        // Get fluid state from the level
        ClientLevel level = minecraft.level;
        if (level == null) return false;

        FluidState fluidState = level.getFluidState(pos);
        Fluid fluid = fluidState.getType();

        // Check if it's one of our custom water fluids
        return fluid == ModFluids.ACID_STILL || fluid == ModFluids.ACID_FLOWING;
    }

    @Override
    public int getBaseColor(ClientLevel level, Camera camera, int viewDistance, float tickDelta) {
			return 0x075800;
    }
}
// :::1
