package ericchiu.simplerail.setup;

import ericchiu.simplerail.entity.LocomotiveCartEntity;
import ericchiu.simplerail.registry.Blocks;
import ericchiu.simplerail.registry.Entities;
import ericchiu.simplerail.render.LocomotiveCartRender;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class Render {

  public static void setup() {
    RenderType cutout = RenderType.cutout();

    RenderTypeLookup.setRenderLayer(Blocks.HIGH_SPEED_RAIL.get(), cutout);
    RenderTypeLookup.setRenderLayer(Blocks.HOLDING_RAIL.get(), cutout);
    RenderTypeLookup.setRenderLayer(Blocks.ONEWAY_RAIL.get(), cutout);
    RenderTypeLookup.setRenderLayer(Blocks.EJECT_RAIL.get(), cutout);

    RenderingRegistry.registerEntityRenderingHandler(Entities.LOCOMOTIVE_CART.get(),
        manager -> new LocomotiveCartRender<LocomotiveCartEntity>(manager));
  }

}
