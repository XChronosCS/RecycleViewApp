package com.example.recycleviewapp;

import android.content.ClipData;
import android.content.Context;
import android.gesture.Gesture;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

@SuppressWarnings("deprecation")
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Vocab> dictionary;

    public ItemAdapter(List<Vocab> memo) {
        dictionary = memo;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        ViewConfiguration vc = ViewConfiguration.get(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View vocabView = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(context, vocabView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Vocab word = dictionary.get(position);
        holder.setStoreWord(word);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView vocabWord;
        public Button remCorrect;
        private Context context;

        public void setStoreWord(Vocab storeWord) {
            this.storeWord = storeWord;
        }

        public Vocab storeWord;


        public ViewHolder(Context context, View itemView) {
            super(itemView);
            vocabWord = (TextView) itemView.findViewById(R.id.vocab_word);
            remCorrect = (Button) itemView.findViewById(R.id.def_button);
            this.context = context;
            itemView.setOnTouchListener(new OnSwipeTouchListener(this.context) {
                @Override
                public void onSwipeRight() {
                    super.onSwipeRight();
                    int position = getAdapterPosition();
                    if(position != Adapter.NO_SELECTION){
                        dictionary.remove(position);
                        ItemAdapter.this.notifyItemRemoved(position);
                    }
                    Log.i("Swipe Right", "Swipe right works correctly,");
                }

                @Override
                public void onLongClick(){
                    super.onLongClick();
                    Context context = itemView.getContext();
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View popupView = inflater.inflate(R.layout.popup_edit, null);
                    EditText editName = (EditText) popupView.findViewById(R.id.editname);
                    EditText editDesc = (EditText) popupView.findViewById(R.id.editdesc);
                    editName.setText(storeWord.getWord());
                    editDesc.setText(storeWord.getDef());
                    Button enterButton = (Button) popupView.findViewById(R.id.enter_edit);
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true;
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    popupWindow.showAtLocation(itemView, Gravity.CENTER, 0, 0);
                    enterButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = getAdapterPosition();
                            String wordName = editName.getText().toString();
                            String wordDef = editDesc.getText().toString();
                            storeWord.setDef(wordDef);
                            storeWord.setWord(wordName);
                            ItemAdapter.this.notifyItemChanged(position);
                            popupWindow.dismiss();
                        }
                    });
                }
            });
        }

    }

}