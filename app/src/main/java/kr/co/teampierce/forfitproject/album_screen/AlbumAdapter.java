package kr.co.teampierce.forfitproject.album_screen;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.teampierce.forfitproject.R;

/**
 * Created by banggijin on 2017. 1. 20..
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    public ArrayList<AlbumModel> albumList;
    private Context context;

    public AlbumAdapter(Context context, ArrayList<AlbumModel> albumList) {

        this.albumList = albumList;
        this.context = context;

    }
    public void good(){

    }
    public void deleteI(int pos){
        albumList.remove(pos);
    }


    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        //view.findViewById(R.id.checkBox)
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AlbumAdapter.ViewHolder viewHolder, final int i) {

        // 색 변환하는 부분 깔끔하게 나오게
        //viewHolder.itemView.setBackgroundColor(albumList.get(i).isSelected() ? Color.CYAN : Color.WHITE);
        View.OnClickListener multipleClick = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(albumList.get(i).isSelectMode()) {
                    albumList.get(i).setSelected(!albumList.get(i).isSelected());
                    v.setBackgroundColor(albumList.get(i).isSelected() ? Color.CYAN : Color.WHITE);
                    viewHolder.check.setChecked(!viewHolder.check.isChecked());
                }

            }
        };

        viewHolder.itemView.setOnClickListener(multipleClick);
        viewHolder.check.setOnClickListener(multipleClick);
        viewHolder.img.setOnClickListener(multipleClick);

        //viewHolder.title.
        if(albumList.get(i).isSelectMode()){
            viewHolder.check.setVisibility(View.VISIBLE);
        }else{
            viewHolder.check.setVisibility(View.INVISIBLE);
            viewHolder.itemView.setBackgroundColor(Color.WHITE);
            viewHolder.img.setBackgroundColor(Color.WHITE);
            viewHolder.check.setBackgroundColor(Color.TRANSPARENT);
            viewHolder.check.setChecked(false);


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
