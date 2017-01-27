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
            "Img2",
            "Img3","Img4","Img5","Img6","Img7","Img8",
    };
    private final Integer image_ids[] = {
            R.drawable.testimg1,
            R.drawable.testimg2,
            R.drawable.testimg3,
            R.drawable.testimg4,
            R.drawable.testimg5,
            R.drawable.testimg6,
            R.drawable.testimg7,
            R.drawable.testimg8,
    };

    AlbumControl(Activity _activity, RecyclerView recyclerView){

        this.albumActivity= _activity;
       // this.albumView = albumView;
        this.recyclerView= recyclerView;


    }
    public void albumInit(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(albumActivity.getApplicationContext(), numOfPhotoOnLine );
        recyclerView.setLayoutManager(layoutManager);
        albumModels = prepareData();
        AlbumAdapter adapter = new AlbumAdapter(albumActivity.getApplicationContext(), albumModels);
        adapter.setSelectedItemNum(0);
        adapter.setSelectMode(false);

        recyclerView.setAdapter(adapter);

    }

    public ArrayList<AlbumModel> prepareData(){

        // 여기서 싱글톤 데이터 가져와서 앨범에 넣어주고, 클릭, 삭제 같은 거 했을 때는 같은 번호에 해당하는 데이터를 지워주고 notify 하면됨

        ArrayList<AlbumModel> theimage = new ArrayList<>();
        for(int i = 0; i< image_titles.length; i++){
            AlbumModel albumModel = new AlbumModel();
            albumModel.setImage_title(image_titles[i]);
            albumModel.setImage_ID(image_ids[i]);
            theimage.add(albumModel);
        }

        return theimage;
    }





}
