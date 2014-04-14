package garin.artemiy.sqlitesimple.example.adapters;

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
@SuppressWarnings("CanBeFinal")
public class FTSAdapter extends ArrayAdapter<FTSModel> {

    private Context context;
    private RecordsDAO recordsDAO;

    public FTSAdapter(Context context, RecordsDAO recordsDAO) {
        super(context, R.layout.item_record);
        this.context = context;
        this.recordsDAO = recordsDAO;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_record, null);
        }

        FTSModel ftsModel = getItem(position);
        TextView recordText = (TextView) convertView.findViewById(R.id.recordTextView);
        Record record = recordsDAO.readWhere(Record.COLUMN_ID, ftsModel.getId());

        if (record != null) recordText.setText(record.getRecordText());

        return convertView;
    }

}
