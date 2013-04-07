package garin.artemiy.sqlitesimple.example;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import garin.artemiy.sqlitesimple.R;
import garin.artemiy.sqlitesimple.example.adapter.MainCursorAdapter;
import garin.artemiy.sqlitesimple.example.model.Record;
import garin.artemiy.sqlitesimple.example.operator.RecordsDAO;
import garin.artemiy.sqlitesimple.library.SQLiteSimpleFTS;
import garin.artemiy.sqlitesimple.library.model.FTSModel;

/**
 * author: Artemiy Garin
 * date: 11.12.12
 */
public class MainActivity extends ListActivity {

    private RecordsDAO recordsDAO;
    private Cursor cursor;
    private SQLiteSimpleFTS simpleFTS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recordsDAO = new RecordsDAO(this);
        setContentView(R.layout.main_layout);

        cursor = recordsDAO.selectCursorDescFromTable();
        MainCursorAdapter cursorAdapter = new MainCursorAdapter(this, cursor);
        setListAdapter(cursorAdapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                recordsDAO.delete(id);
                updateAdapter();
                return true;
            }
        });

        simpleFTS = new SQLiteSimpleFTS(this, false);

        EditText ftsEditText = (EditText) findViewById(R.id.ftsEditText);
        ftsEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int count, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int count, int i3) {
                simpleFTS.search(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAdapter();
    }

    private void updateAdapter() {
        MainCursorAdapter cursorAdapter = (MainCursorAdapter) getListAdapter();
        cursorAdapter.changeCursor(recordsDAO.selectCursorDescFromTable());
        cursorAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public void onClickAddRecord(View view) {
        Record record = new Record();
        record.setRecordText(((EditText) findViewById(R.id.recordEditText)).getText().toString());

        if (recordsDAO.createIfNotExist(record, Record.COLUMN_RECORD_TEXT, record.getRecordText()) != 0) {
            simpleFTS.create(new FTSModel(record.getId(), record.getRecordText()));
        }

        updateAdapter();
    }

    @SuppressWarnings("unused")
    public void onClickDeleteAllRecords(View view) {
        recordsDAO.deleteAll();
        updateAdapter();
    }

}
