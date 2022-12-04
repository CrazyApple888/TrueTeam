package ru.nsu.alphacontest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import ru.nsu.alphacontest.R
import ru.nsu.alphacontest.login.ui.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            add<LoginFragment>(
                containerViewId = R.id.fragment_container
            )
        }
    }

}