package ericchiu.simplerail.block;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ericchiu.simplerail.block.base.BaseRail;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class CrossRail extends BaseRail {

  private Map<BlockPos, CartData> crossRailData = new HashMap<BlockPos, CartData>();

  public CrossRail() {
    super();
  }

  @Override
  public boolean isStraight() {
    return true;
  }

  @Override
  public void onMinecartPass(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    CartData cartData = this.crossRailData.get(pos);

    if (cartData == null || !cartData.uuid.equals(cart.getUUID())) {
      Vector3d motion = cart.getDeltaMovement();
      if (motion.x == 0 && motion.z == 0) {
        return;
      }

      Direction direction = cart.getMotionDirection();
      double speed = Math.max(Math.abs(motion.x), Math.abs(motion.z));

      cartData = new CartData(cart.getUUID(), speed, direction, this.getDestPos(pos, direction), cart.yRot, cart.xRot);
      this.crossRailData.put(pos, cartData);

      if (direction.equals(Direction.EAST) || direction.equals(Direction.WEST)) {
        world.setBlock(pos, state.setValue(BlockStateProperties.RAIL_SHAPE, RailShape.EAST_WEST), 3);
      } else if (direction.equals(Direction.NORTH) || direction.equals(Direction.SOUTH)) {
        world.setBlock(pos, state.setValue(BlockStateProperties.RAIL_SHAPE, RailShape.NORTH_SOUTH), 3);
      }

      cart.setDeltaMovement(Vector3d.ZERO);
      cart.moveTo(pos, cart.yRot, cart.xRot);
      return;
    }

    cart.moveTo(cartData.destPos, cartData.yRot, cartData.xRot);
    cart.setDeltaMovement( //
        cartData.direction.getStepX() * cartData.speed, //
        cartData.direction.getStepY(), //
        cartData.direction.getStepZ() * cartData.speed);
  }

  @Override
  public void destroy(IWorld world, BlockPos pos, BlockState state) {
    this.crossRailData.remove(pos);
    super.destroy(world, pos, state);
  }

  @Override
  public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest,
      FluidState fluid) {
    this.crossRailData.remove(pos);
    return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
  }

  private BlockPos getDestPos(BlockPos pos, Direction direction) {
    if (Direction.EAST.equals(direction)) {
      return pos.east();
    } else if (Direction.WEST.equals(direction)) {
      return pos.west();
    } else if (Direction.NORTH.equals(direction)) {
      return pos.north();
    } else if (Direction.SOUTH.equals(direction)) {
      return pos.south();
    }

    return pos;
  }

  private static class CartData {

    public final UUID uuid;
    public final double speed;
    public final Direction direction;
    public final BlockPos destPos;
    public final float yRot;
    public final float xRot;

    public CartData(UUID uuid, double speed, Direction direction, BlockPos destPos, float yRot, float xRot) {
      this.uuid = uuid;
      this.speed = speed;
      this.direction = direction;
      this.destPos = destPos;
      this.yRot = yRot;
      this.xRot = xRot;
    }

  }

}
