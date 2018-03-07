package com.navinfo.dongfeng.terminal.comm.common;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorUtil {

	public static boolean isShutdown(Executor executor) {
		if (executor instanceof ExecutorService) {
			if (((ExecutorService) executor).isShutdown()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Shuts down the specified executors.
	 */
	public static void terminate(Executor... executors) {
		// Check nulls.
		if (executors == null) {
			throw new NullPointerException("executors");
		}

		Executor[] executorsCopy = new Executor[executors.length];
		for (int i = 0; i < executors.length; i++) {
			if (executors[i] == null) {
				throw new NullPointerException("executors[" + i + "]");
			}
			executorsCopy[i] = executors[i];
		}

		// Shut down all executors.
		boolean interrupted = false;
		for (Executor e : executorsCopy) {
			if (!(e instanceof ExecutorService)) {
				continue;
			}

			ExecutorService es = (ExecutorService) e;
			for (;;) {
				try {
					es.shutdownNow();
				} catch (SecurityException ex) {
					// Running in a restricted environment - fall back.
					try {
						es.shutdown();
					} catch (SecurityException ex2) {
						// Running in a more restricted environment.
						// Can't shut down this executor - skip to the next.
						break;
					} catch (NullPointerException ex2) {
						// Some JDK throws NPE here, but shouldn't.
					}
				} catch (NullPointerException ex) {
					// Some JDK throws NPE here, but shouldn't.
				}

				try {
					if (es.awaitTermination(100, TimeUnit.MILLISECONDS)) {
						break;
					}
				} catch (InterruptedException ex) {
					interrupted = true;
				}
			}
		}

		if (interrupted) {
			Thread.currentThread().interrupt();
		}
	}

}
