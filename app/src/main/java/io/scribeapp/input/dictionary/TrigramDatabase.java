package io.scribeapp.input.dictionary;

import java.util.ArrayList;
import java.util.Arrays;

import io.scribeapp.input.Utils;
import io.scribeapp.classifier.model.LabelClassificationResult.Label;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import javax.inject.Inject;

/**
 * Klasa umożliwia dostęp do listy trigramów zapisanych w bazie danych.
 * 
 * <p>
 * See also the following files:
 * <ul>
 * <li>TrigramDbHelper.java</li>
 * </ul>
 */
public class TrigramDatabase {
	private static final String TAG = "TrigramDatabase";

	private TrigramDbHelper dbHelper;
	private SQLiteDatabase database;

    @Inject
	public TrigramDatabase(Context c) {
		dbHelper = new TrigramDbHelper(c);
		database = dbHelper.getWritableDatabase();
	}

	/**
	 * Dodaje trigram do bazy lub aktualizuje częstotliwość występowania, jeśli już istnieje
	 * @param trigram
	 */
	public void addTrigram(String trigram) {
		Cursor cursor = database.query(TrigramDbHelper.TABLE_GRAMS, TrigramDbHelper.ALL_COLUMNS, TrigramDbHelper.COLUMN_GRAM + "=?", new String[] { trigram }, null, null, null);
		Log.v("Add Word", "TRIGRAM: " + trigram);
		if (cursor.getCount() == 0) {
			Log.v("Add trigram", "No trigram found");
			ContentValues values = new ContentValues();
			values.put(TrigramDbHelper.COLUMN_GRAM, trigram);
			values.put(TrigramDbHelper.COLUMN_FREQUENCY, 0);
			database.insert(TrigramDbHelper.TABLE_GRAMS, null, values);
		}
		else {
			cursor.moveToFirst();
			ContentValues values = new ContentValues();
			int freq = cursor.getInt(cursor.getColumnIndexOrThrow(TrigramDbHelper.COLUMN_FREQUENCY));
			values.put(TrigramDbHelper.COLUMN_FREQUENCY, freq + 1);
			values.put(TrigramDbHelper.COLUMN_GRAM, trigram);

			Log.v("Add trigram", "Trigram exists Freq: " + freq);
			database.update(TrigramDbHelper.TABLE_GRAMS, values, TrigramDbHelper.COLUMN_GRAM + "=?", new String[] { trigram });
		}
		cursor.close();
	}

	/**
	 * Zwraca listę obiektów Label. Reprezentują one możliwe zakończenia podanego prefiksu wraz z prawdopodobieństwem wystąpienia takiej sekwencji
	 * @param prefix
	 * @return
	 */
	public ArrayList<Label> getSuggestions(String prefix) {
		Log.d(TAG, "Suggest");
		int denom = computeNormalizer();
		Cursor cursor = database.query(TrigramDbHelper.TABLE_GRAMS, TrigramDbHelper.ALL_COLUMNS, TrigramDbHelper.COLUMN_GRAM + " LIKE ?", new String[] { prefix + "%" }, null, null, DictionaryDbHelper.COLUMN_FREQUENCY + " DESC");
		ArrayList<Label> result = new ArrayList<Label>();
		if (cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				float p = ((float) cursor.getInt(cursor.getColumnIndex(TrigramDbHelper.COLUMN_FREQUENCY))) / denom;
				char c = cursor.getString(cursor.getColumnIndex(TrigramDbHelper.COLUMN_GRAM)).charAt(2);
				if (Arrays.binarySearch(Utils.LETTERS, c) >= 0) result.add(new Label(c, p));
			}
		}
		cursor.close();
		return result;
	}

	/**
	 * Zamyka powiązaną bazę danych
	 */
	public void close() {
		database.close();
	}

	/**
	 * Oblicza sumę częstotliwości wszystkich trigramów, aby później znormalizować prawdopodobieństwo
	 * @return
	 */
	private int computeNormalizer() {
		Cursor cursor = database.rawQuery("SELECT SUM(" + TrigramDbHelper.COLUMN_FREQUENCY + ") FROM " + TrigramDbHelper.TABLE_GRAMS, null);
		cursor.moveToFirst();
		if (cursor.getCount() == 1) return cursor.getInt(0);
		return 0;
	}
}
