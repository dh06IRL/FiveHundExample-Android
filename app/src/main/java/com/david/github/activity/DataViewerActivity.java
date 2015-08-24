package com.david.github.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.david.github.R;
import com.david.github.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

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

            try {
                viewerImage.setImageBitmap(downloadBitmap(imageUrl));
            }catch (IOException e){
                Log.e("error", e.toString());
            }

        }else{
            viewerImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_sad_face));
        }
    }

    private Bitmap downloadBitmap(String url) throws IOException {
        HttpUriRequest request = new HttpGet(url.toString());
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(request);

        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            byte[] bytes = EntityUtils.toByteArray(entity);

            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
                    bytes.length);
            return bitmap;
        } else {
            throw new IOException("Download failed, HTTP response code "
                    + statusCode + " - " + statusLine.getReasonPhrase());
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
