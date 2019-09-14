package com.demo.learnjetpack;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.demo.learnjetpack.MainActivity.UPDATE_WORD;
import static com.demo.learnjetpack.MainActivity.UPDATE_WORD_ID;

/**
 * @author narut.
 * @Date 2019-09-11.
 * @Time 22:56.
 */
public class NewWordActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_REPLY = "com.demo.learnjetpack.REPLY";
    public static final String EXTRA_REPLY_ID = "com.demo.learnjetpack.REPLY_ID";
    private EditText mEditWord;
    private Bundle mBundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditWord = findViewById(R.id.edit_word);

        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            String word = mBundle.getString(UPDATE_WORD, "");
            if (!word.isEmpty()) {
                mEditWord.setText(word);
                mEditWord.setSelection(word.length());
                mEditWord.requestFocus();
            }
        }

        findViewById(R.id.button_save).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (TextUtils.isEmpty(mEditWord.getText())) {
            // 未输入新单词/未修改
            setResult(RESULT_CANCELED, intent);
        } else {
            String newWord = mEditWord.getText().toString();
            intent.putExtra(EXTRA_REPLY, newWord);
            if (mBundle != null && mBundle.containsKey(UPDATE_WORD_ID)) {
                long wordId = mBundle.getLong(UPDATE_WORD_ID, -1);
                if (wordId != -1) {
                    // 将修改的数据的索引添加到返回 intent
                    intent.putExtra(EXTRA_REPLY_ID, wordId);
                }
            }
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}
