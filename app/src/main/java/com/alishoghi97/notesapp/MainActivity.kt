package com.alishoghi97.notesapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SwitchCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alishoghi97.notesapp.adpters.NotesAdapter
import com.alishoghi97.notesapp.models.NoteEntity
import com.alishoghi97.notesapp.utils.SwipeToDeleteCallBack
import com.alishoghi97.notesapp.viewmodel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotesAdapter
    private lateinit var fab: FloatingActionButton
    @SuppressLint("UseSwitchCompatOrMaterialCode", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        val toolbar :  androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        navigationDrawerOpener(toolbar)
        searchNoteFunction()
    }
    // Search Query on Note Content and Title
    private fun searchNoteFunction() {
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle submission if needed
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                noteViewModel.searchNotes("%$newText%").observe(this@MainActivity) { notes ->
                    adapter.setDataList(notes)
                }
                return true
            }
        })
    }
    // This function implements the Navigation drawer Menu
    @SuppressLint("InflateParams")
    private fun navigationDrawerOpener(toolbar: androidx.appcompat.widget.Toolbar) {
        val drawerly: DrawerLayout = findViewById(R.id.drawerLayout)
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.menu_set -> {
                    val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.About ->{
                    val intent = Intent(this, AboutActivity::class.java)
                    startActivity(intent)

                    true
                }
                // Add other menu item cases as needed
                else -> false // Return false for items you don't handle here
            }
        }
        val switchItem = navigationView.menu.findItem(R.id.nightmode_switch)
        if (switchItem != null) {
            // Inflate the custom switch layout
            val customSwitchView =
                LayoutInflater.from(this).inflate(R.layout.menu_switch, null) as View
            switchItem.setActionView(customSwitchView)
            // Find the Switch inside the custom layout
            drawerSwitch(customSwitchView)
        }
        val toggle = ActionBarDrawerToggle(
            this,
            drawerly,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerly.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()
    }
    //Dark Mode Functionality in Switch in DrawerMenu
    private fun drawerSwitch(customSwitchView: View) {
        val customSwitch = customSwitchView.findViewById<SwitchCompat>(R.id.switch_for_dark)

        // Set the initial state based on the current night mode

        customSwitch.setOnClickListener {
            if (customSwitch.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            // Update the switch state after changing night mod
        }
    }
    // Initialize necessary components
    private fun init() {
        bindview()
        recyclerviewLayoutChanger()
        adapter = NotesAdapter(this)
        recyclerView.adapter = adapter
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.getAllData().observe(this){
            adapter.setDataList(it)
        }

        val addNewNoteResultActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    addNoteFunction(result)
                }
            }

        fab.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            addNewNoteResultActivity.launch(intent)
        }



        val swipeHandler = object : SwipeToDeleteCallBack(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                MainScope().launch {
                    noteViewModel.delete(adapter.getNote(viewHolder.adapterPosition))
                    Toast.makeText(this@MainActivity, "Note item deleted", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        val editNoteResultActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    editNoteFunction(result)
                }
            }

        adapter.setOnItemClickListener(object : NotesAdapter.OnItemClickListener {
            override fun onItemClick(note:NoteEntity) {
                val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
                intent.putExtra(AddNoteActivity.EXTRA_TITLE, note.title)
                intent.putExtra(AddNoteActivity.EXTRA_CONTENT, note.content)
                intent.putExtra(AddNoteActivity.EXTRA_ID, note.id)
                editNoteResultActivity.launch(intent)
            }
        })


    }
    // Update the note when edited
    private fun editNoteFunction(result: ActivityResult) {
        val data = result.data ?: return
        val id = data.getIntExtra(AddNoteActivity.EXTRA_ID, -1)
        if (id == -1) {
            Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT).show()
            return
        }
        val title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE)
        val content = data.getStringExtra(AddNoteActivity.EXTRA_CONTENT)
        val todo =
            NoteEntity(title = title!!, content = content!!, id = id)
        MainScope().launch { noteViewModel.update(todo) }
        Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show()
    }
    // Add a new note to the database
    private fun addNoteFunction(result: ActivityResult) {
        val data = result.data ?: return
        val title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE)
        val content = data.getStringExtra(AddNoteActivity.EXTRA_CONTENT)
        val todo = NoteEntity(title = title!!, content = content!!)
        MainScope().launch { noteViewModel.insert(todo) }
        Toast.makeText(this, "new Note added", Toast.LENGTH_SHORT).show()
    }
    // Change the layout of the RecyclerView based on user preference
    private fun recyclerviewLayoutChanger() {
        val layoutmanagerList = LinearLayoutManager(this)
        val layoutManagerGrid = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        // recyclerView.layoutManager = layoutManager
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val switchprf = prefs.getBoolean("switch", true)
        // Set RecyclerView layout based on user preference
        if (switchprf) {
            recyclerView.layoutManager = layoutManagerGrid
        } else {
            recyclerView.layoutManager = layoutmanagerList
        }
    }

    private fun bindview() {

        fab =  findViewById(R.id.fabAddNote)
        recyclerView = findViewById(R.id.recyclerView)
    }
}