package hr.tomislavplaninic.demo;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.List;

import hr.tomislavplaninic.demo.communication.CommunicationHandler;
import hr.tomislavplaninic.demo.game.Game;
import hr.tomislavplaninic.demo.game.GameListener;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = ExampleInstrumentedTest.class.getSimpleName();

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("hr.tomislavplaninic.demo", appContext.getPackageName());
    }

    @Test
    public void testVideoGameFetch() {
        CommunicationHandler communicationHandler = CommunicationHandler.getInstance("https://api.rawg.io");
        communicationHandler.fetchGames("action", 1, 4, new GameListener() {
            @Override
            public void onGamesReceived(List<Game> gameList, int count) {
                assertNull(gameList);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}