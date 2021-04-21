package ericchiu.simplerail.registry;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.constants.TileEntityCons;
import ericchiu.simplerail.tileentity.SingnalTimerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraftforge.event.RegistryEvent;

public class TileEntities {

  public static final TileEntityType<SingnalTimerTileEntity> SINGNAL_TIMER = Builder
      .of(SingnalTimerTileEntity::new, Blocks.SINGNAL_TIMER.get()).build(null);

  public static void register(RegistryEvent.Register<TileEntityType<?>> evt) {
    SINGNAL_TIMER.setRegistryName(SimpleRail.MOD_ID, TileEntityCons.SINGNAL_TIMER);

    evt.getRegistry().register(SINGNAL_TIMER);
  }

}
