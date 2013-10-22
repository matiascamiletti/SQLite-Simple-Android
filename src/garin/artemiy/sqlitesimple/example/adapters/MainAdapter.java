package garin.artemiy.sqlitesimple.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import garin.artemiy.sqlitesimple.R;
import garin.artemiy.sqlitesimple.example.models.Record;

/**
 * Author: Artemiy Garin
 * Date: 07.04.13
 */
@SuppressWarnings("CanBeFinal")
public class MainAdapter extends ArrayAdapter<Record> {

    private Context context;

    public MainAdapter(Context context) {
        super(context, R.layout.record_item);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        if (convertView != null) {
            view = convertView;
        } else {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.record_item, null);
        }

        Record record = getItem(position);
        TextView recordText = (TextView) view.findViewById(R.id.recordTextView);

        recordText.setText(record.getRecordText());

        return view;
    }

}
