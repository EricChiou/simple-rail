package ericchiu.simplerail.block;

import ericchiu.simplerail.block.base.BasePoweredRail;
import ericchiu.simplerail.config.CommonConfig;
import ericchiu.simplerail.entity.LocomotiveCartEntity;
import ericchiu.simplerail.setup.SimpleRailProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DestoryRail extends BasePoweredRail {

	public static final BooleanProperty NEED_POWER = SimpleRailProperties.NEED_POWER;

	public DestoryRail() {
		super();

		this.registerDefaultState(this.stateDefinition.any() //
				.setValue(NEED_POWER, CommonConfig.INSTANCE.destoryRailNeedPower.get()));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(NEED_POWER);
		super.createBlockStateDefinition(builder);
	}

	@Override
	public void onMinecartPass(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
		boolean powered = state.getValue(BlockStateProperties.POWERED);

		if (!CommonConfig.INSTANCE.destoryRailNeedPower.get() || powered) {
			if (cart instanceof LocomotiveCartEntity) {
				((LocomotiveCartEntity) cart).deleteTrain();
			} else {
				cart.remove();
			}

			world.addParticle(ParticleTypes.LARGE_SMOKE, //
					pos.getX() + 0.5D, pos.getY() + 0.75D, pos.getZ() + 0.5D, //
					0.0D, 0.0D, 0.0D);
			world.playLocalSound(pos.getX() + 0.5D, pos.getY() + 0.75D, pos.getZ() + 0.5D, SoundEvents.GENERIC_BURN,
					SoundCategory.BLOCKS, 4.0F, 4.0F, false);
		}
	}

	@Override
	public void onPlace(BlockState state, World world, BlockPos pos, BlockState originState, boolean bool) {
		BlockState newState = state.setValue(NEED_POWER, CommonConfig.INSTANCE.destoryRailNeedPower.get());
		super.onPlace(newState, world, pos, originState, bool);
	}

}
