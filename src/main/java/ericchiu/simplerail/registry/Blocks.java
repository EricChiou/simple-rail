package ericchiu.simplerail.registry;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.block.HighSpeedRail;
import ericchiu.simplerail.block.HoldingRail;
import ericchiu.simplerail.block.OnewayForwardRail;
import ericchiu.simplerail.block.OnewayReverseRail;
import ericchiu.simplerail.constants.I18n;

import net.minecraft.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Blocks {

	public static final HighSpeedRail HIGH_SPEED_RAIL = new HighSpeedRail();
	public static final HoldingRail HOLDING_RAIL = new HoldingRail();
	public static final OnewayForwardRail ONEWAY_FORWARD_RAIL = new OnewayForwardRail();
	public static final OnewayReverseRail ONEWAY_REVERSE_RAIL = new OnewayReverseRail();

	private static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS,
			SimpleRail.MOD_ID);

	public static void register(IEventBus bus) {
		REGISTER.register(I18n.BLOCK_HIGH_SPEED_RAIL, () -> HIGH_SPEED_RAIL);
		REGISTER.register(I18n.BLOCK_HOLDING_RAIL, () -> HOLDING_RAIL);
		REGISTER.register(I18n.BLOCK_ONEWAY_FORWARD_RAIL, () -> ONEWAY_FORWARD_RAIL);
		REGISTER.register(I18n.BLOCK_ONEWAY_REVERSE_RAIL, () -> ONEWAY_REVERSE_RAIL);

		REGISTER.register(bus);
	}

}
