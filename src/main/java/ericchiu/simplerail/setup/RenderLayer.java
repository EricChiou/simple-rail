package ericchiu.simplerail.setup;

import ericchiu.simplerail.registry.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

public class RenderLayer {

  public static void set() {
    RenderType cutout = RenderType.cutout();

    RenderTypeLookup.setRenderLayer(Blocks.HIGH_SPEED_RAIL_OBJ.get(), cutout);
  }

}
