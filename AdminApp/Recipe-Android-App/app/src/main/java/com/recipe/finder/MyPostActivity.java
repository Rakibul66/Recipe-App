package com.recipe.finder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.recipe.finder.Adapters.Feed;
import com.recipe.finder.Adapters.FeedAdapter;
import com.recipe.finder.Adapters.FeedRecyclerDecoration;


public class MyPostActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle;

    RecyclerView recyclerView;
    private FeedAdapter adapter;

    private FirebaseFirestore db;
    private CollectionReference post;

    public static final String EXTRA_POST_ID = "com.example.firebaseprofile.EXTRA_POST_ID";
    public static final String EXTRA_TITLE = "com.example.firebaseprofile.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.firebaseprofile.EXTRA_DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);



        recyclerView = findViewById(R.id.recyclerview);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        int sidePadding = getResources().getDimensionPixelSize(R.dimen.sidePadding);
        recyclerView.addItemDecoration(new FeedRecyclerDecoration(topPadding, sidePadding, bottomPadding));

        db = FirebaseFirestore.getInstance();
        post = db.collection("Post");

        Query query = post.orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Feed> options = new FirestoreRecyclerOptions.Builder<Feed>()
                .setQuery(query, Feed.class)
                .build();
        adapter = new FeedAdapter(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        adapter.setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot) {
                Feed feed = documentSnapshot.toObject(Feed.class);

                String id = documentSnapshot.getId();
                String title = feed.getTitle();
                String description = feed.getDesc();

                Intent intent = new Intent(MyPostActivity.this, MoreEditpostActivity.class);
                intent.putExtra(EXTRA_POST_ID,id);
                intent.putExtra(EXTRA_TITLE,title);
                intent.putExtra(EXTRA_DESCRIPTION,description);
                startActivity(intent);
            }
        });

    }
}
