package com.andydixon.bigbrother;

import com.andydixon.bigbrother.proletarian.BaseWorker;

import java.util.*;
import java.util.logging.Logger;

public class Threadkeeper {

    HashMap<String, Thread> threadPool = new HashMap<>();
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public Threadkeeper() {

        // Start the reaper thread

        Thread reaperThread = new Thread(() -> {
            while (true) {
                List<String> killList = new ArrayList<>();

                threadPool.forEach((key, thread) -> {
                    if (!thread.isAlive()) {
                        killList.add(key);
                    }
                });
                killList.forEach((thread) -> {
                    threadPool.remove(thread);
                    //log.info("Reaper removed " + thread + " as the thread is no longer alive.");
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        reaperThread.start();
        log.info("Reaper thread initialised.");
    }

    public void startThread(String threadName,BaseWorker newThread) {
        threadPool.put(threadName,new Thread(newThread));
        threadPool.get(threadName).start();
    }

    public boolean has(String threadName) {
        return threadPool.containsKey(threadName);
    }

    public boolean isntRunning(String threadName) {
        return !has(threadName);
    }


}
