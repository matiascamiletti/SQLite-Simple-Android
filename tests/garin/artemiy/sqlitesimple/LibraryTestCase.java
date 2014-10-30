package garin.artemiy.sqlitesimple;

import android.test.AndroidTestCase;
import android.util.Log;
import garin.artemiy.sqlitesimple.example.dao.RecordsDAO;
import garin.artemiy.sqlitesimple.example.models.Record;
import org.junit.Assert;

import java.util.Date;
import java.util.UUID;

/**
 * Author: Artemiy Garin
 * Date: 03.10.13
 */
public class LibraryTestCase extends AndroidTestCase {

    private static final String TAG = "LibraryTestLog";
    private static final String SPEED_FORMAT = "%s (%s) result: %s ms";

    /**
     * Speed test settings
     */
    private static final int RECORDS_COUNT = 500;

    public void testFunctional() {
        Log.d(TAG, "Functional tests begin");
        Log.d(TAG, "Initialization..");

        /**
         * Init
         */
        RecordsDAO recordsDAO = new RecordsDAO(getContext());
        recordsDAO.deleteAll();

        Assert.assertTrue(recordsDAO.getCount() == 0);

        /**
         * Create Read Update Delete tests
         */

        Log.d(TAG, "Initialization complete!");
        Log.d(TAG, "Check create functions..");

        // Create
        String messageText = UUID.randomUUID().toString();
        Date exampleDate = new Date();

        Record record = new Record();
        record.setRecordText(messageText);
        record.setRecordDate(exampleDate);
        long id = recordsDAO.create(record);

        Assert.assertNotNull(id);
        Assert.assertTrue(recordsDAO.getCount() == 1);

        Log.d(TAG, "Complete!");
        Log.d(TAG, "Check read functions..");

        // Read
        Assert.assertNotNull(recordsDAO.read(id));
        Assert.assertEquals(recordsDAO.read(id).getRecordDate().getTime(), exampleDate.getTime());

        Log.d(TAG, "Complete!");
        Log.d(TAG, "Check update functions..");

        // Update
        String newMessageText = UUID.randomUUID().toString();
        record.setRecordText(newMessageText);
        recordsDAO.update(id, record);

        Record updatedRecord = recordsDAO.read(id);

        Assert.assertFalse(updatedRecord.getRecordText().equals(messageText));

        Log.d(TAG, "Complete!");
        Log.d(TAG, "Check delete functions..");

        // Delete
        recordsDAO.delete(id);

        Assert.assertTrue(recordsDAO.getCount() == 0);

        /**
         * Other functional tests
         */

        Log.d(TAG, "Complete!");
        Log.d(TAG, "Check additional functions..");

        recordsDAO.create(record);
        Assert.assertTrue(recordsDAO.readAllAsc().size() == 1);
        Assert.assertTrue(recordsDAO.readAllDesc().size() == 1);
        Assert.assertNotNull(recordsDAO.readWhere(Record.COLUMN_RECORD_TEXT, newMessageText));
        Assert.assertTrue(recordsDAO.readAllWhere(Record.COLUMN_RECORD_TEXT, newMessageText).size() == 1);

        Log.d(TAG, "Complete!");
        Log.d(TAG, "Functional tests complete.");
        Log.d("", "");

    }

    public void testSpeed() {
        Log.d(TAG, "Speed tests begin");

        long testTimeStart;
        long testTimeEnd;

        RecordsDAO recordsDAO = new RecordsDAO(getContext());
        recordsDAO.deleteAll();

        /**
         * Insert
         */

        testTimeStart = System.currentTimeMillis();

        for (int i = 0; i <= RECORDS_COUNT; i++) {
            Record record = new Record();
            record.setRecordText(UUID.randomUUID().toString());
            recordsDAO.create(record);
        }

        testTimeEnd = System.currentTimeMillis();

        Log.d(TAG, String.format(SPEED_FORMAT, "Insert", RECORDS_COUNT, testTimeEnd - testTimeStart));

        /**
         * Select
         */

        testTimeStart = System.currentTimeMillis();

        recordsDAO.readAllAsc();

        testTimeEnd = System.currentTimeMillis();

        Log.d(TAG, String.format(SPEED_FORMAT, "Select", RECORDS_COUNT, testTimeEnd - testTimeStart));
        Log.d(TAG, "Speed tests complete.");

        recordsDAO.deleteAll();
    }

}
