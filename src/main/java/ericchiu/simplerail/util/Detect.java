package ericchiu.simplerail.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class Detect {

  public static boolean isServer() {
    return FMLEnvironment.dist == Dist.DEDICATED_SERVER;
  }

  public static boolean isClient() {
    return FMLEnvironment.dist == Dist.CLIENT;
  }

}
