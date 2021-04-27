package ericchiu.simplerail.block;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ericchiu.simplerail.block.base.BaseRail;
import ericchiu.simplerail.setup.SimpleRailProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class YCrossRail extends BaseRail {

  public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
  public static final EnumProperty<Direction> DIRECTION = SimpleRailProperties.DIRECTION;

  private Map<BlockPos, CartData> yCrossRailData = new HashMap<BlockPos, CartData>();

  public YCrossRail() {
    super();
    this.registerDefaultState(this.stateDefinition.any() //
        .setValue(POWERED, false) //
        .setValue(DIRECTION, Direction.NORTH));
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    builder.add(POWERED, DIRECTION);
    super.createBlockStateDefinition(builder);
  }

  @Override
  public boolean isStraight() {
    return true;
  }

  @Override
  public void onMinecartPass(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    CartData cartData = this.yCrossRailData.get(pos);

    if (cartData == null || !cartData.uuid.equals(cart.getUUID())) {
      Vector3d motion = cart.getDeltaMovement();
      if (motion.x == 0 && motion.z == 0) {
        return;
      }

      Direction direction = cart.getMotionDirection();
      double speed = Math.max(Math.abs(motion.x), Math.abs(motion.z));

      Direction destDirection = this.getDestDirection(state, pos, direction);
      BlockPos destPos = this.getDestPos(pos, destDirection);

      cartData = new CartData(cart.getUUID(), speed, destDirection, destPos, cart.yRot, cart.xRot);
      this.yCrossRailData.put(pos, cartData);

      if (destDirection.equals(Direction.EAST) || destDirection.equals(Direction.WEST)) {
        world.setBlock(pos, state.setValue(BlockStateProperties.RAIL_SHAPE, RailShape.EAST_WEST), 3);
      } else if (destDirection.equals(Direction.NORTH) || destDirection.equals(Direction.SOUTH)) {
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
  public void onPlace(BlockState state, World world, BlockPos pos, BlockState p_220082_4_, boolean p_220082_5_) {
    boolean powered = world.hasNeighborSignal(pos);
    world.setBlock(pos, state.setValue(POWERED, powered), 3);
    super.onPlace(state, world, pos, p_220082_4_, p_220082_5_);
  }

  @Override
  protected void updateState(BlockState state, World world, BlockPos pos, Block block) {
    boolean powered = world.hasNeighborSignal(pos);
    world.setBlock(pos, state.setValue(POWERED, powered), 3);
    super.updateState(state, world, pos, block);
  }

  @Override
  public void destroy(IWorld world, BlockPos pos, BlockState state) {
    this.yCrossRailData.remove(pos);
    super.destroy(world, pos, state);
  }

  @Override
  public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest,
      FluidState fluid) {
    this.yCrossRailData.remove(pos);
    return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
  }

  private Direction getDestDirection(BlockState state, BlockPos pos, Direction direction) {
    Direction railDirection = state.getValue(DIRECTION);
    boolean powered = state.getValue(POWERED);

    if (Direction.EAST.equals(direction)) {
      if (Direction.EAST.equals(railDirection)) {
        if (powered) {
          return Direction.NORTH;
        } else {
          return Direction.EAST;
        }
      } else if (Direction.WEST.equals(railDirection)) {
        if (powered) {
          return Direction.EAST;
        } else {
          return Direction.EAST;
        }
      } else if (Direction.NORTH.equals(railDirection)) {
        if (powered) {
          return Direction.SOUTH;
        } else {
          return Direction.NORTH;
        }
      } else if (Direction.SOUTH.equals(railDirection)) {
        if (powered) {
          return Direction.NORTH;
        } else {
          return Direction.NORTH;
        }
      }
      return Direction.EAST;
    } else if (Direction.WEST.equals(direction)) {
      if (Direction.EAST.equals(railDirection)) {
        if (powered) {
          return Direction.WEST;
        } else {
          return Direction.WEST;
        }
      } else if (Direction.WEST.equals(railDirection)) {
        if (powered) {
          return Direction.SOUTH;
        } else {
          return Direction.WEST;
        }
      } else if (Direction.NORTH.equals(railDirection)) {
        if (powered) {
          return Direction.NORTH;
        } else {
          return Direction.NORTH;
        }
      } else if (Direction.SOUTH.equals(railDirection)) {
        if (powered) {
          return Direction.NORTH;
        } else {
          return Direction.NORTH;
        }
      }
      return Direction.WEST;
    } else if (Direction.NORTH.equals(direction)) {
      if (Direction.EAST.equals(railDirection)) {
        if (powered) {
          return Direction.NORTH;
        } else {
          return Direction.WEST;
        }
      } else if (Direction.WEST.equals(railDirection)) {
        if (powered) {
          return Direction.EAST;
        } else {
          return Direction.WEST;
        }
      } else if (Direction.NORTH.equals(railDirection)) {
        if (powered) {
          return Direction.WEST;
        } else {
          return Direction.NORTH;
        }
      } else if (Direction.SOUTH.equals(railDirection)) {
        if (powered) {
          return Direction.NORTH;
        } else {
          return Direction.NORTH;
        }
      }
      return Direction.NORTH;
    } else if (Direction.SOUTH.equals(direction)) {
      if (Direction.EAST.equals(railDirection)) {
        if (powered) {
          return Direction.WEST;
        } else {
          return Direction.WEST;
        }
      } else if (Direction.WEST.equals(railDirection)) {
        if (powered) {
          return Direction.WEST;
        } else {
          return Direction.WEST;
        }
      } else if (Direction.NORTH.equals(railDirection)) {
        if (powered) {
          return Direction.SOUTH;
        } else {
          return Direction.SOUTH;
        }
      } else if (Direction.SOUTH.equals(railDirection)) {
        if (powered) {
          return Direction.EAST;
        } else {
          return Direction.SOUTH;
        }
      }
      return Direction.SOUTH;
    }
    return Direction.NORTH;
  }

  private BlockPos getDestPos(BlockPos pos, Direction destDirection) {
    if (Direction.EAST.equals(destDirection)) {
      return pos.east();
    } else if (Direction.WEST.equals(destDirection)) {
      return pos.west();
    } else if (Direction.NORTH.equals(destDirection)) {
      return pos.north();
    } else if (Direction.SOUTH.equals(destDirection)) {
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
