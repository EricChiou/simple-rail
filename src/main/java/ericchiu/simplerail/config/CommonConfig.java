package ericchiu.simplerail.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

public class CommonConfig {
  private static final Pair<CommonConfig, ForgeConfigSpec> PAIR = new Builder().configure(CommonConfig::new);

  public static final CommonConfig INSTANCE = PAIR.getLeft();
  public static final ForgeConfigSpec SPEC = PAIR.getRight();

  // config
  public final DoubleValue highSpeedRailMaxSpeed;

  public CommonConfig(Builder builder) {
    builder.comment("Simple Rail Settings");

    builder.push("rail");
    highSpeedRailMaxSpeed = builder.comment("High speed rail max speed (default: 0.8f, original powered rail speed: 0.4f)")
        .defineInRange("highSpeedRailMaxSpeed", 0.8D, 0.4D, 2.0D);
    builder.pop();
  }
}
