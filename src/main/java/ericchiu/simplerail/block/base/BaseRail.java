package ericchiu.simplerail.block.base;

import ericchiu.simplerail.config.CommonConfig;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.RailBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BaseRail extends RailBlock {

  public BaseRail() {
    super(AbstractBlock.Properties //
        .of(Material.DECORATION) //
        .noCollission() //
        .strength(0.7F) //
        .sound(SoundType.METAL) //
        .harvestTool(ToolType.PICKAXE));
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
  public boolean canEntityDestroy(BlockState state, IBlockReader world, BlockPos pos, Entity entity) {
    return false;
  }

}
