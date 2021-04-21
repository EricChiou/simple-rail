package ericchiu.simplerail.tileentity;

import ericchiu.simplerail.registry.TileEntities;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.world.TickPriority;

public class SingnalTimerTileEntity extends TileEntity implements ITickableTileEntity {

  public SingnalTimerTileEntity(TileEntityType<?> type) {
    super(type);
  }

  public SingnalTimerTileEntity() {
    this(TileEntities.SINGNAL_TIMER);
  }

  @Override
  public void tick() {
    System.out.println("11111 SingnalTimerTileEntity tick");
    // this.getLevel().getBlockTicks().scheduleTick(new BlockPos(0, 0, 0),
    // this.getBlockState().getBlock(), 20,
    // TickPriority.VERY_HIGH);
  }

}
