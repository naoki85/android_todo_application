package com.example.android.sample.todoapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DB_NAME = "todo_application"
private const val DB_VERSION = 1

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        // テーブルの作成
        db?.execSQL("CREATE TABLE tasks ( " +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " name TEXT NOT NULL, " +
                " created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                " updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

fun fetchTasks(context: Context) : MutableList<String> {
    // 読み込み用のデータベースを開く
    val database = DatabaseHelper(context).readableDatabase
    // データベースから全件検索する
    val cursor = database.query(
            "tasks", null, null, null, null, null, "created_at DESC")

    val tasks = mutableListOf<String>()
    cursor.use {
        // カーソルで順次処理していく
        while (cursor.moveToNext()) {
            // 保存されているタスクを得る
            val task_name = cursor.getString(cursor.getColumnIndex("name"))
            tasks.add(task_name)
        }
    }
    database.close()
    return tasks
}

fun insertTasks(context: Context, name: String) {
    // 書き込み可能なデータベースを開く
    val database = DatabaseHelper(context).writableDatabase

    database.use { db ->
        // 挿入するレコード
        val record = ContentValues().apply {
            put("name", name)
        }
        // データベースに挿入する
        db.insert("tasks", null, record)
    }
}