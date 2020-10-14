package se.warting.roboelectricinsertwithonconflict

import android.app.Activity
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import se.warting.roboelectricinsertwithonconflict.databinding.ActivityMainBinding


class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        testMinusOne()

    }


    fun testMinusOne() {
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
        val shouldBeMinusOne =
            database.insertWithOnConflict("foo", null, args, SQLiteDatabase.CONFLICT_IGNORE)

        Toast.makeText(
            this,
            "shouldBeOne: $shouldBeOne shouldBeMinusOne: $shouldBeMinusOne",
            Toast.LENGTH_LONG
        ).show()
    }
}