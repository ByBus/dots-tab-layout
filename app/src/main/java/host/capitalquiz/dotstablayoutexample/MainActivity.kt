package host.capitalquiz.dotstablayoutexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import host.capitalquiz.dotstablayout.DotsTabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pager = findViewById<ViewPager2>(R.id.pager)
        pager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)

        val dotsLayout = findViewById<DotsTabLayout>(R.id.tab_layout_dots)
        dotsLayout.attachTo(pager, this)
    }
}