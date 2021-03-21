package com.andydixon.bigbrother.resources;

public abstract class BaseResource {

    String name="Unnamed Resource";
    int keepAliveTimeout = -1;

    /**
     * The keepAlive code
     */
    public void keepAlive() {
        System.out.println("No keepalive defined in "+this.getClass().getName());
    }
    /**
    Cleanup tasks, if required
     */
    public void cleanup(){

    }

    /**
     * Get the interval for this prole in seconds
     * @return
     */
    public int getKeepAliveTimeout() {
        if (keepAliveTimeout==-1) keepAliveTimeout=30000;
        return keepAliveTimeout;
    }

    /**
     * Set the interval for this prole to work (seconds)
     * @param value
     */
    protected void setKeepAliveTimeout(int value) {
        keepAliveTimeout=value;
    }

    /**
     * Used to do stuff at the point of registry instantiation, like using setInterval(secs)
     */
    public void init() {
        setKeepAliveTimeout(-1); // -1 defaults to 60 seconds, but is useful for seeing if this has been unconfigured
    }
}
