package com.example.imagesonair;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ShowImages extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private DatabaseReference databaseReference;
    private List<Upload> uploads;
    private FirebaseStorage storage;
    private ValueEventListener valueEventListener;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        uploads = new ArrayList<>();
        adapter = new RecyclerAdapter(ShowImages.this, uploads);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemclickLiseter(ShowImages.this);
        storage = FirebaseStorage.getInstance();
        userName = getIntent().getStringExtra("username");
        databaseReference = FirebaseDatabase.getInstance().getReference(userName+"MyDir/");
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                uploads.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Upload upload = ds.getValue(Upload.class);
                    upload.setDbKey(ds.getKey());
                    uploads.add(upload);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ShowImages.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Double Click to choose options", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareClick(int position) {
        Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
     Upload selectedItem = uploads.get(position);
     final String key = selectedItem.getDbKey();
        StorageReference imageReference = storage.getReferenceFromUrl(selectedItem.getImageURL());
        imageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child(key).removeValue();
                Toast.makeText(ShowImages.this, "Image Deleted", Toast.LENGTH_SHORT).show();
            }
        });

//        Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(valueEventListener);
    }

    public void UploadImages(View v){
        Intent i = new Intent(ShowImages.this, UploadPage.class);
        i.putExtra("username",userName);
        startActivity(i);
    }

    public void logout(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
