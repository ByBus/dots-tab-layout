package host.capitalquiz.dotstablayoutexample

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
    private val fragmentsColorAndText = listOf(
        R.color.blue to "First Fragment",
        R.color.green to "Second Fragment",
        R.color.orange to "Third Fragment",
        R.color.red to "Fourth Fragment",
        R.color.pink to "Fifth Fragment",
    )
    override fun getItemCount(): Int = fragmentsColorAndText.size

    override fun createFragment(position: Int): Fragment {
        val data = fragmentsColorAndText[position]
        return FirstFragment.newInstance(data.first, data.second)
    }

}