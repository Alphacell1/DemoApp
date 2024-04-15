package hr.tomislavplaninic.demo.communication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor to retry failed HTTP requests.
 */
public class RetryInterceptor implements Interceptor {
    private static final int MAX_RETRIES = 4;
    private int retryCount = 0;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = null;

        while (retryCount < MAX_RETRIES) {
            try {
                response = chain.proceed(request);
                if (response.isSuccessful()) {
                    return response;
                }
                response.close();
            } catch (IOException e) {
                retryCount++;
                if (retryCount >= MAX_RETRIES) {
                    throw e;
                }
            }
        }

        throw new IOException("Max retries exceeded");
    }
}
