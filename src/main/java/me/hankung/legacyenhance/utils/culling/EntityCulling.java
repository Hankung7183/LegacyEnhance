package me.hankung.legacyenhance.utils.culling;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.logisticscraft.occlusionculling.OcclusionCullingInstance;

import me.hankung.legacyenhance.utils.culling.tool.CullTask;
import me.hankung.legacyenhance.utils.culling.tool.Provider;

public class EntityCulling {

    public OcclusionCullingInstance culling;

    public CullTask cullTask;
    private Thread cullThread;

    public int renderedBlockEntities = 0;
    public int skippedBlockEntities = 0;
    public int renderedEntities = 0;
    public int skippedEntities = 0;

    public static int tracingDistance = 128;
    public Set<String> blockEntityWhitelist = new HashSet<>(Arrays.asList("tile.beacon"));

    public void init() {
        culling = new OcclusionCullingInstance(tracingDistance, new Provider());
        cullTask = new CullTask(culling, blockEntityWhitelist);

        cullThread = new Thread(cullTask, "CullThread");
        cullThread.setUncaughtExceptionHandler((thread, ex) -> {
            System.out.println("The CullingThread has crashed! Please report the following stacktrace!");
            ex.printStackTrace();
        });
        cullThread.start();
    }

    public void worldTick() {
        cullTask.requestCull = true;
    }
    
    public void clientTick() {
        cullTask.requestCull = true;
    }

}
