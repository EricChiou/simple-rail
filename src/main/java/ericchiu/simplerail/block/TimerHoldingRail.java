package ericchiu.simplerail.block;

import java.util.List;
import java.util.Random;

import ericchiu.simplerail.block.base.BasePoweredRail;
import ericchiu.simplerail.setup.SimpleRailProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TimerHoldingRail extends BasePoweredRail {

  public static final EnumProperty<Direction> DIRECTION = SimpleRailProperties.DIRECTION;

  public TimerHoldingRail() {
    super();

    this.registerDefaultState(this.stateDefinition.any().setValue(DIRECTION, Direction.NORTH));
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    builder.add(DIRECTION);
    super.createBlockStateDefinition(builder);
  }

  @Override
  public void onMinecartPass(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    boolean powered = state.getValue(BlockStateProperties.POWERED);

    if (!powered) {
      if (!cart.getDeltaMovement().equals(Vector3d.ZERO)) {
        world.setBlock(pos, state.setValue(DIRECTION, cart.getMotionDirection()), 3);
      }

      cart.setDeltaMovement(Vector3d.ZERO);
      cart.moveTo(pos, cart.yRot, cart.xRot);
    }
  }

  @Override
  protected void updateState(BlockState state, World world, BlockPos pos, Block block) {
    boolean powered = state.getValue(BlockStateProperties.POWERED);
    if (powered) {
      Vector3i motion = state.getValue(DIRECTION).getNormal();
      List<AbstractMinecartEntity> carts = world.getEntitiesOfClass(AbstractMinecartEntity.class,
          new AxisAlignedBB(pos));
      for (AbstractMinecartEntity cart : carts) {
        cart.setDeltaMovement(motion.getX() * 0.4D, motion.getY() * 0.4D, motion.getZ() * 0.4D);
      }
    }

    super.updateState(state, world, pos, block);
  }

}
