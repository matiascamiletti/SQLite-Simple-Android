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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author: Artemiy Garin
 * date: 22.12.2012
 */
public class MainCursorAdapter extends CursorAdapter {

    private static final String DATE_FORMAT_PATTERN = "HH:mm:ss dd/MM/yyyy";

    @SuppressWarnings("deprecation")
    public MainCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
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
        RecordsDAO recordsDAO = new RecordsDAO(context);
        Record record = recordsDAO.read(cursor);
        TextView publicationDate = (TextView) view.findViewById(R.id.publicationDate);

        Date date = new Date();
        date.setTime(record.getDateOfPublication());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);

        publicationDate.setText(simpleDateFormat.format(date));
    }
}
