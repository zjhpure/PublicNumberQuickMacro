package org.pure.quickmacro.network.executor;

@SuppressWarnings("unused")
abstract class BackgroundCallRunnable<R> {
    void preExecute() {}

    abstract R runAsync();

    void postExecute(R result) {}
}
