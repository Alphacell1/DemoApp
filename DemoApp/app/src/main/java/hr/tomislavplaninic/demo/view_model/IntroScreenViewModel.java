package hr.tomislavplaninic.demo.view_model;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.lifecycle.ViewModel;

import com.airbnb.lottie.LottieAnimationView;

import hr.tomislavplaninic.demo.activities.GenreSelectionActivity;
import hr.tomislavplaninic.demo.activities.VideoGameActivity;

/**
 * ViewModel for managing logic related to the intro screen.
 */
public class IntroScreenViewModel extends ViewModel {

    private boolean isReturningUser;

    /**
     * Get whether the user is returning or not.
     *
     * @return True if the user is returning, false otherwise.
     */
    public boolean getIsReturningUser() {
        return isReturningUser;
    }

    /**
     * Set whether the user is returning or not.
     *
     * @param returningUser True if the user is returning, false otherwise.
     */
    public void setIsReturningUser(boolean returningUser) {
        isReturningUser = returningUser;
    }

    /**
     * Check internet connectivity and navigate to appropriate screen.
     *
     * @param context Context of the calling activity.
     */
    public void checkInternetAndNavigate(Context context) {
        if (isNetworkAvailable(context)) {
            Intent intent;
            if (isReturningUser) {
                intent = new Intent(context, VideoGameActivity.class);
            } else {
                intent = new Intent(context, GenreSelectionActivity.class);
            }
            context.startActivity(intent);
        } else {
            // Handle no internet connection
        }
    }

    /**
     * Start the animation on the specified LottieAnimationView.
     *
     * @param context        Context of the calling activity.
     * @param animationView The LottieAnimationView to animate.
     */
    public void startAnimation(Context context, LottieAnimationView animationView) {
        float endPosition = context.getResources().getDisplayMetrics().widthPixels;
        ObjectAnimator animator = ObjectAnimator.ofFloat(animationView, "translationX", -(endPosition / 2), (endPosition / 2) + 5);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    /**
     * Check if network connectivity is available.
     *
     * @param context Context of the calling activity.
     * @return True if network connectivity is available, false otherwise.
     */
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
