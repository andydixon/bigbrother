package com.andydixon.bigbrother.proletarian;

public class Pong extends BaseWorker{

    public int interval = 2;

    public void init() {
        setInterval(3);
    }

    public void execute() {
        System.out.println("Pong!");
    }


}
