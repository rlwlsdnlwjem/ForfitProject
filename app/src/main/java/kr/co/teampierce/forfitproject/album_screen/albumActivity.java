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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import kr.co.teampierce.forfitproject.R;
import kr.co.teampierce.forfitproject.compare_screen.compareActivity;


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

        boolean left=false;
        albumAdapter = (AlbumAdapter) recyclerView.getAdapter();
        // 2개 체크해서 넘김
        for(int i = 0; i < albumAdapter.albumList.size();i++){
            Log.i("TAG", i + " check? : " + albumAdapter.albumList.get(i).isSelected());
            if(albumAdapter.albumList.get(i).isSelected()) {
                if(!left){
                    left=true;
                    intent.putExtra("left",i);
                }else{
                    intent.putExtra("right",i);
                }
            }
        }
        startActivity(intent);
    }
    public void onDeleteClick(View v){
        albumAdapter = (AlbumAdapter) recyclerView.getAdapter();
        for(int i = albumAdapter.albumList.size()-1; i >= 0 ;i--){
            Log.i("TAG", i + " check? : " + albumAdapter.albumList.get(i).isSelected());
            if(albumAdapter.albumList.get(i).isSelected()) {
                //TODO 지우기 할 때 여기서 데이터 싱글톤 불러다가 지우면 됨, 지우겠습니까 다이얼로그 띄우기
                albumAdapter.albumList.get(i).setSelected(false);
                albumAdapter.albumList.remove(i);

            }
        }

        Log.i("TAG", "sel : " +albumAdapter.getSelectedItemNum());
        //
        albumAdapter.notifyDataSetChanged();

    }


    private void albumInit(){
        recyclerView = (RecyclerView) findViewById(R.id.albumRecyclerView);
        albumAdapter = (AlbumAdapter) recyclerView.getAdapter();
        compareButton = (Button) findViewById(R.id.compareButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        imgView = (ImageView) findViewById(R.id.imgViewExpanded);

        RelativeLayout albumLayout = (RelativeLayout) findViewById(R.id.albumMenuLayout);
        AlbumControl albumControl = new AlbumControl(this,  recyclerView);
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

    public void OnClickAlbum(View v){
        LinearLayout detail = (LinearLayout) findViewById(R.id.detailFrame);
        LinearLayout album = (LinearLayout) findViewById(R.id.albumFrame);
        if(detail.getVisibility()!=View.VISIBLE){
            detail.setVisibility(View.VISIBLE);
            album.setVisibility(View.GONE);
        }else{
            detail.setVisibility(View.GONE);
            album.setVisibility(View.VISIBLE);
        }




    }
}
