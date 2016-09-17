package com.example.andras.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActionBarActivity extends AppCompatActivity {

    private ActionMode actionMode;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerForContextMenu(findViewById(R.id.twContextMenu));

        //action mode for any item
        TextView twContextualAction = (TextView) findViewById(R.id.twContextualAction);
        twContextualAction.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (actionMode != null) {
                    return false;
                }

                actionMode = startActionMode(new MyActionModeCallback());
                view.setSelected(true);
                return true;
            }
        });

        //action mode for lists
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.text_view, new String[]{"First", "Second", "Third"});
        listView.setAdapter(adapter);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new MyMultiChoiceModelListener());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        System.out.println("onPrepareOptionsMenuxxx");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (menu.size() == 0) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            System.out.println("menusize: " + menu.size());
        }
        MenuItem item = menu.findItem(R.id.codeversed_logo);
        System.out.println("onCreateContextMenuxxx" + item.isEnabled());
        item.setEnabled(!item.isEnabled());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        TextView actionView = (TextView) menu.findItem(R.id.action_settings).getActionView();
        actionView.setText("I'm action view! Mostly I'm a SearchView");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private class MyActionModeCallback implements ActionMode.Callback {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_contextual_action, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Action mode title");
            mode.setSubtitle("Action mode subtitle");
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.contextual_action_2:
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };

    private class MyMultiChoiceModelListener extends MyActionModeCallback implements AbsListView.MultiChoiceModeListener  {

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            int checkedCount = listView.getCheckedItemCount();
            if (checkedCount > 0) {
                mode.setSubtitle(checkedCount + " item selected");
            } else {
                mode.setSubtitle("Action mode subtitle");
            }
        }
    }




}
