package com.andydixon.bigbrother;

import com.andydixon.bigbrother.proletarian.BaseWorker;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        log.info("BigBrother Starting..");

        HashMap<String, Integer> classRegistry = new HashMap<>();

        Reflections reflections = new Reflections("com.andydixon.bigbrother.proletarian");
        Set<Class<? extends BaseWorker>> classes = reflections.getSubTypesOf(BaseWorker.class);

        String classList = "";
        for (Class<? extends BaseWorker> aClass : classes) {
            //Get interval for the class then add it to the registry
            try {
                BaseWorker prole = aClass.getDeclaredConstructor().newInstance();
                prole.init();
                classRegistry.put(prole.getClass().getName(), prole.getInterval());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                log.severe(aClass.getName() + " Unable to add to registry: " + e.getMessage());
            }
        }
        log.info("Registered prole classes:\n" + classRegistry);

        Threadkeeper threadKeeper = new Threadkeeper();

        while (true) {
            classRegistry.forEach((className, interval) -> {
                long unixTime = System.currentTimeMillis() / 1000L;
                if (unixTime % interval == 0) {
                    if (threadKeeper.isntRunning(className)) {
                        Class<?> prole = null;
                        try {
                            prole = Class.forName(className);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            threadKeeper.startThread(className, (BaseWorker) prole.getDeclaredConstructor().newInstance());
                        } catch (InstantiationException e) {
                            log.severe(className + " instantiation failed. " + e.getMessage());
                        } catch (IllegalAccessException e) {
                            log.severe(className + " instantiation failed (Illegal access Exception). " + e.getMessage());
                        } catch (NoSuchMethodException e) {
                            log.severe(className + " Missing Declared Constructor. " + e.getMessage());
                        } catch (InvocationTargetException e) {
                            log.severe(className + " Invocation Target Error. " + e.getMessage());
                        }
                    }
                }

            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.warning("Something interrupted the sleep");
            }
        }

    }
}
