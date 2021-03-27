package ericchiu.simplerail.registry;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.constants.I18n;
import ericchiu.simplerail.entity.LocomotiveCartEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Entities {

  private static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES,
      SimpleRail.MOD_ID);

  public static final RegistryObject<EntityType<LocomotiveCartEntity>> LOCOMOTIVE_CART = REGISTER.register(
      I18n.ENTITY_LOCOMOTIVE_CART,
      () -> EntityType.Builder.<LocomotiveCartEntity>of(LocomotiveCartEntity::new, EntityClassification.MISC) //
          .setCustomClientFactory(LocomotiveCartEntity::new) //
          .sized(0.98F, 0.7F) //
          .clientTrackingRange(8) //
          .build(I18n.ENTITY_LOCOMOTIVE_CART));

  public static void register(IEventBus bus) {
    REGISTER.register(bus);
  }

}
