package com.david.github.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.david.github.R;
import com.david.github.activity.DataViewerActivity;
import com.david.github.activity.MainActivity;
import com.david.github.models.Photo;
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
import java.util.List;

/**
 * Created by davidhodge on 7/23/15.
 */
public class DataAdapter extends BaseAdapter {

    private List<Photo> items;
    private LayoutInflater inflater;
    private Context mContext;
    private MainActivity mainActivity;

    public DataAdapter(Context mContext, List<Photo> items, MainActivity mainActivity) {
        this.mContext = mContext;
        this.items = items;
        inflater = LayoutInflater.from(this.mContext);
        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_data_view, parent, false);
            holder.dataName= (TextView) convertView.findViewById(R.id.data_name);
            holder.dataUsername = (TextView) convertView.findViewById(R.id.data_user_name);
            holder.dataRating = (TextView) convertView.findViewById(R.id.data_rating);
            holder.dataDesc = (TextView) convertView.findViewById(R.id.data_desc);
            holder.dataImage = (ImageView) convertView.findViewById(R.id.data_image);
            holder.dataImageUser = (ImageView) convertView.findViewById(R.id.data_image_user);
            holder.userInfoHolder = (LinearLayout) convertView.findViewById(R.id.user_info_holder);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            holder.dataImage.setImageBitmap(downloadBitmap(items.get(position).imageUrl));
            holder.dataImageUser.setImageBitmap(downloadBitmap(items.get(position).user.avatars.large.https));
        }catch (Exception e){
            Log.e("error", e.toString());
        }

        holder.dataName.setText(items.get(position).name);
        holder.dataUsername.setText(items.get(position).user.username);
        holder.dataDesc.setText(items.get(position).description);
        holder.dataRating.setText(String.format("%.2f", items.get(position).highestRating));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DataViewerActivity.class);
                intent.putExtra(Constants.EXTRA_NAME, items.get(position).name);
                intent.putExtra(Constants.EXTRA_RATING, items.get(position).highestRating);
                intent.putExtra(Constants.EXTRA_DESC, items.get(position).description);
                intent.putExtra(Constants.EXTRA_USERNAME, items.get(position).user.username);
                intent.putExtra(Constants.EXTRA_URL, items.get(position).imageUrl);

                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(mainActivity,
                                new Pair<View, String>(holder.dataName,
                                        mContext.getString(R.string.transition_name)),
                                new Pair<View, String>(holder.dataRating,
                                        mContext.getString(R.string.transition_rating)),
                                new Pair<View, String>(holder.dataUsername,
                                        mContext.getString(R.string.transition_username)),
                                new Pair<View, String>(holder.dataDesc,
                                        mContext.getString(R.string.transition_desc))
                        );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mContext.startActivity(intent, options.toBundle());
                }else{
                    mContext.startActivity(intent);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        LinearLayout userInfoHolder;
        TextView dataName;
        TextView dataUsername;
        TextView dataRating;
        TextView dataDesc;
        ImageView dataImage;
        ImageView dataImageUser;
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
}
