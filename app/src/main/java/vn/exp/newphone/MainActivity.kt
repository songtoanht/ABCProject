package vn.exp.newphone

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import vn.exp.newphone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var databinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        replaceFragment()
    }

    private fun replaceFragment() {
        val mainFragment = MainFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.fr_content, mainFragment, "simple")
                .addToBackStack(null)
                .commit()
    }
}
