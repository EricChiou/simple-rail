package ericchiu.simplerail.block;

import ericchiu.simplerail.block.base.BasePoweredRail;
import ericchiu.simplerail.config.CommonConfig;
import ericchiu.simplerail.setup.SimpleRailProperties;
import ericchiu.simplerail.tileentity.TimerHoldingRailTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TimerHoldingRail extends BasePoweredRail {

  public static final EnumProperty<Direction> DIRECTION = SimpleRailProperties.DIRECTION;
  public static final IntegerProperty LEVEL = SimpleRailProperties.LEVEL;

  private static final int LV1 = CommonConfig.INSTANCE.timerHoldingRailLv1.get();
  private static final int LV2 = CommonConfig.INSTANCE.timerHoldingRailLv2.get();
  private static final int LV3 = CommonConfig.INSTANCE.timerHoldingRailLv3.get();
  private static final int LV4 = CommonConfig.INSTANCE.timerHoldingRailLv4.get();
  private static final int LV5 = CommonConfig.INSTANCE.timerHoldingRailLv5.get();
  private static final int LV6 = CommonConfig.INSTANCE.timerHoldingRailLv6.get();
  private static final int LV7 = CommonConfig.INSTANCE.timerHoldingRailLv7.get();
  private static final int LV8 = CommonConfig.INSTANCE.timerHoldingRailLv8.get();
  private static final int LV9 = CommonConfig.INSTANCE.timerHoldingRailLv9.get();

  public TimerHoldingRail() {
    super();
    this.registerDefaultState(this.stateDefinition.any().setValue(DIRECTION, Direction.NORTH));
    this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0));
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    builder.add(DIRECTION);
    builder.add(LEVEL);
    super.createBlockStateDefinition(builder);
  }

  @Override
  public void onMinecartPass(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    if (world.isClientSide) {
      return;
    }

    ServerWorld serverWorld = (ServerWorld) world;

    int level = state.getValue(LEVEL);
    if (level == 0) {
      this.doNothingToCart(cart);
      return;
    }

    TimerHoldingRailTileEntity tileEntity = this.getTileEntity(serverWorld, pos);
    if (tileEntity == null) {
      this.doNothingToCart(cart);
      return;
    }

    if (cart.getUUID().equals(tileEntity.getCartUuid())) {
      if (System.currentTimeMillis() >= tileEntity.getGoTime()) {
        Vector3i motion = state.getValue(DIRECTION).getNormal();
        cart.setDeltaMovement(motion.getX() * 0.4D, motion.getY() * 0.4D, motion.getZ() * 0.4D);
      } else {
        this.stopCart(pos, cart);
      }
    } else {
      world.setBlock(pos, state.setValue(DIRECTION, cart.getMotionDirection()), 3);

      this.stopCart(pos, cart);
      tileEntity.setCartUuid(cart.getUUID());
      tileEntity.setGoTime(this.calGoTime(level));
    }
  }

  private long calGoTime(int lv) {
    int holdingTime = 0;
    switch (lv) {
    case 1:
      holdingTime = LV1 * 1000;
      break;
    case 2:
      holdingTime = LV2 * 1000;
      break;
    case 3:
      holdingTime = LV3 * 1000;
      break;
    case 4:
      holdingTime = LV4 * 1000;
      break;
    case 5:
      holdingTime = LV5 * 1000;
      break;
    case 6:
      holdingTime = LV6 * 1000;
      break;
    case 7:
      holdingTime = LV7 * 1000;
      break;
    case 8:
      holdingTime = LV8 * 1000;
      break;
    case 9:
      holdingTime = LV9 * 1000;
      break;
    }

    return System.currentTimeMillis() + holdingTime;
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TimerHoldingRailTileEntity();
  }

  private TimerHoldingRailTileEntity getTileEntity(ServerWorld serverWorld, BlockPos pos) {
    try {
      return (TimerHoldingRailTileEntity) serverWorld.getBlockEntity(pos);
    } catch (Exception e) {
      return null;
    }
  }

  private void doNothingToCart(AbstractMinecartEntity cart) {
    if (!cart.getDeltaMovement().equals(Vector3d.ZERO)) {
      cart.setDeltaMovement(cart.getDeltaMovement().multiply(1.2, 1.2, 1.2));
    }
  }

  private void stopCart(BlockPos pos, AbstractMinecartEntity cart) {
    cart.setDeltaMovement(Vector3d.ZERO);
    cart.moveTo(pos, cart.yRot, cart.xRot);
  }

}
