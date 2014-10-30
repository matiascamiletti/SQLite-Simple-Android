package garin.artemiy.sqlitesimple.example.adapters;

import android.annotation.SuppressLint;
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
public class RecordAdapter extends ArrayAdapter<Record> {

    private Context context;

    public RecordAdapter(Context context) {
        super(context, R.layout.item_record);
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_record, null);

        Record record = getItem(position);
        TextView recordText = (TextView) convertView.findViewById(R.id.recordTextView);

        recordText.setText(record.getRecordText());

        return convertView;
    }

}
