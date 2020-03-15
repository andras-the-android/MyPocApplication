package com.example.andras.myapplication;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DragDropActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop);
        View source = findViewById(R.id.source);
        View target1 = findViewById(R.id.target1);
        View target2 = findViewById(R.id.target2);
        View target3 = findViewById(R.id.target3);

        MyLongClickListener longClickListener = new MyLongClickListener();
        source.setOnLongClickListener(longClickListener);
        target1.setOnLongClickListener(longClickListener);
        target2.setOnLongClickListener(longClickListener);
        target3.setOnLongClickListener(longClickListener);

        source.setOnDragListener(new MyDragListener(findViewById(R.id.sourceTw)));
        target1.setOnDragListener(new MyDragListener(findViewById(R.id.target1Tw)));
        target2.setOnDragListener(new MyDragListener(findViewById(R.id.target2Tw)));
        target3.setOnDragListener(new MyDragListener(findViewById(R.id.target3Tw)));

    }

    private class MyLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {

            if (v instanceof ImageView && ((ImageView) v).getDrawable() != null) {
                String id = String.valueOf(v.getId());
                ClipData clipData = ClipData.newPlainText(id, id);

                v.startDrag(clipData, new View.DragShadowBuilder(v), null, 0);
                return true;
            }
            return false;
        }
    }

    private class MyDragListener implements View.OnDragListener {

        private TextView textView;

        public MyDragListener(TextView  textView) {
            this.textView = textView;
        }

        @Override
        public boolean onDrag(View view, DragEvent event) {
            String logItem = " event: " + toStringDragEvent(event.getAction()) + "----" + event;
            textView.setText(logItem);
            Log.d(getLocalClassName(), view.getTag() + logItem);

            ImageView imageView = (ImageView) view;

            switch(event.getAction()) {

                case DragEvent.ACTION_DRAG_STARTED:

                    // Determines if this View can accept the dragged data
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                        imageView.setColorFilter(Color.BLUE);
                        imageView.setBackgroundColor(Color.BLUE);

                        // Invalidate the view to force a redraw in the new tint
                        imageView.invalidate();

                        // returns true to indicate that the View can accept the dragged data.
                        return true;

                    }

                    // Returns false. During the current drag and drop operation, this View will
                    // not receive events again until ACTION_DRAG_ENDED is sent.
                    return false;

                case DragEvent.ACTION_DRAG_ENTERED:
                    imageView.setColorFilter(Color.GREEN);
                    imageView.setBackgroundColor(Color.GREEN);
                    imageView.invalidate();
                    return true;

                case DragEvent.ACTION_DRAG_LOCATION:
                    // Ignore the event
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:
                    imageView.setColorFilter(Color.BLUE);
                    imageView.setBackgroundColor(Color.BLUE);
                    imageView.invalidate();
                    return true;

                case DragEvent.ACTION_DROP:
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    ImageView source = findViewById(Integer.parseInt(item.getText().toString()));

                    if (imageView != source) {
                        imageView.setImageDrawable(source.getDrawable());
                        source.setImageDrawable(null);
                        return true;
                    } else {
                        //drop is discarded when the source is the target
                        return false;
                    }



                case DragEvent.ACTION_DRAG_ENDED:

                    imageView.clearColorFilter();
                    imageView.setBackgroundResource(R.drawable.backgoundborder);
                    imageView.invalidate();

                    // Does a getResult(), and displays what happened.
                    if (event.getResult()) {
                        Toast.makeText(DragDropActivity.this, "The drop was handled.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DragDropActivity.this, "The drop didn't work.", Toast.LENGTH_SHORT).show();
                    }
                    return true;

                default:
                    Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                    break;
            }

            return false;
        }

    }

    private String toStringDragEvent(int eventNumber) {
        switch (eventNumber) {
            case DragEvent.ACTION_DRAG_STARTED : return "ACTION_DRAG_STARTED";
            case DragEvent.ACTION_DRAG_ENTERED : return "ACTION_DRAG_ENTERED";
            case DragEvent.ACTION_DRAG_LOCATION : return "ACTION_DRAG_LOCATION";
            case DragEvent.ACTION_DRAG_EXITED : return "ACTION_DRAG_EXITED";
            case DragEvent.ACTION_DROP : return "ACTION_DROP";
            case DragEvent.ACTION_DRAG_ENDED : return "ACTION_DRAG_ENDED";
            default : return "default";
        }
    }
}
