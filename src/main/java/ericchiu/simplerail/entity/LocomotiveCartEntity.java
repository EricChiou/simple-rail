package ericchiu.simplerail.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.FurnaceMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LocomotiveCartEntity extends FurnaceMinecartEntity {

  public LocomotiveCartEntity(World world, double x, double y, double z) {
    super(world, x, y, z);
  }

  @Override
  protected void moveAlongTrack(BlockPos pos, BlockState state) {
    super.moveAlongTrack(pos, state);
  }

  @Override
  public void tick() {
    super.tick();
  }

}
