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
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class HoldingRail extends PoweredRailBlock {

  public final BlockItem blockItem;

  private Direction cartDirection;
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
  public boolean canMakeSlopes(BlockState state, IBlockReader world, BlockPos pos) {
    return false;
  }

  @Override
  public void onMinecartPass(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    boolean powered = state.getValue(BlockStateProperties.POWERED);
    cartEntity = cart;
    cartDirection = cart.getMotionDirection();

    if (!powered) {
      cart.setDeltaMovement(Vector3d.ZERO);
    }
  }

  @Override
  protected void updateState(BlockState state, World world, BlockPos pos, Block block) {
    boolean powered = state.getValue(BlockStateProperties.POWERED);

    if (powered && cartEntity != null && cartDirection != null) {
      Vector3i cartMotion = cartDirection.getNormal();
      cartEntity.setDeltaMovement(cartMotion.getX() * 0.4d, cartMotion.getY() * 0.4d, cartMotion.getZ() * 0.4d);
    }

    super.updateState(state, world, pos, block);
  }

}
