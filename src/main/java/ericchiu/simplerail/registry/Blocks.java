package ericchiu.simplerail.registry;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.block.DestoryRail;
import ericchiu.simplerail.block.EjectRail;
import ericchiu.simplerail.block.HighSpeedRail;
import ericchiu.simplerail.block.HoldingRail;
import ericchiu.simplerail.block.OnewayRail;
import ericchiu.simplerail.block.TimerHoldingRail;
import ericchiu.simplerail.block.SignalTimerBlock;
import ericchiu.simplerail.block.TrainDispenserBlock;
import ericchiu.simplerail.constants.I18n;

import net.minecraft.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Blocks {

	private static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS,
			SimpleRail.MOD_ID);

	// rail
	public static final RegistryObject<Block> HIGH_SPEED_RAIL = REGISTER.register(I18n.BLOCK_HIGH_SPEED_RAIL,
			() -> new HighSpeedRail());
	public static final RegistryObject<Block> HOLDING_RAIL = REGISTER.register(I18n.BLOCK_HOLDING_RAIL,
			() -> new HoldingRail());
	public static final RegistryObject<Block> ONEWAY_RAIL = REGISTER.register(I18n.BLOCK_ONEWAY_RAIL,
			() -> new OnewayRail());
	public static final RegistryObject<Block> EJECT_RAIL = REGISTER.register(I18n.BLOCK_EJECT_RAIL,
			() -> new EjectRail());
	public static final RegistryObject<Block> DESTORY_RAIL = REGISTER.register(I18n.BLOCK_DESTORY_RAIL,
			() -> new DestoryRail());
	public static final RegistryObject<Block> TIMER_HOLDING_RAIL = REGISTER.register(I18n.BLOCK_TIMER_HOLDING_RAIL,
			() -> new TimerHoldingRail());

	// machine
	public static final RegistryObject<Block> TRAIN_DISPENSER = REGISTER.register(I18n.BLOCK_TRAIN_DISPENSER,
			() -> new TrainDispenserBlock());
	public static final RegistryObject<Block> SIGNAL_TIMER = REGISTER.register(I18n.BLOCK_SIGNAL_TIMER,
			() -> new SignalTimerBlock());

	public static void register(IEventBus bus) {
		REGISTER.register(bus);
	}

}
