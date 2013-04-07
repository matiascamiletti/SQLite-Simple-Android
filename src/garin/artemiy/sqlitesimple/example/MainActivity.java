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

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * author: Artemiy Garin
 * date: 11.12.12
 */
public class MainActivity extends ListActivity {

    private RecordsDAO recordsDAO;
    private Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recordsDAO = new RecordsDAO(this);
        setContentView(R.layout.main_layout);

        cursor = recordsDAO.selectCursorDescFromTable();
        MainCursorAdapter cursorAdapter = new MainCursorAdapter(this, cursor);
        setListAdapter(cursorAdapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                recordsDAO.update(id, generateRecord());
                updateAdapter();
            }
        });

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                recordsDAO.delete(id);
                updateAdapter();
                return true;
            }
        });

        // FTS
        List<FTSModel> ftsModelList = new ArrayList<FTSModel>();
        FTSModel ftsModel = new FTSModel(19, 2, "улица Докукина");
        ftsModelList.add(ftsModel);

        final SQLiteSimpleFTS simpleFTS = new SQLiteSimpleFTS(this, true);
        simpleFTS.createAll(ftsModelList);

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

    private Record generateRecord() {
        Record record = new Record();
        record.setDateOfPublication(GregorianCalendar.getInstance().getTimeInMillis());
        record.setPublished(true);
        return record;
    }

    @SuppressWarnings("unused")
    public void onClickAddRecord(View view) {
        recordsDAO.create(generateRecord());
        updateAdapter();
    }

    @SuppressWarnings("unused")
    public void onClickDeleteAllRecords(View view) {
        recordsDAO.deleteAll();
        updateAdapter();
    }

    @SuppressWarnings("unused")
    public void onClickUpdateLastRow(View view) {
        recordsDAO.update(recordsDAO.getLastRowId(), generateRecord());
        updateAdapter();
    }

}
