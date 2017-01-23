package kr.co.teampierce.forfitproject.album_screen;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import kr.co.teampierce.forfitproject.R;

/**
 * Created by banggijin on 2017. 1. 20..
 */

public class AlbumControl extends Activity{

    private Activity albumActivity;
    private RelativeLayout albumView;
    private RecyclerView recyclerView;
    private ArrayList<AlbumModel> albumModels;
    private final static int numOfPhotoOnLine = 3;

    private final String image_titles[] = {
            "Img1",
        /*    "Img2",
            "Img3","Img4","Img5","Img6","Img7","Img8",*/
    };
    private final Integer image_ids[] = {
            R.drawable.testimg1,
         /*   R.drawable.testimg2,
            R.drawable.testimg3,
            R.drawable.testimg4,
            R.drawable.testimg5,
            R.drawable.testimg6,
            R.drawable.testimg7,
            R.drawable.testimg8,*/
    };

    AlbumControl(Activity _activity, RelativeLayout albumView, RecyclerView recyclerView){

        this.albumActivity= _activity;
        this.albumView = albumView;
        this.recyclerView= recyclerView;


    }
    public void albumInit(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(albumActivity.getApplicationContext(), numOfPhotoOnLine );
        recyclerView.setLayoutManager(layoutManager);
        albumModels = prepareData();
        AlbumAdapter adapter = new AlbumAdapter(albumActivity.getApplicationContext(), albumModels);
        recyclerView.setAdapter(adapter);

    }

    public ArrayList<AlbumModel> prepareData(){

        ArrayList<AlbumModel> theimage = new ArrayList<>();
        for(int i = 0; i< image_titles.length; i++){
            AlbumModel albumModel = new AlbumModel();
            albumModel.setImage_title(image_titles[i]);
            albumModel.setImage_ID(image_ids[i]);
            theimage.add(albumModel);
        }

        return theimage;
    }

    public View.OnClickListener getOnClickAlbumButtonListener(){
        Button.OnClickListener mClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                RelativeLayout albumView = (RelativeLayout) albumActivity.findViewById(R.id.albumLayout);
                if (albumView.getVisibility() == View.GONE || albumView.getVisibility() == View.INVISIBLE)
                    albumView.setVisibility(View.VISIBLE);
                else
                    albumView.setVisibility(View.GONE);
            }
        };
        return mClickListener;

    }
    public View.OnClickListener getOnClickCompareButtonListener(){
        Button.OnClickListener mClickListener = new View.OnClickListener() {
            public void onClick(View view){
                AlbumAdapter albumAdapter = (AlbumAdapter) recyclerView.getAdapter();
                for(int i=0;i<albumAdapter.getItemCount();i++){
                    albumAdapter.albumList.get(i).setSelectMode(!albumAdapter.albumList.get(i).isSelectMode());
                }
                albumAdapter.notifyDataSetChanged();
            }
        };
        return mClickListener;

    }
    public void getOnClickDeleteFirst(){

    }
    public void getOnClickCompareFirst(){

    }
    public void getOnClickCompareAfterCompare(){

    }
    public void getOnClickDeleteAfterDelete(){

    }
    public void getOnClickSelectAllAfterDelete(){

    }
    public void onClickDelete(View view){
        // RecyclerView Rv = (RecyclerView) findViewById(R.id.albumRecyclerView);
        // albumModels.remove(2);
        // Rv.getAdapter().notifyDataSetChanged();


    }
    public void onClickShow(View view){

    }

}
