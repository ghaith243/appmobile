package hotel.review.appandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import hotel.review.appandroid.R;

public class ReservationAdapter extends BaseAdapter {

    private final ArrayList<String> reservationList;
    private final ArrayList<Integer> reservationIds;
    private final Context context;

    public ReservationAdapter(Context context, ArrayList<String> reservationList,
                              ArrayList<Integer> reservationIds) {
        this.context = context;
        this.reservationList = reservationList;
        this.reservationIds = reservationIds;
    }

    @Override
    public int getCount() {
        return reservationList.size();
    }

    @Override
    public Object getItem(int position) {
        return reservationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return reservationIds.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reservation_list_item, parent, false);
        }

        TextView reservationDetails = convertView.findViewById(R.id.reservationsTitle);
        String reservationDetail = reservationList.get(position);
        reservationDetails.setText(reservationDetail);

        return convertView;
    }
}
