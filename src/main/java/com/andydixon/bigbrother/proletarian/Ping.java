package com.andydixon.bigbrother.proletarian;

public class Ping extends BaseWorker{

    public int interval = 1;

    public void init() {
        setInterval(2);
    }

    public void execute() {
        System.out.println("Ping!");
    }


}
