/*
Manage help page
 */
package ziruih.cmpt276.as3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setupHyperLink();

    }

    public static Intent makeLaunchIntent(Context context) {
        Intent intent = new Intent(context, HelpActivity.class);
        return intent;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }

    private void setupHyperLink() {
        TextView author =(TextView)findViewById(R.id.text_author);

        author.setMovementMethod(LinkMovementMethod.getInstance());

        TextView icon = (TextView) findViewById(R.id.text_icon);
        icon.setMovementMethod(LinkMovementMethod.getInstance());

        TextView bg1 = (TextView) findViewById(R.id.text_bg_cr1);
        bg1.setMovementMethod(LinkMovementMethod.getInstance());

        TextView bg2 = (TextView) findViewById(R.id.text_bg_cr2);
        bg2.setMovementMethod(LinkMovementMethod.getInstance());

        TextView courseWeb = (TextView) findViewById(R.id.course_web);
        courseWeb.setMovementMethod(LinkMovementMethod.getInstance());
    }
}