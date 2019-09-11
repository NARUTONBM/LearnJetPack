package com.demo.learnjetpack;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author narut.
 * @Date 2019-09-11.
 * @Time 22:56.
 */
public class NewWordActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.demo.learnjetpack.REPLY";
    private EditText mEditWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditWord = findViewById(R.id.edit_word);
        findViewById(R.id.button_save).setOnClickListener(v -> {
            Intent intent = new Intent();
            if (TextUtils.isEmpty(mEditWord.getText())) {
                setResult(RESULT_CANCELED, intent);
            } else {
                String newWord = mEditWord.getText().toString();
                intent.putExtra(EXTRA_REPLY, newWord);
                setResult(RESULT_OK, intent);
            }
            finish();
        });
    }
}
