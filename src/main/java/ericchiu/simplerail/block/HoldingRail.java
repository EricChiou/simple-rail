package ericchiu.simplerail.block;

import ericchiu.simplerail.config.CommonConfig;
import ericchiu.simplerail.itemgroup.Rail;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class HoldingRail extends PoweredRailBlock {

  public final BlockItem blockItem;

  private Direction cartMotionDirection;
  private AbstractMinecartEntity cartEntity;

  public HoldingRail() {
    super(AbstractBlock.Properties //
        .of(Material.METAL) //
        .strength(0.7f). //
        harvestLevel(0). //
        harvestTool(ToolType.PICKAXE). //
        sound(SoundType.METAL). //
        noCollission(), //
        true);

    blockItem = new BlockItem(this, new Item.Properties().tab(Rail.TAB));
  }

  @Override
  public float getRailMaxSpeed(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    return CommonConfig.INSTANCE.highSpeedRailMaxSpeed.get().floatValue();
  }

  @Override
  public void onMinecartPass(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    boolean powered = state.getValue(BlockStateProperties.POWERED);
    cartEntity = cart;
    cartMotionDirection = cart.getMotionDirection();

    if (!powered) {
      cart.setDeltaMovement(Vector3d.ZERO);
    } else {
      super.onMinecartPass(state, world, pos, cart);
    }
  }

  @Override
  protected void updateState(BlockState state, World world, BlockPos pos, Block block) {
    boolean powered = state.getValue(BlockStateProperties.POWERED);

    if (powered && cartEntity != null && cartMotionDirection != null) {
      cartEntity.setDeltaMovement(cartMotionDirection.getStepX() * 0.4D, cartMotionDirection.getStepY() * 0.4D,
          cartMotionDirection.getStepZ() * 0.4D);
    }

    super.updateState(state, world, pos, block);
  }

}
