package com.david.github.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.david.github.R;
import com.david.github.utils.Constants;
import com.github.florent37.glidepalette.GlidePalette;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by davidhodge on 7/23/15.
 */
public class DataViewerActivity extends BaseActivity {

    Context mContext;
    ActionBar actionBar;

    String name, username, desc, imageUrl;
    Double rating;

    @Bind(R.id.viewer_image)
    ImageView viewerImage;
    @Bind(R.id.viewer_rating)
    TextView viewerRating;
    @Bind(R.id.viewer_name)
    TextView viewerName;
    @Bind(R.id.viewer_user_name)
    TextView viewerUsername;
    @Bind(R.id.viewer_desc)
    TextView viewerDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_viewer);
        ButterKnife.bind(this);

        mContext = this;
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            name = extras.getString(Constants.EXTRA_NAME);
            username = extras.getString(Constants.EXTRA_USERNAME);
            desc = extras.getString(Constants.EXTRA_DESC);
            imageUrl = extras.getString(Constants.EXTRA_URL);

            rating = extras.getDouble(Constants.EXTRA_RATING);

            viewerRating.setText(String.format("%.2f", rating));
            viewerName.setText(name);
            viewerUsername.setText(username);
            viewerDesc.setText(desc);

            Glide.with(mContext)
                    .load(imageUrl)
                    .centerCrop()
                    .listener(GlidePalette.with(imageUrl)
                            .use(GlidePalette.Profile.VIBRANT)
                            .intoBackground(viewerRating, GlidePalette.Swatch.RGB)
                            .intoTextColor(viewerRating, GlidePalette.Swatch.BODY_TEXT_COLOR)
                            .use(GlidePalette.Profile.MUTED)
                            .intoBackground(viewerImage, GlidePalette.Swatch.RGB)
                            .intoCallBack(new GlidePalette.CallBack() {
                                @Override
                                public void onPaletteLoaded(Palette palette) {
                                    //maybe?
                                }
                            }))
                    .crossFade()
                    .into(viewerImage);
        }else{
            viewerImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_sad_face));
            SnackbarManager.show(
                    Snackbar.with(mContext)
                            .type(SnackbarType.MULTI_LINE)
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .actionColor(Color.WHITE)
                            .text(getString(R.string.viewer_error))
                            .actionLabel(getString(R.string.viewer_okay))
                            .actionListener(new ActionClickListener() {
                                @Override
                                public void onActionClicked(Snackbar snackbar) {
                                    finish();
                                }
                            })
                            .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
                            .animation(true));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
