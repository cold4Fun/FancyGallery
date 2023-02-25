package edu.vt.cs5254.gallery

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.imageLoader
import edu.vt.cs5254.gallery.databinding.
import kotlinx.coroutines.launch


private const val TAG = "PhotoGalleryFragment"

class GalleryFragment : Fragment() {

    var _binding: FragmentGalleryBinding ?= null

    private val vm: MainViewModel by activityViewModels()


    private val binding
        get() = checkNotNull(_binding) {
            "FragmentGalleryBinding is null"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

        requireActivity().addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_gallery, menu)
            }


            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return when (menuItem.itemId) {
                    R.id.reload_menu -> {
                        requireContext().imageLoader.memoryCache?.clear()
                        vm.reloadGalleryItems()
                        true
                    }else ->
                        false

                }
            }

        },
            viewLifecycleOwner
        )
        binding.photoGrid.layoutManager = GridLayoutManager(context, 3)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.galleryItems.collect { items ->
                    binding.photoGrid.adapter = GalleryItemAdapter(items) { photoPageUri ->
                        findNavController().navigate(GalleryFragmentDirections.showPhoto(photoPageUri))
//                        val intent = Intent(Intent.ACTION_VIEW, photoPageUri)
//                        startActivity(intent)
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}