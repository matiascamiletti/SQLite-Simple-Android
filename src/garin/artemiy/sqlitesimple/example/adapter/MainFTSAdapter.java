package garin.artemiy.sqlitesimple.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import garin.artemiy.sqlitesimple.R;
import garin.artemiy.sqlitesimple.example.dao.RecordsDAO;
import garin.artemiy.sqlitesimple.example.models.Record;
import garin.artemiy.sqlitesimple.library.model.FTSModel;

/**
 * Author: Artemiy Garin
 * Date: 07.04.13
 */
public class MainFTSAdapter extends ArrayAdapter<FTSModel> {

    private Context context;
    private RecordsDAO recordsDAO;

    public MainFTSAdapter(Context context, RecordsDAO recordsDAO) {
        super(context, R.layout.record_item);
        this.context = context;
        this.recordsDAO = recordsDAO;
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

        FTSModel ftsModel = getItem(position);
        TextView recordText = (TextView) view.findViewById(R.id.recordTextView);
        Record record = recordsDAO.readWhere(Record.COLUMN_ID, ftsModel.getId());

        if (record != null)
            recordText.setText(record.getRecordText());

        return view;
    }

}
