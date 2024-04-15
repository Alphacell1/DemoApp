package hr.tomislavplaninic.demo.database;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import hr.tomislavplaninic.demo.BuildConfig;

/**
 * Manager class for interacting with Firebase Realtime Database.
 */
public class FirebaseManager {

    private static FirebaseManager instance;
    private final FirebaseDatabase mDatabase;

    private final String TAG;

    private FirebaseManager() {
        mDatabase = FirebaseDatabase.getInstance(BuildConfig.FIREBASE_URL);
        TAG = this.getClass().getSimpleName();
    }

    /**
     * Get an instance of the FirebaseManager.
     *
     * @return An instance of FirebaseManager
     */
    public static synchronized FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    /**
     * Insert a favorite game into the database.
     *
     * @param gameName The name of the game to be inserted
     */
    public void insertFavoriteGame(String gameName) {
        DatabaseReference gamesRef = mDatabase.getReference("favorite_games");
        String gameId = gamesRef.push().getKey();
        gamesRef.child(gameId).setValue(gameName);
    }

    /**
     * Fetch favorite games from the database.
     *
     * @param listener The ValueEventListener to be notified when data changes
     */
    public void fetchFavoriteGames(ValueEventListener listener) {
        DatabaseReference gamesRef = mDatabase.getReference("favorite_games");
        gamesRef.addValueEventListener(listener);
    }

    /**
     * Delete a favorite game from the database.
     *
     * @param gameName The name of the game to be deleted
     */
    public void deleteFavoriteGame(String gameName) {
        DatabaseReference gamesRef = mDatabase.getReference("favorite_games");
        Query query = gamesRef.orderByValue().equalTo(gameName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    childSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error occurred : " + databaseError.getDetails());
            }
        });
    }
}
