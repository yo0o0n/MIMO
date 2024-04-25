package com.mimo.android

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // XML 파일에서 정의한 토글 버튼에 대한 참조를 가져옴
        val toggleButton = findViewById<ToggleButton>(R.id.toggleButton)

        // 토글 버튼의 상태 변경 리스너 설정
        toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // 토글 버튼이 선택된 상태일 때 할 일
                Toast.makeText(this@MainActivity, "토글 버튼이 켜졌습니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 토글 버튼이 선택되지 않은 상태일 때 할 일
                Toast.makeText(this@MainActivity, "토글 버튼이 꺼졌습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}