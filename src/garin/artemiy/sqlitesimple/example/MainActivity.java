package garin.artemiy.sqlitesimple.example;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import garin.artemiy.sqlitesimple.R;
import garin.artemiy.sqlitesimple.example.adapters.FTSAdapter;
import garin.artemiy.sqlitesimple.example.adapters.RecordAdapter;
import garin.artemiy.sqlitesimple.example.dao.InternalDAO;
import garin.artemiy.sqlitesimple.example.dao.RecordsDAO;
import garin.artemiy.sqlitesimple.example.models.Record;
import garin.artemiy.sqlitesimple.library.SQLiteSimpleFTS;
import garin.artemiy.sqlitesimple.library.model.FTSModel;
import garin.artemiy.sqlitesimple.library.util.SimpleConstants;

/**
 * author: Artemiy Garin
 * date: 11.12.12
 */
public class MainActivity extends Activity {

    private RecordsDAO recordsDAO;
    private SQLiteSimpleFTS simpleFTS;
    private FTSAdapter ftsAdapter;
    private RecordAdapter recordAdapter;
    private String searchWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        recordsDAO = new RecordsDAO(this);
        simpleFTS = new SQLiteSimpleFTS(this, false);
        ftsAdapter = new FTSAdapter(this, recordsDAO);
        recordAdapter = new RecordAdapter(this);

        ListView recordsListView = (ListView) findViewById(R.id.recordListView);
        recordsListView.setOnItemLongClickListener(new CustomOnLongClickListener());
        recordsListView.setAdapter(recordAdapter);

        ListView ftsListView = (ListView) findViewById(R.id.ftsListView);
        ftsListView.setAdapter(ftsAdapter);

        updateAdapters();

        EditText ftsEditText = (EditText) findViewById(R.id.ftsEditText);
        ftsEditText.addTextChangedListener(new CustomTextWatcher());

        InternalDAO internalDAO = new InternalDAO(this);
        Log.d("Internal database rows:", String.valueOf(internalDAO.getCount()));
    }

    private void updateAdapters() {
        recordAdapter.clear();

        for (Record record : recordsDAO.readAllDesc()) recordAdapter.add(record);
        recordAdapter.notifyDataSetChanged();

        updateFTSAdapter();
    }

    private void updateFTSAdapter() {
        if (searchWord != null) {
            ftsAdapter.clear();

            for (FTSModel ftsModel : simpleFTS.search(searchWord, false)) ftsAdapter.add(ftsModel);

            ftsAdapter.notifyDataSetChanged();
        }
    }

    @SuppressWarnings("unused")
    public void onClickAddRecord(View view) {
        Record record = new Record();

        record.setRecordText(((EditText) findViewById(R.id.recordEditText)).getText().toString());
        long id = recordsDAO.createIfNotExist(record, Record.COLUMN_RECORD_TEXT, record.getRecordText());

        if (id != SimpleConstants.ZERO_RESULT)
            simpleFTS.create(new FTSModel(String.valueOf(id), record.getRecordText()));

        ((EditText) findViewById(R.id.recordEditText)).setText(null);
        updateAdapters();
    }

    @SuppressWarnings("unused")
    public void onClickDeleteAllRecords(View view) {
        recordsDAO.deleteAll();

        simpleFTS.dropTable();
        simpleFTS.createTableIfNotExist(this);

        updateAdapters();
    }

    private class CustomTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int count, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int count, int i3) {
            searchWord = charSequence.toString();
            updateFTSAdapter();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private class CustomOnLongClickListener implements AdapterView.OnItemLongClickListener {
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
            recordsDAO.deleteWhere(Record.COLUMN_ID, String.valueOf(recordAdapter.getItem(i).getId()));
            updateAdapters();
            return true;
        }
    }

}
