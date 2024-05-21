package com.loci.mbtitest

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class TestActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    val questionnaireResults = QuestionnaireResults()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(this)
        viewPager.isUserInputEnabled = false

    }

    fun moveToNextQuestion() {
        if (viewPager.currentItem == 3) {
            //마지막페이지 -> 결과화면으로이동
            val intent = Intent(this, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("results", ArrayList(questionnaireResults.results))
            startActivity(intent)

        } else {
            val nextItem = viewPager.currentItem + 1
            if (nextItem < (viewPager.adapter?.itemCount ?: 0)) {
                viewPager.setCurrentItem(nextItem, true)
            }
        }
    }


}

class QuestionnaireResults {
    val results = mutableListOf<Int>()

    fun addResponse(response: List<Int>) {
        val mostFrequent = response.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        mostFrequent?.let { results.add(it) }
    }

}