package kr.co.teampierce.forfitproject.album_screen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


import kr.co.teampierce.forfitproject.R;
import kr.co.teampierce.forfitproject.compare_screen.compareActivity;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by banggijin on 2017. 1. 20..
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    public ArrayList<AlbumModel> albumList;
    private Context context;
    private int selectedItemNum=0;
    public boolean selectMode = false;


    public boolean isSelectMode(){
        return selectMode;
    }
    public void setSelectMode(boolean b){
        selectMode=b;
    }
    public void setSelectedItemNum(int n){
        selectedItemNum=n;
    }
    public int getSelectedItemNum(){
        return selectedItemNum;
    }
    public AlbumAdapter(Context context, ArrayList<AlbumModel> albumList) {
        this.albumList = albumList;
        this.context = context;
    }

    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }


    private void multipleCheckTrue(final AlbumAdapter.ViewHolder viewHolder,final int i){
        viewHolder.check.setChecked(true);
        viewHolder.itemView.setAlpha(0.5f);
        albumList.get(i).setSelected(true);


    }
    private void multipleCheckFalse(final AlbumAdapter.ViewHolder viewHolder,final int i){
        viewHolder.check.setChecked(false);
        viewHolder.itemView.setAlpha(1f);
        albumList.get(i).setSelected(false);

    }


    @Override
    public void onBindViewHolder(final AlbumAdapter.ViewHolder viewHolder, final int i) {

        // 색 변환하는 부분 깔끔하게 나오게
        View.OnClickListener multipleClick = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(selectMode) {
                    if(viewHolder.itemView.getAlpha()==0.5f)
                        multipleCheckFalse(viewHolder, i);
                    else
                        multipleCheckTrue(viewHolder, i);

                    if(selectedItemNum == 2){
                        albumActivity.enableCompareButton();
                    }else{
                        albumActivity.disableCompareButton();
                    }
                }else{
                    albumActivity.showPhotoInfo(i);
                    // 사진 커지게 보여주기

                }

            }
        };


        View.OnLongClickListener OnLongClick = new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                //Log.i("TAG","longclick : " + selectMode);

                if(selectMode==false){
                    selectMode=true;
                    for(int j=0;j<albumList.size();j++){
                        albumList.get(j).setSelected(false);
                    }
                    selectedItemNum=0;
                    //multipleCheckTrue(viewHolder,i);

                    notifyDataSetChanged();

                    return true;
                } else
                    return true;
            }
        };

        // 체크버튼 리스너는 따로 등록해주자

        viewHolder.itemView.setOnClickListener(multipleClick);
        viewHolder.itemView.setOnLongClickListener(OnLongClick);

        CheckBox.OnCheckedChangeListener OnCheckedChange = new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    multipleCheckTrue(viewHolder, i);
                    selectedItemNum++;
                }

                else{
                    multipleCheckFalse(viewHolder, i);
                    selectedItemNum--;
                }

            }
        };

        viewHolder.check.setOnCheckedChangeListener(OnCheckedChange);

        if(selectMode){
            viewHolder.check.setVisibility(View.VISIBLE);
            albumActivity.enableDeleteButton();
        }else{
            viewHolder.check.setVisibility(View.INVISIBLE);
            viewHolder.check.setChecked(false);
            albumActivity.disableDeleteButton();
        }

        // 여기가 리소스 표시부분
        // 다른데 건드릴 필요없이 여기에 괄호 안에 우리 데이터값 i 번째에 맞게 뽑아서 보여주면 됨

        viewHolder.title.setText(albumList.get(i).getImage_title());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageResource((albumList.get(i).getImage_ID()));

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;
        private CheckBox check;
        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
            check = (CheckBox) view.findViewById(R.id.checkBox);

        }
    }
}
