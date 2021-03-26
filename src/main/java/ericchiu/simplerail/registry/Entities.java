package ericchiu.simplerail.registry;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.constants.I18n;
import ericchiu.simplerail.entity.LocomotiveCartEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Entities {

  public static final EntityType<LocomotiveCartEntity> LOCOMOTIVE_CART = EntityType.Builder
      .<LocomotiveCartEntity>of(LocomotiveCartEntity::new, EntityClassification.MISC) //
      .sized(0.98F, 0.7F) //
      .clientTrackingRange(8) //
      .setCustomClientFactory(LocomotiveCartEntity::new) //
      .build(I18n.ENTITY_LOCOMOTIVE_CART);

  private static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES,
      SimpleRail.MOD_ID);

  public static void register(IEventBus bus) {
    REGISTER.register(I18n.ENTITY_LOCOMOTIVE_CART, () -> LOCOMOTIVE_CART);

    REGISTER.register(bus);
  }

}
