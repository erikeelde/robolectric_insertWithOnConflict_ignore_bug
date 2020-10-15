package se.warting.roboelectricinsertwithonconflict

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(minSdk = 16, maxSdk = 28)
class RoomUnitTest {
    @Test
    fun addition_isCorrect() {
        val database = SQLiteDatabase.create(null)
        database.execSQL(
                "CREATE TABLE foo (\n" +
                        "\tbar INTEGER PRIMARY KEY\n" +
                        ");"
        );
        val beforeInsert: Cursor = database.rawQuery("SELECT bar FROM foo", null)

        beforeInsert.close()

        val args = ContentValues()
        args.put("bar", 1)

        val shouldBeOne =
                database.insertWithOnConflict("foo", null, args, SQLiteDatabase.CONFLICT_IGNORE)

        assertEquals(1, shouldBeOne)

        val shouldBeMinusOne =
                database.insertWithOnConflict("foo", null, args, SQLiteDatabase.CONFLICT_IGNORE)

        assertEquals(-1, shouldBeMinusOne)
    }

}