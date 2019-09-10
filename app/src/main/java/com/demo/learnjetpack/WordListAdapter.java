package com.demo.learnjetpack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.learnjetpack.room.Word;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author narut.
 * @Date 2019-09-10.
 * @Time 23:55.
 */
public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private List<Word> mWordList;

    public WordListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_rv, parent, false);

        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if (mWordList != null) {
            Word word = mWordList.get(position);
            holder.wordItem.setText(word.getWord());
        } else {
            holder.wordItem.setText(mContext.getString(R.string.value_no_word));
        }
    }

    void setWords(List<Word> wordList) {
        mWordList = wordList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mWordList != null ? mWordList.size() : 0;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {

        private final TextView wordItem;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordItem = itemView.findViewById(R.id.textView);
        }
    }
}
