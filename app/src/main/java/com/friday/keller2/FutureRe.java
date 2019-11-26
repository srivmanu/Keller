package com.friday.keller2;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

/**
 * Created By srivmanu on 11/25/2019 for Keller 2
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */
public class FutureRe extends CompletableFuture<Response> implements Callback {

    @Override
    public void onFailure(@NotNull final Call call, @NotNull final IOException e) {
        super.completeExceptionally(e);
    }

    @Override
    public void onResponse(@NotNull final Call call, @NotNull final Response response) throws IOException {
        super.complete(response);
    }
}
