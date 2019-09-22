package com.demo.learnjetpack;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.demo.learnjetpack.persistence.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.demo.learnjetpack.NewWordActivity.EXTRA_REPLY;
import static com.demo.learnjetpack.NewWordActivity.EXTRA_REPLY_ID;

/**
 * @author narut
 */
@SuppressLint("CheckResult")
public class MainActivity extends AppCompatActivity implements WordListAdapter.ClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String UPDATE_WORD = "update_word";
    public static final String UPDATE_WORD_ID = "update_word_id";
    private WordViewModel mViewModel;
    private WordListAdapter mAdapter;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        RecyclerView rvItem = findViewById(R.id.rv_item);
        mAdapter = new WordListAdapter(this);
        mAdapter.setonClickListener(this);
        rvItem.setAdapter(mAdapter);
        rvItem.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Word currentWord = mAdapter.getCurrentWord(position);
                Toast.makeText(MainActivity.this, String.format("Deleting the \"%s\".", currentWord.getWord()),
                        Toast.LENGTH_SHORT).show();
                mViewModel.deleteWord(currentWord)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(integer -> Log.d(TAG, String.format("删除了%d行的数据", integer)));
            }
        });
        touchHelper.attachToRecyclerView(rvItem);

        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(WordViewModel.class);
        //mViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        //mViewModel.getAllWords().observe(this, words -> mAdapter.setWords(words));
        mViewModel.getAllWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(words -> mAdapter.setWords(words));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data) {
            Toast.makeText(this, "Clearing the data...", Toast.LENGTH_SHORT).show();
            mViewModel.deleteAll();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(EXTRA_REPLY));
            if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE) {
                mViewModel.insertWord(word)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aLong -> Log.d(TAG, String.format("在第%d行插入一条新数据", aLong)));
            } else {
                long wordId = data.getLongExtra(EXTRA_REPLY_ID, -1);
                if (wordId != -1) {
                    mViewModel.updateWord(new Word(wordId, word.getWord()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(aLong -> Log.d(TAG, String.format("修改了%d行的数据", aLong)));
                } else {
                    Toast.makeText(this, "Failed to update.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Word currentWord = mAdapter.getCurrentWord(position);
        Intent intent = new Intent(this, NewWordActivity.class);
        intent.putExtra(UPDATE_WORD, currentWord.getWord());
        intent.putExtra(UPDATE_WORD_ID, currentWord.getId());
        startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }
}
