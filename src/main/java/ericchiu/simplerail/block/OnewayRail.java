package ericchiu.simplerail.block;

import ericchiu.simplerail.block.base.BasePoweredRail;
import ericchiu.simplerail.config.CommonConfig;
import ericchiu.simplerail.setup.SimpleRailProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class OnewayRail extends BasePoweredRail {

  public static final BooleanProperty REVERSE = SimpleRailProperties.REVERSE;
  public static final BooleanProperty NEED_POWER = SimpleRailProperties.NEED_POWER;
  public static final BooleanProperty USE_POWER = SimpleRailProperties.USE_POWER;

  public OnewayRail() {
    super();

    this.registerDefaultState(this.stateDefinition.any() //
        .setValue(REVERSE, false) //
        .setValue(NEED_POWER, CommonConfig.INSTANCE.onewayRailNeedPower.get()) //
        .setValue(USE_POWER, CommonConfig.INSTANCE.onewayRailUsePowerChangeDirection.get()));
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    builder.add(REVERSE);
    builder.add(NEED_POWER);
    builder.add(USE_POWER);
    super.createBlockStateDefinition(builder);
  }

  @Override
  public void onMinecartPass(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    RailShape shape = state.getValue(BlockStateProperties.RAIL_SHAPE_STRAIGHT);
    boolean powered = state.getValue(BlockStateProperties.POWERED);
    boolean reverse = state.getValue(SimpleRailProperties.REVERSE);

    if (CommonConfig.INSTANCE.onewayRailUsePowerChangeDirection.get() //
        || powered //
        || !CommonConfig.INSTANCE.onewayRailNeedPower.get() //
    ) {
      if (reverse) {
        goForward(shape, cart);
      } else {
        goReverse(shape, cart);
      }
    } else {
      if (!cart.getDeltaMovement().equals(Vector3d.ZERO)) {
        cart.setDeltaMovement(cart.getDeltaMovement().multiply(1.2, 1.2, 1.2));
      }
    }
  }

  @Override
  public void onPlace(BlockState state, World world, BlockPos pos, BlockState originState, boolean bool) {
    BlockState newState = state //
        .setValue(NEED_POWER, CommonConfig.INSTANCE.onewayRailNeedPower.get())
        .setValue(USE_POWER, CommonConfig.INSTANCE.onewayRailUsePowerChangeDirection.get());

    super.onPlace(newState, world, pos, originState, bool);
  }

  private void goForward(RailShape shape, AbstractMinecartEntity cart) {
    if (shape.equals(RailShape.NORTH_SOUTH)) {
      cart.setDeltaMovement(0, 0, 0.4D);
    } else {
      cart.setDeltaMovement(-0.4D, 0, 0);
    }
  }

  private void goReverse(RailShape shape, AbstractMinecartEntity cart) {
    if (shape.equals(RailShape.NORTH_SOUTH)) {
      cart.setDeltaMovement(0, 0, -0.4D);
    } else {
      cart.setDeltaMovement(0.4D, 0, 0);
    }
  }

}
