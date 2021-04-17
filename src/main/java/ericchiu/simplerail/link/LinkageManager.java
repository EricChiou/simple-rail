package ericchiu.simplerail.link;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.world.server.ServerWorld;

public class LinkageManager {

  private static Map<UUID, ArrayList<UUID>> trains = new ConcurrentHashMap<UUID, ArrayList<UUID>>();

  public static Map<UUID, ArrayList<UUID>> getTrains() {
    return trains;
  }

  public static ArrayList<UUID> getTrainUuid(UUID locomotiveUuid) {
    ArrayList<UUID> trainUuid = trains.get(locomotiveUuid);
    return trainUuid == null ? new ArrayList<UUID>() : trains.get(locomotiveUuid);
  }

  public static UUID getLocomotiveUuid(UUID uuid) {
    for (UUID locomotiveUuid : trains.keySet()) {
      if (locomotiveUuid.equals(uuid)) {
        return locomotiveUuid;
      }

      for (UUID cartUuid : trains.get(locomotiveUuid)) {
        if (cartUuid.equals(uuid)) {
          return locomotiveUuid;
        }
      }
    }

    return null;
  }

  public static void setTrainUuid(UUID locomotiveUuid, ArrayList<UUID> trainUuid) {
    trains.put(locomotiveUuid, trainUuid);
  }

  public static void updateTrain(UUID locomotiveUuid, ArrayList<AbstractMinecartEntity> train) {
    ArrayList<UUID> trainUuid = new ArrayList<UUID>();
    for (AbstractMinecartEntity cart : train) {
      trainUuid.add(cart.getUUID());
    }
    trains.put(locomotiveUuid, trainUuid);
  }

  public static void removeTrain(UUID locomotiveUuid) {
    trains.remove(locomotiveUuid);
  }

  public static boolean checkCartLinkable(ServerWorld serverWorld, UUID uuid) {
    for (UUID locomotiveUuid : trains.keySet()) {
      if (serverWorld.getEntity(locomotiveUuid) == null) {
        trains.remove(locomotiveUuid);
      } else {
        if (locomotiveUuid.equals(uuid)) {
          return false;
        }

        for (UUID cartUuid : trains.get(locomotiveUuid)) {
          if (cartUuid.equals(uuid)) {
            return false;
          }
        }
      }
    }

    return true;
  }

}
