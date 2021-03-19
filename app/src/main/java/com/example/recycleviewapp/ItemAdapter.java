package com.example.recycleviewapp;

import android.content.Context;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Vocab> dictionary;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView vocabWord;
        public Button remCorrect;
        public Vocab storeWord;

        public ViewHolder(View itemView){
            super(itemView);
            vocabWord = (TextView) itemView.findViewById(R.id.vocab_word);
            remCorrect = (Button) itemView.findViewById(R.id.def_button);
        }
    }

    public ItemAdapter(List<Vocab> memo){
        dictionary = memo;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        ViewConfiguration vc = ViewConfiguration.get(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View vocabView = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(vocabView);
        return viewHolder;
    }

    RecyclerView.OnItemTouchListener myItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Vocab word = dictionary.get(position);
        // Set item views based on your views and data model
        TextView textView = holder.vocabWord;
        textView.setText(word.getWord());
        Button button = holder.remCorrect;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View popupView = inflater.inflate(R.layout.popup_def, null);
                TextView defText = (TextView) popupView.findViewById(R.id.definition);
                defText.setText(word.getDef());
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });

    }


    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return dictionary.size();
    }
}
