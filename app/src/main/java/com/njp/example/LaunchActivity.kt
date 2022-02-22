package com.njp.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        startActivity(
            Intent(this@LaunchActivity, MainActivity::class.java).apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("issues://JakeWharton/hugo/issues")

                putExtras(
                    bundleOf("key_intent_test_current_time" to System.currentTimeMillis())
                )
            }
        )

        finish()

    }
}