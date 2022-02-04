package com.udu.arcfind.resources;

public class ShutdownHook extends Thread {
    public void run() {
        //the stuff below will run when the program ends
        TempStorage.remove();
    }
}
