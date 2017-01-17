package pt.ismai.hungryme.ui;

import android.os.Bundle;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import pt.ismai.hungryme.R;
import pt.ismai.hungryme.ui.UI.BaseActivity;

public class WebViewActivity extends BaseActivity {
    private WebView mWebView = null;

    private WebView mWebview ;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mWebview  = new WebView(this);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });

        String url = getIntent().getStringExtra("url");
        mWebview .loadUrl(url);

        Utils.onActivityCreateSetTheme(this);
        setContentView(mWebview);

    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_settings;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }
}