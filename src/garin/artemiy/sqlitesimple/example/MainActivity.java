package garin.artemiy.sqlitesimple.example;

import android.app.ListActivity;
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

import java.util.List;

/**
 * author: Artemiy Garin
 * date: 11.12.12
 */
public class MainActivity extends ListActivity {

    private RecordsDAO recordsDAO;
    private SQLiteSimpleFTS simpleFTS;
    private MainFTSAdapter mainFTSAdapter;

    private static final String EMPTY = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recordsDAO = new RecordsDAO(this);
        simpleFTS = new SQLiteSimpleFTS(this, false);
        mainFTSAdapter = new MainFTSAdapter(this, recordsDAO);

        setContentView(R.layout.main_layout);

        final MainAdapter mainAdapter = new MainAdapter(this);
        setListAdapter(mainAdapter);
        updateAdapter();

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                recordsDAO.deleteWhere(Record.COLUMN_ID, String.valueOf(mainAdapter.getItem(i).getId()));
                updateAdapter();
                return true;
            }
        });

        ListView ftsList = (ListView) findViewById(R.id.ftsList);
        ftsList.setAdapter(mainFTSAdapter);

        EditText ftsEditText = (EditText) findViewById(R.id.ftsEditText);
        ftsEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int count, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int count, int i3) {
                mainFTSAdapter.clear();
                mainFTSAdapter.notifyDataSetChanged();

                List<FTSModel> ftsModelList = simpleFTS.search(charSequence.toString(), false);
                for (FTSModel ftsModel : ftsModelList) {
                    mainFTSAdapter.add(ftsModel);
                }

                mainFTSAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recordsDAO.recycle();
        simpleFTS.recycle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAdapter();
    }

    private void updateAdapter() {
        MainAdapter mainAdapter = (MainAdapter) getListAdapter();
        mainAdapter.clear();

        for (Record record : recordsDAO.readAllDesc()) {
            mainAdapter.add(record);
        }

        mainAdapter.notifyDataSetChanged();
        mainFTSAdapter.notifyDataSetChanged();

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
        updateAdapter();
    }

    @SuppressWarnings("unused")
    public void onClickDeleteAllRecords(View view) {
        recordsDAO.deleteAll();
        updateAdapter();
    }

}
