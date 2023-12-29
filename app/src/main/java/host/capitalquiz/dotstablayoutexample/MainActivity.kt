package host.capitalquiz.dotstablayoutexample

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import host.capitalquiz.dotstablayout.DotsTabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pager = findViewById<ViewPager2>(R.id.pager)
        pager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)

        val dotsLayout = findViewById<DotsTabLayout>(R.id.dotstab_layout)
        dotsLayout.attachTo(pager, this)

        findViewById<Button>(R.id.nextPageButton).setOnClickListener {
            pager.currentItem++
        }
    }
}