package org.pure.quickmacro.network.executor;

@SuppressWarnings("unused")
public interface BackgroundExecutor {
	<R> void execute(NetworkCallRunnable<R> runnable);

    <R> void execute(BackgroundCallRunnable<R> runnable);
}
