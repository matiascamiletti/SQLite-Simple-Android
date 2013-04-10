package garin.artemiy.sqlitesimple.example.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import garin.artemiy.sqlitesimple.R;
import garin.artemiy.sqlitesimple.example.model.Record;
import garin.artemiy.sqlitesimple.example.operator.RecordsDAO;

/**
 * author: Artemiy Garin
 * date: 22.12.2012
 */
public class MainCursorAdapter extends CursorAdapter {

    private RecordsDAO recordsDAO;

    @SuppressWarnings("deprecation")
    public MainCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        recordsDAO = new RecordsDAO(context);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(R.layout.record_item, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Record record = recordsDAO.read(cursor);

        TextView recordText = (TextView) view.findViewById(R.id.recordTextView);

        recordText.setText(String.valueOf(record.getRecordText()));
    }

}
