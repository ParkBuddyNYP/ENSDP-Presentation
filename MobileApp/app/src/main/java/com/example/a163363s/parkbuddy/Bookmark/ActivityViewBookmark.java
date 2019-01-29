package com.example.a163363s.parkbuddy.Bookmark;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.a163363s.parkbuddy.R;

import java.util.List;

public class ActivityViewBookmark extends AppCompatActivity {


    private FloatingActionButton btnAdd;
    private RecyclerView recyclerView;
    private BookmarkDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookmark);
        btnAdd = findViewById(R.id.btnAddBookmark);
        recyclerView = findViewById(R.id.recyclerViewBookmark);
        db = new BookmarkDB(this, "Bookmark_DB");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityViewBookmark.this, SelectLocationActivity.class));
                ActivityViewBookmark.this.finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<bookmarkModel> list = db.getBookmarksList();
        Adapter adapter = new Adapter(list, this);
        recyclerView.setAdapter(adapter);
    }
}
