package me.hankung.legacyenhance.utils.culling.tool;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;

import com.logisticscraft.occlusionculling.OcclusionCullingInstance;
import com.logisticscraft.occlusionculling.util.Vec3d;

import me.hankung.legacyenhance.LegacyEnhance;
import me.hankung.legacyenhance.utils.culling.EntityCulling;
import me.hankung.legacyenhance.utils.culling.interfaces.ICullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class CullTask implements Runnable {

    public boolean requestCull = false;

    private final OcclusionCullingInstance culling;
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final int sleepDelay = Integer.parseInt(LegacyEnhance.CONFIG.performanceEntityCullingInterval.get());
    private final int hitboxLimit = 50;
    private final Set<String> unCullable;
    public long lastTime = 0;

    // reused preallocated vars
    private Vec3d lastPos = new Vec3d(0, 0, 0);
    private Vec3d aabbMin = new Vec3d(0, 0, 0);
    private Vec3d aabbMax = new Vec3d(0, 0, 0);

    public CullTask(OcclusionCullingInstance culling, Set<String> unCullable) {
        this.culling = culling;
        this.unCullable = unCullable;
    }

    @Override
    public void run() {
        while (client != null) {
            try {
                Thread.sleep(sleepDelay);

                if (LegacyEnhance.CONFIG.performanceEntityCullingEnabled.get() && client.world != null
                        && client.player != null && client.player.ticksAlive > 10 && client.getCameraEntity() != null) {
                    net.minecraft.util.math.Vec3d cameraMC = null;
                    cameraMC = getCameraPos();
                    if (requestCull
                            || !(cameraMC.x == lastPos.x && cameraMC.y == lastPos.y && cameraMC.z == lastPos.z)) {
                        long start = System.currentTimeMillis();
                        requestCull = false;
                        lastPos.set(cameraMC.x, cameraMC.y, cameraMC.z);
                        com.logisticscraft.occlusionculling.util.Vec3d camera = lastPos;
                        culling.resetCache();
                        boolean noCulling = client.player.isSpectator() || client.options.perspective != 0;
                        Iterator<BlockEntity> iterator = client.world.blockEntities.iterator();
                        BlockEntity entry;
                        while (iterator.hasNext()) {
                            try {
                                entry = iterator.next();
                            } catch (NullPointerException | ConcurrentModificationException ex) {
                                break;
                            }
                            if (unCullable.contains(entry.getBlock().getTranslationKey())) {
                                continue;
                            }
                            ICullable cullable = (ICullable) entry;
                            if (!cullable.isForcedVisible()) {
                                if (noCulling) {
                                    cullable.setCulled(false);
                                    continue;
                                }
                                BlockPos pos = entry.getPos();
                                if (pos.squaredDistanceTo(cameraMC.x, cameraMC.y, cameraMC.z) < 64 * 64) {
                                    aabbMin.set(pos.getX(), pos.getY(), pos.getZ());
                                    aabbMax.set(pos.getX() + 1d, pos.getY() + 1d, pos.getZ() + 1d);
                                    boolean visible = culling.isAABBVisible(aabbMin, aabbMax, camera);
                                    cullable.setCulled(!visible);
                                }

                            }
                        }
                        Entity entity = null;
                        Iterator<Entity> iterable = client.world.getLoadedEntities().iterator();
                        while (iterable.hasNext()) {
                            try {
                                entity = iterable.next();
                            } catch (NullPointerException | ConcurrentModificationException ex) {
                                break;
                            }
                            if (entity == null || !(entity instanceof ICullable)) {
                                continue;
                            }
                            ICullable cullable = (ICullable) entity;
                            if (!cullable.isForcedVisible()) {
                                if (noCulling || isSkippableArmorstand(entity)) {
                                    cullable.setCulled(false);
                                    continue;
                                }
                                if (entity.getPos().squaredDistanceTo(cameraMC) > EntityCulling.tracingDistance * EntityCulling.tracingDistance) {
                                    cullable.setCulled(false);
                                    continue;
                                }
                                Box boundingBox = entity.getBoundingBox();
                                if (boundingBox.maxX - boundingBox.minX > hitboxLimit
                                        || boundingBox.maxY - boundingBox.minY > hitboxLimit
                                        || boundingBox.maxZ - boundingBox.minZ > hitboxLimit) {
                                    cullable.setCulled(false);
                                    continue;
                                }
                                aabbMin.set(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
                                aabbMax.set(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
                                boolean visible = culling.isAABBVisible(aabbMin, aabbMax, camera);
                                cullable.setCulled(!visible);
                            }
                        }
                        lastTime = (System.currentTimeMillis() - start);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LegacyEnhance.LOGGER.info("Shutting down culling task!");
    }

    private net.minecraft.util.math.Vec3d getCameraPos() {
        if (client.options.perspective == 0) {
            return client.getCameraEntity().getCameraPosVec(0);
        }
        return client.getCameraEntity().getCameraPosVec(0);
    }

    private boolean isSkippableArmorstand(Entity entity) {
        if (LegacyEnhance.CONFIG.performanceEntityCullingCAR.get())
            return false;
        return entity instanceof ArmorStandEntity && ((ArmorStandEntity) entity).shouldShowName();
    }
}
