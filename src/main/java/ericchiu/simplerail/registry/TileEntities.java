package ericchiu.simplerail.registry;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.constants.TileEntityCons;
import ericchiu.simplerail.tileentity.SingnalTimerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntities {

  // private static final DeferredRegister<TileEntityType<?>> REGISTER =
  // DeferredRegister
  // .create(ForgeRegistries.TILE_ENTITIES, SimpleRail.MOD_ID);

  // tile entity type
  public static final TileEntityType<SingnalTimerTileEntity> SINGNAL_TIMER = Builder
      .of(SingnalTimerTileEntity::new, Blocks.SINGNAL_TIMER.get()).build(null);

  // tile entity type register object
  // public static final RegistryObject<TileEntityType<SingnalTimerTileEntity>>
  // SINGNAL_TIMER_OBJ = REGISTER
  // .register(TileEntityCons.SINGNAL_TIMER, () -> SINGNAL_TIMER);

  public static void register(IEventBus bus) {
    // REGISTER.register(bus);
    SINGNAL_TIMER.setRegistryName(SimpleRail.MOD_ID, TileEntityCons.SINGNAL_TIMER);

    Registry.register(Registry.BLOCK_ENTITY_TYPE, TileEntityCons.SINGNAL_TIMER, SINGNAL_TIMER);
  }

}
