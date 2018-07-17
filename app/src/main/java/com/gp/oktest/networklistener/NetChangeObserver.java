package com.gp.oktest.networklistener;

public interface NetChangeObserver {
    void onConnect(NetworkType type);
    void onDisConnect();
}
