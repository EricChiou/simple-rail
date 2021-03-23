package ericchiu.simplerail.setup;

import ericchiu.simplerail.registry.Blocks;
import ericchiu.simplerail.registry.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class Registration {

	private static IEventBus BUS = FMLJavaModLoadingContext.get().getModEventBus();

	public static void setup() {
		Items.register(BUS);
		Blocks.register(BUS);
	}

}
