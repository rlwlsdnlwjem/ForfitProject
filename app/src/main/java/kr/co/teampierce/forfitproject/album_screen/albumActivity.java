package kr.co.teampierce.forfitproject.album_screen;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import kr.co.teampierce.forfitproject.R;
import kr.co.teampierce.forfitproject.compare_screen.compareActivity;

import static android.R.attr.start;
import static com.facebook.FacebookSdk.getApplicationContext;
import static kr.co.teampierce.forfitproject.R.id.albumLayout;
import static kr.co.teampierce.forfitproject.R.id.img;
import static kr.co.teampierce.forfitproject.R.id.imgViewExpanded;

public class albumActivity extends AppCompatActivity {
    private static RecyclerView recyclerView;
    private static AlbumAdapter albumAdapter;
    private static Button compareButton;
    private static Button deleteButton;
    private static ImageView imgView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        albumInit();


    }
    public void onCompareClick(View v){
        Intent intent = new Intent(getApplicationContext(), compareActivity.class);
        intent.putExtra("name","abc");
        albumAdapter = (AlbumAdapter) recyclerView.getAdapter();
        for(int i = 0; i < albumAdapter.albumList.size();i++){
            Log.i("TAG", i + " check? : " + albumAdapter.albumList.get(i).isSelected());
        }
        startActivity(intent);
    }
    public void onDeleteClick(View v){
        albumAdapter = (AlbumAdapter) recyclerView.getAdapter();
        for(int i = 0; i < albumAdapter.albumList.size();i++){
            Log.i("TAG", i + " check? : " + albumAdapter.albumList.get(i).isSelected());
        }
        Log.i("TAG", "sel : " +albumAdapter.getSelectedItemNum());

    }


    private void albumInit(){
        recyclerView = (RecyclerView) findViewById(R.id.albumRecyclerView);
        albumAdapter = (AlbumAdapter) recyclerView.getAdapter();
        compareButton = (Button) findViewById(R.id.compareButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        imgView = (ImageView) findViewById(R.id.imgViewExpanded);

        RelativeLayout albumLayout = (RelativeLayout) findViewById(R.id.albumLayout);
        AlbumControl albumControl = new AlbumControl(this, albumLayout, recyclerView);
        albumControl.albumInit();

    }

    public static void enableCompareButton(){
        compareButton.setEnabled(true);
    }
    public static void disableCompareButton(){
        compareButton.setEnabled(false);
    }
    public static void enableDeleteButton(){
        deleteButton.setEnabled(true);
    }
    public static void disableDeleteButton(){
        deleteButton.setEnabled(false);
    }
    public static void showPhotoInfo(int index){
        albumAdapter = (AlbumAdapter) recyclerView.getAdapter();
        imgView.setImageResource(albumAdapter.albumList.get(index).getImage_ID());
    }

    @Override
    public void onBackPressed(){

        albumAdapter = (AlbumAdapter) recyclerView.getAdapter();

        if(albumAdapter.selectMode==true){
            albumAdapter.setSelectMode(false);
            albumAdapter.notifyDataSetChanged();
            disableDeleteButton();
            disableCompareButton();

        }else
            super.onBackPressed();
    }

}
