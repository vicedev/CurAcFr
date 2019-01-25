package com.vice.currentactivityfragment

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.vice.curacfrlib.CurAcFr
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_jump_Second.setOnClickListener {
            startActivity(Intent(MainActivity@ this, SecondActivity::class.java))
        }

        btn_jump_fragment_first.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fl_content, FirstFragment()).commit()
        }

        btn_jump_fragment_second.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fl_content, SecondFragment()).commit()
        }

        btn_show_float.setOnClickListener {
            CurAcFr.show()
        }
        btn_hide_float.setOnClickListener {
            CurAcFr.hide()
        }
    }
}
