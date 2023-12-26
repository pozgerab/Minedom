package com.gertoxq.minedom.util;

import com.gertoxq.minedom.Minedom;
import org.bukkit.Bukkit;

public class ScheduleCleanup {

    private final Runnable main;
    private final long mainDuration;
    private final long mainInterval;
    private final Runnable cleanup;
    private int id;


    public ScheduleCleanup(Runnable main, long mainduration, long maininterval, Runnable cleanup) {
        this.main = main;
        this.cleanup = cleanup;
        this.mainDuration = mainduration;
        this.mainInterval = maininterval;
        run();
    }

    private void run() {
        this.id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Minedom.getPlugin(), this.main, 0, this.mainInterval);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> Bukkit.getScheduler().cancelTask(id), this.mainDuration);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), this.cleanup, this.mainDuration);
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(this.id);
    }

}
