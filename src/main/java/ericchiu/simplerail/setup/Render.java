package ericchiu.simplerail.setup;

import ericchiu.simplerail.entity.LocomotiveCartEntity;
import ericchiu.simplerail.registry.Blocks;
import ericchiu.simplerail.render.LocomotiveCartRender;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class Render {

  public static void setup() {
    RenderType cutout = RenderType.cutout();

    RenderTypeLookup.setRenderLayer(Blocks.HIGH_SPEED_RAIL, cutout);
    RenderTypeLookup.setRenderLayer(Blocks.HOLDING_RAIL, cutout);
    RenderTypeLookup.setRenderLayer(Blocks.ONEWAY_RAIL, cutout);
    RenderTypeLookup.setRenderLayer(Blocks.EJECT_RAIL, cutout);

    RenderingRegistry.registerEntityRenderingHandler(LocomotiveCartEntity.class,
        renderManager -> new LocomotiveCartRender(renderManager));
  }

}
