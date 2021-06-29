package ericchiu.simplerail.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.entity.LocomotiveCartEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;

public class ChunkEventManager {

  private static final Logger LOGGER = LogManager.getLogger();

  private static final int CHUNK_RADIUS = 2;

  public static void run(EnteringChunk event) {
    forceChunkLoading(event);
  }

  private static void forceChunkLoading(EnteringChunk event) {
    Entity entity = event.getEntity();
    if (entity instanceof LocomotiveCartEntity) {
      if (!entity.level.isClientSide) {
        ServerWorld world = entity.level.getServer().getLevel(entity.level.dimension());
        BlockPos blockPos = entity.blockPosition();
        int chunkX = blockPos.getX() >> 4;
        int chunkZ = blockPos.getZ() >> 4;

        loadChunks(world, entity, chunkX, chunkZ, CHUNK_RADIUS);
      }
    }
  }

  private static void loadChunks(ServerWorld world, Entity entity, int chunkX, int chunkZ, int radius) {
    for (int x = chunkX - radius; x <= chunkX + radius; x++) {
      for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {
        try {
          ForgeChunkManager.forceChunk(world, SimpleRail.MOD_ID, entity, chunkX, chunkZ, true, true);
        } catch (Exception e) {
          LOGGER.error(e);
        }
      }
    }
  }

}
