package com.example.docs.fluid.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jspecify.annotations.Nullable;
import net.minecraft.util.RandomSource;

public abstract class AcidFluid extends FlowingFluid {
	 	@Override
    public void animateTick(Level world, BlockPos pos, FluidState state, RandomSource random) {
        if (!state.isStill() && !(Boolean)state.get(FALLING)) {
            if (random.nextInt(64) == 0) {
                world.playLocalSound(
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT,  // Bubbling poison/swamp sound
                        SoundSource.AMBIENT,
                        random.nextFloat() * 0.25F + 0.75F,
                        random.nextFloat() + 0.5F,
                        false
                );
            }
        } else if (random.nextInt(10) == 0) {
            world.addParticle(
                    ParticleTypes.UNDERWATER, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0
            );
        }
    }

		@Nullable
		@Override
		public ParticleOptions getDripParticle() {
				return ParticleTypes.DRIPPING_WATER;
		}
}
