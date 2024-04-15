package hr.tomislavplaninic.demo;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import hr.tomislavplaninic.demo.communication.CommunicationHandler;
import hr.tomislavplaninic.demo.game.Game;
import hr.tomislavplaninic.demo.game.GameListener;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testVideoGameFetch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        CommunicationHandler communicationHandler = CommunicationHandler.getInstance("https://api.rawg.io");
        communicationHandler.fetchGames("action", 1, 4, new GameListener() {
            @Override
            public void onGamesReceived(List<Game> gameList, int count) {
                assertNotNull(gameList);
                latch.countDown();
            }

            @Override
            public void onError(String message) {
            }
        });

        latch.await();
    }

}