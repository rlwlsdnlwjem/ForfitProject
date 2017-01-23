package kr.co.teampierce.forfitproject.album_screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.RelativeLayout;

import kr.co.teampierce.forfitproject.R;

import static kr.co.teampierce.forfitproject.R.id.albumButton;

public class albumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        albumInit();

    }

    private void albumInit(){
        Button compareButton = (Button) findViewById(R.id.compareButton);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.albumRecyclerView);
        RelativeLayout albumLayout = (RelativeLayout) findViewById(R.id.albumLayout);

        AlbumControl albumControl = new AlbumControl(this, albumLayout, recyclerView);
        albumControl.albumInit();

       // albumButton.setOnClickListener(albumControl.getOnClickAlbumButtonListener());

        compareButton.setOnClickListener(albumControl.getOnClickCompareButtonListener());

    }
}
