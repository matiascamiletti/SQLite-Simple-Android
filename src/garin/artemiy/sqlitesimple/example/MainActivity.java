package garin.artemiy.sqlitesimple.example;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import garin.artemiy.sqlitesimple.R;
import garin.artemiy.sqlitesimple.example.adapter.MainAdapter;
import garin.artemiy.sqlitesimple.example.adapter.MainFTSAdapter;
import garin.artemiy.sqlitesimple.example.model.Record;
import garin.artemiy.sqlitesimple.example.operator.RecordsDAO;
import garin.artemiy.sqlitesimple.library.SQLiteSimpleFTS;
import garin.artemiy.sqlitesimple.library.model.FTSModel;
import garin.artemiy.sqlitesimple.library.util.SimpleConstants;

/**
 * author: Artemiy Garin
 * date: 11.12.12
 */
public class MainActivity extends Activity {

    private static final String EMPTY = "";
    private RecordsDAO recordsDAO;
    private SQLiteSimpleFTS simpleFTS;
    private MainFTSAdapter mainFTSAdapter;
    private MainAdapter mainAdapter;
    private String searchWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        recordsDAO = new RecordsDAO(this);
        simpleFTS = new SQLiteSimpleFTS(this, false);
        mainFTSAdapter = new MainFTSAdapter(this, recordsDAO);
        mainAdapter = new MainAdapter(this);

        ListView mainList = (ListView) findViewById(R.id.mainList);
        mainList.setOnItemLongClickListener(new CustomOnLongClickListener());
        mainList.setAdapter(mainAdapter);

        ListView ftsList = (ListView) findViewById(R.id.ftsList);
        ftsList.setAdapter(mainFTSAdapter);

        updateAdapters();

        EditText ftsEditText = (EditText) findViewById(R.id.ftsEditText);
        ftsEditText.addTextChangedListener(new CustomTextWatcher());
    }

    private void updateAdapters() {
        mainAdapter.clear();

        for (Record record : recordsDAO.readAllDesc()) {
            mainAdapter.add(record);
        }

        mainAdapter.notifyDataSetChanged();

        updateFTSAdapter();
    }

    private void updateFTSAdapter() {
        if (searchWord != null) {
            mainFTSAdapter.clear();

            for (FTSModel ftsModel : simpleFTS.search(searchWord, false)) {
                mainFTSAdapter.add(ftsModel);
            }

            mainFTSAdapter.notifyDataSetChanged();
        }
    }

    @SuppressWarnings("unused")
    public void onClickAddRecord(View view) {
        Record record = new Record();

        record.setRecordText(((EditText) findViewById(R.id.recordEditText)).getText().toString());
        long id = recordsDAO.createIfNotExist(record, Record.COLUMN_RECORD_TEXT, record.getRecordText());

        if (id != SimpleConstants.ZERO_RESULT) {
            simpleFTS.create(new FTSModel(String.valueOf(id), record.getRecordText()));
        }

        ((EditText) findViewById(R.id.recordEditText)).setText(EMPTY);
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
            recordsDAO.deleteWhere(Record.COLUMN_ID, String.valueOf(mainAdapter.getItem(i).getId()));
            updateAdapters();
            return true;
        }
    }

}
