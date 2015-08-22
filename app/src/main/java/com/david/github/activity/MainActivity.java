package com.david.github.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.david.github.R;
import com.david.github.adapter.DataAdapter;
import com.david.github.models.FiveHundResponse;
import com.david.github.services.RestClient;
import com.david.github.utils.Constants;
import com.david.github.utils.Utils;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends BaseActivity {

    private int NETWORK_ERROR = 1;
    private int LOAD_ERROR = 2;

    private Context mContext;
    private DataAdapter dataAdapter;
    private SwingBottomInAnimationAdapter animatorAdapter;

    @Bind(R.id.main_data_list)
    ListView mainDataList;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.main_list_image)
    ImageView mainListImage;
    @Bind(R.id.loading)
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mContext = this;

        swipeRefreshLayout.setColorSchemeResources(
                R.color.primary,
                R.color.primary_dark,
                R.color.primary,
                R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                runDataCheck();
            }
        });

        runDataCheck();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_reload) {

            //clears stack, reloads app
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        //could run updates here if needed for fresher data
    }

    private void runDataCheck(){
        //basic network check first
        if(Utils.isNetworkConnectionAvailable(mContext)) {
            mainListImage.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);

            RestClient.get().getData(new Callback<FiveHundResponse>() {
               @Override
               public void success(FiveHundResponse dataModels, Response response) {
                   loading.setVisibility(View.GONE);
                   swipeRefreshLayout.setRefreshing(false);

                   dataAdapter = new DataAdapter(mContext, dataModels.photos, MainActivity.this);
                   animatorAdapter = new SwingBottomInAnimationAdapter(dataAdapter);
                   animatorAdapter.setAbsListView(mainDataList);
                   mainDataList.setAdapter(animatorAdapter);
               }

               @Override
               public void failure(RetrofitError error) {
                   Log.e(Constants.TAG, error.toString());
                   loading.setVisibility(View.GONE);
                   swipeRefreshLayout.setRefreshing(false);
                   mainListImage.setVisibility(View.VISIBLE);
                   //show a snack error
                   showErrorSnack(getString(R.string.main_update_fail), getString(R.string.main_snack_retry), Snackbar.SnackbarDuration.LENGTH_INDEFINITE, LOAD_ERROR);
               }
           });
        }else{
            mainListImage.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
            //show a snack error
            showErrorSnack(getString(R.string.main_network_error), getString(R.string.main_snack_done), Snackbar.SnackbarDuration.LENGTH_INDEFINITE, NETWORK_ERROR);
        }
    }

    private void showErrorSnack(String errorText, String btnText, Snackbar.SnackbarDuration duration, final int type){
        SnackbarManager.show(
                Snackbar.with(mContext)
                        .type(SnackbarType.MULTI_LINE)
                        .color(Color.RED)
                        .textColor(Color.WHITE)
                        .actionColor(Color.WHITE)
                        .text(errorText)
                        .actionLabel(btnText)
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                                if (type == NETWORK_ERROR) {
                                    finish();
                                } else if (type == LOAD_ERROR) {
                                    runDataCheck();
                                } else {
                                    //cause who knows
                                    snackbar.dismiss();
                                }
                            }
                        })
                        .duration(duration)
                        .animation(true));
    }
}
