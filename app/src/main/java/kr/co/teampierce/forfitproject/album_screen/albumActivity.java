package kr.co.teampierce.forfitproject.album_screen;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.app.AlertDialog;
import android.widget.Toast;

import kr.co.teampierce.forfitproject.R;
import kr.co.teampierce.forfitproject.compare_screen.compareActivity;

import static kr.co.teampierce.forfitproject.album_screen.albumActivity.ALBUM_MODE_CONSTANT.ALBUM_MODE;
import static kr.co.teampierce.forfitproject.album_screen.albumActivity.ALBUM_MODE_CONSTANT.DETAIL_MODE;


public class albumActivity extends AppCompatActivity {
    private static RecyclerView recyclerView;
    private static AlbumAdapter albumAdapter;
    private static Button compareButton;
    private static Button deleteButton;
    private static ImageView imgView;
    private static ImageView imgViewDetail;
    private static LinearLayout detail;
    private static LinearLayout album;

    enum ALBUM_MODE_CONSTANT{ALBUM_MODE,DETAIL_MODE}
    private static ALBUM_MODE_CONSTANT albumMode = ALBUM_MODE;


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
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setMessage("정말 삭제하시겠습니까?");

        // 확인버튼
        alertdialog.setPositiveButton("삭제", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "'삭제'버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();

                //삭제행동
                albumAdapter = (AlbumAdapter) recyclerView.getAdapter();
                for(int i = albumAdapter.albumList.size()-1; i >= 0 ;i--){
                    Log.i("TAG", i + " check? : " + albumAdapter.albumList.get(i).isSelected());
                    if(albumAdapter.albumList.get(i).isSelected()) {
                        //TODO 지우기 할 때 여기서 데이터 지우면됨
                        albumAdapter.albumList.get(i).setSelected(false);
                        albumAdapter.albumList.remove(i);
                    }
                }
                //Log.i("TAG", "sel : " +albumAdapter.getSelectedItemNum());
                //
                albumAdapter.notifyDataSetChanged();
                onBackPressed();

            }
        });

        // 취소버튼
        alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "'취소'버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = alertdialog.create();
        // 아이콘 설정
        //alert.setIcon(R.drawable.testimg8);
        alert.setTitle("삭제");
        alert.show();




    }


    private void albumInit(){
        recyclerView = (RecyclerView) findViewById(R.id.albumRecyclerView);
        albumAdapter = (AlbumAdapter) recyclerView.getAdapter();
        compareButton = (Button) findViewById(R.id.compareButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        imgView = (ImageView) findViewById(R.id.imgViewExpanded);
        imgViewDetail = (ImageView) findViewById(R.id.imgViewDetail);

        //
        detail = (LinearLayout) findViewById(R.id.detailFrame);
        album = (LinearLayout) findViewById(R.id.albumFrame);
        albumMode=ALBUM_MODE;

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
        imgViewDetail.setImageResource(albumAdapter.albumList.get(index).getImage_ID());
        //
      //  if(detail.getVisibility()!=View.VISIBLE){
            detail.setVisibility(View.VISIBLE);
            album.setVisibility(View.INVISIBLE);
            albumMode=DETAIL_MODE;
      //  }else{

      //  }

    }

    @Override
    public void onBackPressed(){

        albumAdapter = (AlbumAdapter) recyclerView.getAdapter();
        if(albumMode==ALBUM_MODE){
            if(albumAdapter.selectMode==true){
                albumAdapter.setSelectMode(false);
                albumAdapter.notifyDataSetChanged();
                disableDeleteButton();
                disableCompareButton();

            }else
                super.onBackPressed();
        }else if(albumMode==DETAIL_MODE){
            detail.setVisibility(View.INVISIBLE);
            album.setVisibility(View.VISIBLE);
            albumMode=ALBUM_MODE;
        }

    }

    public void OnClickAlbum(View v){
        LinearLayout detail = (LinearLayout) findViewById(R.id.detailFrame);
        LinearLayout album = (LinearLayout) findViewById(R.id.albumFrame);
        if(detail.getVisibility()!=View.VISIBLE){
            detail.setVisibility(View.VISIBLE);
            album.setVisibility(View.INVISIBLE);
        }else{
            detail.setVisibility(View.INVISIBLE);
            album.setVisibility(View.VISIBLE);
        }

        //DialogSimple();
    }



    public void DialogSimple(){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        // 다이얼로그 메세지
        alertdialog.setMessage("기본 다이얼로그 입니다.");

        // 확인버튼
        alertdialog.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "'확인'버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        // 취소버튼
        alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "'취소'버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        // 메인 다이얼로그 생성
        AlertDialog alert = alertdialog.create();
        // 아이콘 설정
        alert.setIcon(R.drawable.testimg8);
        // 타이틀
        alert.setTitle("제목");
        // 다이얼로그 보기
        alert.show();
    }
}
