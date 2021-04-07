package ericchiu.simplerail.config;

import org.apache.commons.lang3.tuple.Pair;

import ericchiu.simplerail.constants.Config;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class CommonConfig {
  private static final Pair<CommonConfig, ForgeConfigSpec> PAIR = new Builder().configure(CommonConfig::new);

  public static final CommonConfig INSTANCE = PAIR.getLeft();
  public static final ForgeConfigSpec SPEC = PAIR.getRight();

  // config
  public final DoubleValue highSpeedRailMaxSpeed;
  public final BooleanValue onewayRailNeedPower;
  public final BooleanValue onewayRailUsePowerChangeDirection;
  public final IntValue ejectRailTransportDistance;
  public final BooleanValue ejectRailNeedPower;
  public final BooleanValue destoryRailNeedPower;

  public CommonConfig(Builder builder) {
    builder.comment("Simple Rail Settings");

    builder.push("rail");

    builder.push("high_speed_rail");

    highSpeedRailMaxSpeed = builder
        .comment("High speed rail max speed (default: 0.8F, original powered rail speed: 0.4F)")
        .defineInRange(Config.HIGH_SPEED_RAIL_MAX_SPEED, 0.8D, 0.4D, 2.0D);

    builder.pop();

    builder.push("oneway_rail");

    onewayRailNeedPower = builder.comment("Need power to enable oneway rail (default: true)")
        .define(Config.ONEWAY_RAIL_NEED_POWER, true);

    onewayRailUsePowerChangeDirection = builder
        .comment(
            "Use power to change oneway rail direction (default: false)\nIf this is true, it mean needPower is false")
        .define(Config.ONEWAY_RAIL_USE_POWER_CHANGE_DIRECTION, false);

    builder.pop();

    builder.push("eject_rail");

    ejectRailTransportDistance = builder.comment("Eject rail transport distance (default: 3)")
        .defineInRange(Config.EJECT_RAIL_TRANSPORT_DISTANCE, 3, 1, 100);
    ejectRailNeedPower = builder.comment("Need power to enable eject rail (default: true)")
        .define(Config.EJECT_RAIL_NEED_POWER, true);

    builder.pop();

    builder.push("destory_rail");

    destoryRailNeedPower = builder.comment("Need power to enable destory rail (default: false)")
        .define(Config.DESTORY_RAIL_NEED_POWER, false);

    builder.pop();

    builder.pop();
  }
}
