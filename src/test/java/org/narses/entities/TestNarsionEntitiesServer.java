package org.narses.entities;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerHandAnimationEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.world.biomes.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.narses.entities.thrownitementity.ThrownItemEntity;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class TestNarsionEntitiesServer {
    public static void main(String[] args) {
        // Initialize server
        MinecraftServer server = MinecraftServer.init();

        // Setup instance
        InstanceContainer instance = MinecraftServer.getInstanceManager().createInstanceContainer();
        instance.setChunkGenerator(new DevelopmentChunkGenerator());

        // Add player events
        MinecraftServer.getGlobalEventHandler()
                .addListener(PlayerLoginEvent.class, (event) -> event.setSpawningInstance(instance))
                .addListener(PlayerSpawnEvent.class, (event) -> event.getPlayer().teleport(new Pos(0, 10, 0)))
                .addListener(PlayerHandAnimationEvent.class, (event) -> {
                    Player player = event.getPlayer();

                    if (event.getHand() == Player.Hand.MAIN) {
                        ThrownItemEntity thrownItemEntity = new ThrownItemEntity(
                                player.getPosition().add(0, 0, 0),
                                ItemStack.of(Material.BLACK_BED),
                                10
                        );

                        MinecraftServer.getSchedulerManager()
                                .buildTask(thrownItemEntity::remove)
                                .delay(Duration.of(10, ChronoUnit.SECONDS))
                                .schedule();


                        thrownItemEntity.setHitEntity((entity) -> {
                            player.sendMessage("Hit entity: " + entity);
                        });

                        thrownItemEntity.setInstance(instance);
                    }
                });


        // start server
        server.start("0.0.0.0", 25565);
    }

    private static class DevelopmentChunkGenerator implements ChunkGenerator {

        @Override
        public void generateChunkData(@NotNull ChunkBatch batch, int chunkX, int chunkZ) {
            for (int x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
                for (int z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                    batch.setBlock(x, 0, z, Block.STONE);
                }
            }
        }

        @Override
        public void fillBiomes(@NotNull Biome[] biomes, int chunkX, int chunkZ) {
            Arrays.fill(biomes, Biome.PLAINS);
        }

        @Override
        public @Nullable List<ChunkPopulator> getPopulators() {
            return null;
        }
    }
}
