package ericchiu.simplerail.setup;

import ericchiu.simplerail.registry.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

public class RenderLayer {

  public static void set() {
    RenderType cutout = RenderType.cutout();

    RenderTypeLookup.setRenderLayer(Blocks.HIGH_SPEED_RAIL, cutout);
    RenderTypeLookup.setRenderLayer(Blocks.HOLDING_RAIL, cutout);
    RenderTypeLookup.setRenderLayer(Blocks.ONEWAY_FORWARD_RAIL, cutout);
    RenderTypeLookup.setRenderLayer(Blocks.ONEWAY_REVERSE_RAIL, cutout);
  }

}
