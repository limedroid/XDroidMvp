package com.lennon.cn.utill.bean;

public abstract class ToastRunnable implements Runnable {
    private boolean executed;

    public ToastRunnable() {
        executed = false;
    }

    @Override
    public void run() {
        if (executed) {
        } else {
            function();
        }
        executed = true;

    }

    protected abstract void function();
}
