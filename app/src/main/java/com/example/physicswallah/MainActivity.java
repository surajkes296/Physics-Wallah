package com.example.physicswallah;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<PhysicsWallah>> {

    private static final String LOG_TAG=MainActivity.class.getSimpleName();
    private static final String REQUEST_URL ="https://my-json-server.typicode.com/easygautam/data/users";
    private static final int LOADER_ID = 1;
    RecyclerView recyclerView;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.grad));
        alertDialog=progressDialog(this,"Loading...");
        alertDialog.show();


        recyclerView=findViewById(R.id.recyclerView);

        //To make the app Full Screen
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Since Network request can't be made on the main thread Loader is used
        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        LoaderManager loaderManager = getLoaderManager();
        Log.v(LOG_TAG,"initLoader() exectued....");
        loaderManager.initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<PhysicsWallah>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG,"OnCreateLoader() executed....");
        return new PhysicsLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<PhysicsWallah>> loader, List<PhysicsWallah> data) {

        //Setting the RecyclerView Data
        Adapter adapter=new Adapter(MainActivity.this,MainActivity.this,data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        alertDialog.hide();

        Log.d(LOG_TAG,"onLoadFinished() exectued....");
    }

    @Override
    public void onLoaderReset(Loader<List<PhysicsWallah>> loader) {
        Log.v(LOG_TAG,"onLoaderReset() exectued....");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) onBackPressed();
        return  true;
    }


    //The progress which is enabled for the time when network request is made.
    public static AlertDialog progressDialog(Context context, String message) {
        int padding = 30;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(padding, padding, padding, padding);
        linearLayout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        linearLayout.setLayoutParams(params);

        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setIndeterminate(true);
        progressBar.setPadding(0, 0, padding, 0);
        progressBar.setLayoutParams(params);

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        TextView tvText = new TextView(context);
        tvText.setText(message);
        tvText.setLayoutParams(params);

        linearLayout.addView(progressBar);
        linearLayout.addView(tvText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setView(linearLayout);

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
        }
        return dialog;
    }
}
