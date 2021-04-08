package ericchiu.simplerail.block;

import java.util.List;
import java.util.UUID;

import ericchiu.simplerail.block.base.BasePoweredRail;
import ericchiu.simplerail.config.CommonConfig;
import ericchiu.simplerail.setup.SimpleRailProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

public class TimerHoldingRail extends BasePoweredRail {

  public static final EnumProperty<Direction> DIRECTION = SimpleRailProperties.DIRECTION;
  public static final IntegerProperty LEVEL = SimpleRailProperties.LEVEL;
  public static final IntegerProperty GO_TIME = SimpleRailProperties.GO_TIME;

  private static final int LV1 = CommonConfig.INSTANCE.timerHoldingRailLv1.get();
  private static final int LV2 = CommonConfig.INSTANCE.timerHoldingRailLv2.get();
  private static final int LV3 = CommonConfig.INSTANCE.timerHoldingRailLv3.get();
  private static final int LV4 = CommonConfig.INSTANCE.timerHoldingRailLv4.get();
  private static final int LV5 = CommonConfig.INSTANCE.timerHoldingRailLv5.get();
  private static final int LV6 = CommonConfig.INSTANCE.timerHoldingRailLv6.get();
  private static final int LV7 = CommonConfig.INSTANCE.timerHoldingRailLv7.get();
  private static final int LV8 = CommonConfig.INSTANCE.timerHoldingRailLv8.get();
  private static final int LV9 = CommonConfig.INSTANCE.timerHoldingRailLv9.get();

  private UUID currentCartUuid = null;

  public TimerHoldingRail() {
    super();

    this.registerDefaultState(this.stateDefinition.any().setValue(DIRECTION, Direction.NORTH));
    this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0));
    this.registerDefaultState(this.stateDefinition.any().setValue(GO_TIME, 0));
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    builder.add(DIRECTION);
    builder.add(LEVEL);
    builder.add(GO_TIME);
    super.createBlockStateDefinition(builder);
  }

  @Override
  public void onMinecartPass(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    boolean powered = state.getValue(BlockStateProperties.POWERED);

    

    // if (!powered) {
    //   if (!cart.getDeltaMovement().equals(Vector3d.ZERO)) {
    //     world.setBlock(pos, state.setValue(DIRECTION, cart.getMotionDirection()), 3);
    //   }

    //   cart.setDeltaMovement(Vector3d.ZERO);
    //   cart.moveTo(pos, cart.yRot, cart.xRot);
    // }
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
