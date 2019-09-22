package com.demo.learnjetpack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.learnjetpack.persistence.Word;

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
    private ClickListener mClickListener;

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

    Word getCurrentWord(int position) {
        return mWordList.get(position);
    }

    @Override
    public int getItemCount() {
        return mWordList != null ? mWordList.size() : 0;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {

        private final TextView wordItem;

        WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordItem = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(v -> mClickListener.onItemClick(v, getAdapterPosition()));
        }
    }

    void setonClickListener(ClickListener listener) {
        mClickListener = listener;
    }

    public interface ClickListener {
        /**
         * rv item 的点击事件
         *
         * @param view     被点击的 item
         * @param position 被点击 item 的索引
         */
        void onItemClick(View view, int position);
    }
}
