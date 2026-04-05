package com.example.dogadoption.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogadoption.R
import com.example.dogadoption.data.local.entity.FavoriteDogEntity
import com.example.dogadoption.databinding.FragmentFavoritesBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: FavoriteDogAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        binding.buttonBrowseDogs.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        binding.btnBrowseMore.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }

    private fun setupRecyclerView() {
        adapter = FavoriteDogAdapter(
            onEditClicked = { entity -> showEditNotesDialog(entity) },
            onDeleteClicked = { entity -> showDeleteDialog(entity) }
        )
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavorites.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            adapter.submitList(favorites)
            val isEmpty = favorites.isEmpty()
            binding.textEmptyFavorites.visibility = if (isEmpty) View.VISIBLE else View.GONE
            binding.nestedScrollFavorites.visibility = if (isEmpty) View.GONE else View.VISIBLE
            if (!isEmpty) {
                binding.textSavedCount.text = getString(R.string.watchlist_count, favorites.size)
            }
        }
    }

    private fun showEditNotesDialog(entity: FavoriteDogEntity) {
        val inputLayout = TextInputLayout(requireContext())
        val editText = TextInputEditText(requireContext()).apply {
            setText(entity.notes)
            hint = getString(R.string.notes_hint)
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
        }
        inputLayout.addView(editText)
        inputLayout.setPadding(32, 0, 32, 0)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.edit_notes_title))
            .setView(inputLayout)
            .setPositiveButton(getString(R.string.confirm)) { _, _ ->
                val newNotes = editText.text?.toString()?.trim() ?: ""
                viewModel.updateNotes(entity, newNotes)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showDeleteDialog(entity: FavoriteDogEntity) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_favorite_title))
            .setMessage(getString(R.string.delete_favorite_message))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.removeFavorite(entity)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
