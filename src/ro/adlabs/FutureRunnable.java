package ro.adlabs;

import java.util.concurrent.Future;

/**
 * Created by Danny on 7/12/2016.
 */
abstract class FutureRunnable implements Runnable {

    private Future<?> future;

    public Future<?> getFuture() {
        return future;
    }

    public void setFuture(Future<?> future) {
        this.future = future;
    }
}
