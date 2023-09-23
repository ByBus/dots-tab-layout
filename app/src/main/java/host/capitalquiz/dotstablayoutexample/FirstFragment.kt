package host.capitalquiz.dotstablayoutexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.base_text)
        textView.text = arguments?.getString(TEXT) ?: ""
        textView.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                arguments?.getInt(BG_COLOR) ?: 0
            )
        )
    }

    companion object {
        private const val BG_COLOR = "base_bg_color"
        private const val TEXT = "base_fragment_text"
        fun newInstance(@ColorRes color: Int, text: String): Fragment {
            return FirstFragment().apply {
                arguments = bundleOf(BG_COLOR to color, TEXT to text)
            }
        }
    }
}