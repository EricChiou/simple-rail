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
  // high_speed_rail
  public final DoubleValue highSpeedRailMaxSpeed;
  // oneway_rail
  public final BooleanValue onewayRailNeedPower;
  public final BooleanValue onewayRailUsePowerChangeDirection;
  // eject_rail
  public final IntValue ejectRailTransportDistance;
  public final BooleanValue ejectRailNeedPower;
  // destory_rail
  public final BooleanValue destoryRailNeedPower;
  // timer_holding_rail
  public final IntValue timerHoldingRailLv1;
  public final IntValue timerHoldingRailLv2;
  public final IntValue timerHoldingRailLv3;
  public final IntValue timerHoldingRailLv4;
  public final IntValue timerHoldingRailLv5;
  public final IntValue timerHoldingRailLv6;
  public final IntValue timerHoldingRailLv7;
  public final IntValue timerHoldingRailLv8;
  public final IntValue timerHoldingRailLv9;
  // singnal_timer_block
  public final IntValue singnalTimerBlockLv1;
  public final IntValue singnalTimerBlockLv2;
  public final IntValue singnalTimerBlockLv3;
  public final IntValue singnalTimerBlockLv4;
  public final IntValue singnalTimerBlockLv5;
  public final IntValue singnalTimerBlockLv6;
  public final IntValue singnalTimerBlockLv7;
  public final IntValue singnalTimerBlockLv8;
  public final IntValue singnalTimerBlockLv9;

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

    builder.push("timer_holding_rail");

    timerHoldingRailLv1 = builder.comment("Timer Holding Rail lv 1 wait time (default: 5s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV1, 5, 0, 2147483647);
    timerHoldingRailLv2 = builder.comment("Timer Holding Rail lv 2 wait time (default: 10s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV2, 10, 0, 2147483647);
    timerHoldingRailLv3 = builder.comment("Timer Holding Rail lv 3 wait time (default: 15s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV3, 15, 0, 2147483647);
    timerHoldingRailLv4 = builder.comment("Timer Holding Rail lv 4 wait time (default: 20s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV4, 20, 0, 2147483647);
    timerHoldingRailLv5 = builder.comment("Timer Holding Rail lv 5 wait time (default: 25s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV5, 25, 0, 2147483647);
    timerHoldingRailLv6 = builder.comment("Timer Holding Rail lv 6 wait time (default: 30s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV6, 30, 0, 2147483647);
    timerHoldingRailLv7 = builder.comment("Timer Holding Rail lv 7 wait time (default: 40s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV7, 40, 0, 2147483647);
    timerHoldingRailLv8 = builder.comment("Timer Holding Rail lv 8 wait time (default: 50s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV8, 50, 0, 2147483647);
    timerHoldingRailLv9 = builder.comment("Timer Holding Rail lv 9 wait time (default: 60s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV9, 60, 0, 2147483647);

    builder.pop();

    builder.push("singnal_timer_block");

    singnalTimerBlockLv1 = builder.comment("Singnal Timer Block lv 1 wait time (default: 5s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV1, 5, 0, 2147483647);
    singnalTimerBlockLv2 = builder.comment("Singnal Timer Block lv 2 wait time (default: 10s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV2, 10, 0, 2147483647);
    singnalTimerBlockLv3 = builder.comment("Singnal Timer Block lv 3 wait time (default: 15s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV3, 15, 0, 2147483647);
    singnalTimerBlockLv4 = builder.comment("Singnal Timer Block lv 4 wait time (default: 20s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV4, 20, 0, 2147483647);
    singnalTimerBlockLv5 = builder.comment("Singnal Timer Block lv 5 wait time (default: 25s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV5, 25, 0, 2147483647);
    singnalTimerBlockLv6 = builder.comment("Singnal Timer Block lv 6 wait time (default: 30s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV6, 30, 0, 2147483647);
    singnalTimerBlockLv7 = builder.comment("Singnal Timer Block lv 7 wait time (default: 40s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV7, 40, 0, 2147483647);
    singnalTimerBlockLv8 = builder.comment("Singnal Timer Block lv 8 wait time (default: 50s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV8, 50, 0, 2147483647);
    singnalTimerBlockLv9 = builder.comment("Singnal Timer Block lv 9 wait time (default: 60s)")
        .defineInRange(Config.TIMER_HOLDING_RAIL_LV9, 60, 0, 2147483647);

    builder.pop();

    builder.pop();
  }
}
