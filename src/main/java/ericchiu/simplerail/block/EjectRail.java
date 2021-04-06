package ericchiu.simplerail.block;

import java.util.List;

import ericchiu.simplerail.config.CommonConfig;
import ericchiu.simplerail.setup.SimpleRailProperties;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class EjectRail extends PoweredRailBlock {

  public static final BooleanProperty REVERSE = SimpleRailProperties.REVERSE;
  public static final BooleanProperty NEED_POWER = SimpleRailProperties.NEED_POWER;

  public EjectRail() {
    super(AbstractBlock.Properties //
        .of(Material.METAL) //
        .strength(0.7F). //
        harvestLevel(0). //
        harvestTool(ToolType.PICKAXE). //
        sound(SoundType.METAL). //
        noCollission(), //
        true);

    this.registerDefaultState(this.stateDefinition.any().setValue(REVERSE, false));
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    builder.add(REVERSE);
    builder.add(NEED_POWER);
    super.createBlockStateDefinition(builder);
  }

  @Override
  public float getRailMaxSpeed(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    return CommonConfig.INSTANCE.highSpeedRailMaxSpeed.get().floatValue();
  }

  @Override
  public boolean canMakeSlopes(BlockState state, IBlockReader world, BlockPos pos) {
    return false;
  }

  @Override
  public void onMinecartPass(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    RailShape shape = state.getValue(BlockStateProperties.RAIL_SHAPE_STRAIGHT);
    boolean powered = state.getValue(BlockStateProperties.POWERED);
    boolean reverse = state.getValue(SimpleRailProperties.REVERSE);

    if (world.isClientSide) {
      return;
    }

    if (!powered && CommonConfig.INSTANCE.ejectRailNeedPower.get()) {
      return;
    }

    List<Entity> passengers = cart.getPassengers();
    if (passengers.size() <= 0) {
      return;
    }

    passengers.forEach((passenger) -> {
      cart.ejectPassengers();
      passenger.setDeltaMovement(Vector3d.ZERO);
      transportPassenger(pos, passenger, shape, reverse);
    });
  }

  @Override
  public void onPlace(BlockState state, World world, BlockPos pos, BlockState originState, boolean bool) {
    BlockState newState = state.setValue(NEED_POWER, CommonConfig.INSTANCE.ejectRailNeedPower.get());
    super.onPlace(newState, world, pos, originState, bool);
  }

  private void transportPassenger(BlockPos pos, Entity passenger, RailShape shape, boolean reverse) {
    passenger.setDeltaMovement(Vector3d.ZERO);

    Integer transportDistance = CommonConfig.INSTANCE.ejectRailTransportDistance.get();
    if (shape.equals(RailShape.NORTH_SOUTH)) {
      double deltaX = reverse ? transportDistance + 0.5D : -transportDistance - 0.5D;
      passenger.moveTo(pos.getX() + deltaX, pos.getY(), pos.getZ() + 0.5D);

    } else {
      double deltaZ = reverse ? transportDistance + 0.5D : -transportDistance - 0.5D;
      passenger.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + deltaZ);
    }
  }

}
