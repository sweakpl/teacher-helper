package com.sweak.teacherhelper.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.database.entity.Note
import com.sweak.teacherhelper.databinding.FragmentNoteBinding
import com.sweak.teacherhelper.ui.activity.AddEditNoteActivity
import com.sweak.teacherhelper.ui.adapter.recyclerview.NoteAdapter
import com.sweak.teacherhelper.ui.viewmodel.NoteViewModel
import com.sweak.teacherhelper.ui.viewmodel.NoteViewModelFactory

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val getNewNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->
        var toastMessage: String = getString(R.string.note_not_saved)

        if (result.resultCode == RESULT_OK) {
            val title: String? = result.data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
            val description: String? = result.data?.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)

            if (title != null && description != null) {
                noteViewModel.insert(Note(title, description))
                toastMessage = getString(R.string.note_saved)
            }
        }

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }

    private val getEditedNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->
        var toastMessage: String = getString(R.string.note_not_edited)

        if (result.resultCode == RESULT_OK) {
            val id: Int? = result.data?.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)
            val title: String? = result.data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
            val description: String? = result.data?.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)

            if (id != null && title != null && description != null) {
                if (id != -1) {
                    val note = Note(title, description)
                    note.id = id
                    noteViewModel.update(note)

                    toastMessage = getString(R.string.note_updated)
                }
            }
            else
                toastMessage = getString(R.string.cant_update_note)
        }

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteViewModel = ViewModelProvider(this,
            NoteViewModelFactory(requireActivity().application))
            .get(NoteViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        val root = binding.root

        prepareNoteRecyclerView()
        prepareAddEditNoteButton()
        setViewModelDataObserver()

        return root
    }

    private fun prepareNoteRecyclerView() {
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(context)

        noteAdapter = NoteAdapter(object : NoteAdapter.OptionsMenuClickListener {
            override fun onOptionsMenuClicked(position: Int) {
                showOptionsMenu(position)
            }
        })

        binding.recyclerViewNotes.adapter = noteAdapter
    }

    private fun showOptionsMenu(position: Int) {
        val popupMenu = PopupMenu(requireContext(),
            binding.recyclerViewNotes[position].findViewById(R.id.text_view_student_activity_options))
        popupMenu.inflate(R.menu.edit_delete_menu)

        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.delete -> {
                        noteViewModel.delete(noteAdapter.getNoteAt(position))
                        return true
                    }
                    R.id.edit -> {
                        val note: Note = noteAdapter.getNoteAt(position)
                        val intent = Intent(context, AddEditNoteActivity::class.java)
                        intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.id)
                        intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.title)
                        intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.description)
                        getEditedNote.launch(intent)
                        return true
                    }
                }
                return false
            }
        })

        popupMenu.show()
    }

    private fun prepareAddEditNoteButton() {
        binding.buttonAddNote.setOnClickListener {
            val intent = Intent(context, AddEditNoteActivity::class.java)
            getNewNote.launch(intent)
        }
    }

    private fun setViewModelDataObserver() {
        noteViewModel.allNotes.observe(viewLifecycleOwner, { notes ->
            noteAdapter.setNotes(notes)

            binding.textViewEmptyIndicator.visibility =
                if (notes.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}