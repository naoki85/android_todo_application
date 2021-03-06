package com.example.android.sample.todoapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AddTaskFragment.AddTaskListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // リストをレイアウトから探す
        val contentMain = findViewById<ConstraintLayout>(R.id.content_main)
        val listView = contentMain.findViewById<ListView>(R.id.taskList)

        var tasks = fetchTasks(this)

        // アダプターを作成
        val adapter = ArrayAdapter<String>(this,
                R.layout.list_task_row,
                R.id.taskRow,
                tasks)

        // リストにアダプターをセットする
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            tasks.removeAt(position)
            adapter.notifyDataSetChanged()
        }

        fab.setOnClickListener { view ->
            val addTaskFragment = AddTaskFragment()
            addTaskFragment.show(supportFragmentManager, "addTask")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_policy -> {
                val intent = CustomTabsIntent.Builder().build()
                intent.launchUrl(this, Uri.parse(getString(R.string.policy_url)))
                true
            }
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        val dialogView = dialog.dialog
        val taskContent = dialogView.findViewById<EditText>(R.id.addTaskEditText)
        val newTask = taskContent.text.toString()
        insertTasks(this, newTask)
        finish()
        startActivity(intent)
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        // User touched the dialog's negative button
    }
}
