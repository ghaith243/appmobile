package hotel.review.appandroid;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ArrayList<String> reservationList;
    private ArrayList<Integer> reservationIds;
    private ReservationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        dbHelper = new DatabaseHelper(this);
        reservationList = new ArrayList<>();
        reservationIds = new ArrayList<>();

        ListView listView = findViewById(R.id.reservationslistview);

        adapter = new ReservationAdapter(this, reservationList, reservationIds);
        listView.setAdapter(adapter);

        loadReservations();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Delete the selected reservation when clicked
            deleteReservation(position);
        });
    }

    private void loadReservations() {
        reservationList.clear();
        reservationIds.clear();

        Cursor cursor = dbHelper.getAllReservations();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int reservationId = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String guestName = cursor.getString(cursor.getColumnIndex("guestName"));
                @SuppressLint("Range") String checkInDate = cursor.getString(cursor.getColumnIndex("checkInDate"));
                @SuppressLint("Range") String checkOutDate = cursor.getString(cursor.getColumnIndex("checkOutDate"));

                String reservationDetails = "Guest: " + guestName + " | Check-in: " + checkInDate + " | Check-out: " + checkOutDate;
                reservationList.add(reservationDetails);
                reservationIds.add(reservationId);
            }
            cursor.close();
        } else {
            Toast.makeText(this, "No reservations found", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
    }

    private void deleteReservation(int position) {
        int reservationId = reservationIds.get(position);
        boolean isDeleted = dbHelper.deleteReservation(reservationId);  // Delete reservation from the database

        if (isDeleted) {
            Toast.makeText(this, "Reservation deleted", Toast.LENGTH_SHORT).show();
            loadReservations();  // Reload reservations after deletion
        } else {
            Toast.makeText(this, "Failed to delete reservation", Toast.LENGTH_SHORT).show();
        }
    }
}
