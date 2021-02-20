package com.example.app_sfondi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.drm.DrmManagerClient;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.RED;

public class MainActivity extends AppCompatActivity {

    final long ONE_MEGABYTE = 1024 * 1024; //dichiarazione variabile per indicare la quantità massima di byte per il download
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    Adapter adapter;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    ArrayList<Bitmap> bitList;
    ProgressBar progressBar;

    TextView txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //dichiarazione roba fatta nell'activity

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        manager = new LinearLayoutManager(this);
        txtError = (TextView) findViewById(R.id.textView);

        //list of images
        bitList = new ArrayList<>();


        //istanza dello storage di firebase
        FirebaseStorage storage = FirebaseStorage.getInstance();

        //creazione una referenza dello storage
        StorageReference storageRef = storage.getReference();
        //creazione di una referenza con un file iniziale



        //creazione lista con tutti i file dentro
        StorageReference listRef = storage.getReference().child("images");



        //NON FUNZIONA STORAGE DI FIREBASE
        //L'UNICA è CERCARE LE VERSIONI VECCHIE DI QUESTA APPLICAZIONE E VEDERE SE LA ROBA VECCHIA FUNZIONA
        //PORCODDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDIO

        Bitmap bit;
        for(int i=0;i<5;i++){
            //bit = returnBitmap(listRef,i);
            //bitList.add(bit);


        }


        //endless list on scrolling
        adapter = new Adapter(bitList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);


        /*
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            public void onScrollStateChanged(RecyclerView recyclerView, int newState){
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true;
            }

            public void onScrolled(RecyclerView cecyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx,dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstCompletelyVisibleItemPosition();

                System.out.println("current: "+currentItems+" total: "+totalItems+" scrollOut: "+scrollOutItems);



                if(isScrolling && (currentItems + scrollOutItems == totalItems)){
                    //data fetch
                    isScrolling = false;
                    fetchData(listRef);
                }
            }
        });













    }

    private void fetchData(StorageReference listRef){
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable(){
            public void run(){

                System.out.println("It works in the run?");
                    listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>(){
                        public void onSuccess(ListResult listResult) {
                            int j = 0; //variable count
                            for (StorageReference item : listResult.getItems()) { //for all images
                                if (j < 5) {

                                    int finalJ = j; //it is obligatory, otherwise it do an error
                                    item.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() { //insert image in the imageButton
                                        public void onSuccess(byte[] bytes) {
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                            bitList.add(bitmap);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() { //in caso di errore per l'accesso al database
                                        public void onFailure(@NonNull Exception exception) {
                                            System.out.println(exception.toString());
                                        }
                                    });

                                    j++;
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener(){
                        public void onFailure(@NonNull Exception e){
                            System.out.println("Errore lista");
                        }
                    });


                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

            }
        }, 1000);
    }

         */}


    //Dato lo storage e l'indice, restituisce il bitmap dell'immagine   -- da finire
    private Bitmap returnBitmap(StorageReference listRef, int i) {
        final Bitmap[] res = new Bitmap[1];

        listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            public void onSuccess(ListResult listResult) {

                List items = listResult.getItems();
                StorageReference item = (StorageReference) items.get(i);

                item.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() { //insert image in the imageButton
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        res[0] = bitmap;

                    }
                }).addOnFailureListener(new OnFailureListener() { //in caso di errore per l'accesso al database
                    public void onFailure(@NonNull Exception exception) {
                        System.out.println(exception.toString());
                        txtError.setText(exception.toString());
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@NonNull Exception e) {
                System.out.println("Errore lista");
            }
        });

        return res[0];
    }


}












 /* PER FARE IL DOWNLOAD ---- incompleto
        listRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
            public void onSuccess(Uri uri){
                Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
            }
        }).addOnFailureListener(new OnFailureListener(){
            public void onFailure(@NonNull Exception exception){
                textError.setText(exception.toString());
            }
        });
*/