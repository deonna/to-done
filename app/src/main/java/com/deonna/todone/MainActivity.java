package com.deonna.todone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> todoAdapter;
    private ListView lvItems;
    private EditText etNewItem;

    private Todos todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateTodoItems();

        etNewItem = (EditText) findViewById(R.id.etNewItem);

        initializeListView();
    }

    private static boolean hasBeenEdited(int requestCode, int resultCode) {

        return (requestCode == Code.EDIT_REQUEST.ordinal() && resultCode == Code.EDITED.ordinal());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (hasBeenEdited(requestCode, resultCode)) {
            String newTodo = data.getStringExtra("new_todo");
            int position = data.getIntExtra("position", 0);

            todos.edit(position, newTodo);
            todoAdapter.notifyDataSetChanged();
        }
    }

    private void removeTodo(int position) {

        todos.remove(position);
        todoAdapter.notifyDataSetChanged();
    }

    private void populateTodoItems() {

        todos = new Todos(getFilesDir());
        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                todos.getItems());
    }

    public void onAddItem(View view) {

        String newItem = etNewItem.getText().toString().trim();

        if (todos.add(newItem)) {
            todoAdapter.notifyDataSetChanged();
            etNewItem.setText("");
        }
    }

    private void initializeListView() {
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(todoAdapter);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeTodo(position);
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("current_todo", todos.get(i));
                intent.putExtra("position", i);
                startActivityForResult(intent, Code.EDIT_REQUEST.ordinal());
            }
        });
    }
}
