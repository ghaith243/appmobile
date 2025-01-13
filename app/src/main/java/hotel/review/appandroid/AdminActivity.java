package hotel.review.appandroid;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ReservationAdapter reservationAdapter;
    private ArrayList<String> reservationList;
    private ArrayList<Integer> reservationIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize data lists
        reservationList = new ArrayList<>();
        reservationIds = new ArrayList<>();

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.reservationsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reservationAdapter = new ReservationAdapter(reservationList, position -> deleteReservation(position));
        recyclerView.setAdapter(reservationAdapter);

        // Load reservations
        loadReservations();
    }

    private void loadReservations() {
        reservationList.clear();
        reservationIds.clear();

        Cursor cursor = dbHelper.getAllReservations();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range")
                int reservationId = cursor.getInt(cursor.getColumnIndex("id"));

                @SuppressLint("Range")
                String guestName = cursor.getString(cursor.getColumnIndex("guestName"));

                @SuppressLint("Range")
                String checkInDate = cursor.getString(cursor.getColumnIndex("checkInDate"));

                @SuppressLint("Range")
                String checkOutDate = cursor.getString(cursor.getColumnIndex("checkOutDate"));

                String reservationDetails = "Guest: " + guestName + " | Check-in: " + checkInDate + " | Check-out: " + checkOutDate;
                reservationList.add(reservationDetails);
                reservationIds.add(reservationId);
            }
            cursor.close();
        } else {
            Toast.makeText(this, "No reservations found", Toast.LENGTH_SHORT).show();
        }

        reservationAdapter.notifyDataSetChanged();
    }

    private void deleteReservation(int position) {
        int reservationId = reservationIds.get(position);

        boolean isDeleted = dbHelper.deleteReservation(reservationId);

        if (isDeleted) {
            Toast.makeText(this, "Reservation deleted", Toast.LENGTH_SHORT).show();
            loadReservations();
        } else {
            Toast.makeText(this, "Failed to delete reservation", Toast.LENGTH_SHORT).show();
        }
    }
}
