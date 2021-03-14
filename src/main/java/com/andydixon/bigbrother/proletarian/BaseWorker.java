package com.andydixon.bigbrother.proletarian;

import java.util.logging.Logger;

public abstract class BaseWorker implements Runnable {
    /**
     * Modulus of seconds for the worker to be triggered
     */
    private int interval = -1;
    private static Logger log;
    private String proleName;

    public final void run() {
        proleName = this.getClass().getName();
        log = Logger.getLogger(proleName);
        long unixTime = System.currentTimeMillis() / 1000L;
        /**
         * Despite this runs in the main thread, this is a failsafe check
         */
        if (unixTime%interval == 0) {
            this.prestart();
            this.execute();
            this.cleanup();
        }

    }

    /**
     * Tasks that run before the main thread
     */
    public void prestart() {
    }

    /**
     * The main code that is the prole thread is responsible for
     */
    public void execute() {
        System.out.println("execute method not defined in "+this.getClass().getName());
    }
    /**
    Tasks to be run after the main thread code has been executed
     */
    public void cleanup(){

    }

    /**
     * Get the interval for this prole in seconds
     * @return
     */
    public int getInterval() {
        if (interval==-1) interval=60;
        return interval;
    }

    /**
     * Set the interval for this prole to work (seconds)
     * @param value
     */
    protected void setInterval(int value) {
        interval=value;
    }

    /**
     * Used to do stuff at the point of registry instantiation, like using setInterval(secs)
     */
    public void init() {
        setInterval(-1); // -1 defaults to 60 seconds, but is useful for seeing if this has been unconfigured
    }
}
