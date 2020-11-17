/*
Manage Game state
 */

package ziruih.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import ziruih.cmpt276.as3.model.BushManager;

public class GameActivity extends AppCompatActivity {

    BushManager bushManager;
    Button buttons[][];
    Vibrator vibrator;
    int scanCount;
    int pandaFound;
    int pandaCount;
    int timePlayed;
    int bestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // Get manager instance
        // Set manager attribute specified from options
        bushManager = BushManager.getInstance();
        int[] boardState = OptionActivity.getBoardState(GameActivity.this);
        bushManager.setPandaCount(OptionActivity.getNumPanda(GameActivity.this));
        bushManager.setRow(boardState[0]);
        bushManager.setCol(boardState[1]);
        timePlayed = OptionActivity.getPlayTime(GameActivity.this);
        bestScore = OptionActivity.getBestRecord(GameActivity.this, bushManager.getRow(), bushManager.getPandaCount());


        scanCount = 0;
        pandaCount = bushManager.getPandaCount();
        buttons = new Button[bushManager.getRow()][bushManager.getCol()];


        populateButtons();
        setTextViewPandaFound(pandaFound, pandaCount);
        setTextViewScanCount(scanCount);
        setTextViewTimePlayed(timePlayed);
        setTextViewBestScore(bestScore);


    }

    @Override
    protected void onStart() {
        super.onStart();
        bushManager.setBushArray();
    }

    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.table_for_buttons);
        for (int row = 0; row < bushManager.getRow(); row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);
            for (int col = 0; col < bushManager.getCol(); col++) {
                final int FINALROW = row;
                final int FINALCOL = col;
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setPadding(0, 0, 0, 0);
                button.getBackground().setAlpha(64);
                button.setTextColor(Color.CYAN);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(FINALROW, FINALCOL);
                    }
                });
                button.setTextColor(Color.GREEN);
                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void gridButtonClicked(int x, int y) {
        if (bushManager.hasRevealed(x, y)) {
            return;
        }
        Button button = buttons[x][y];
        if (bushManager.hasPanda(x, y)) {
            setButtonBackground(button, R.drawable.panda1);
            setTextViewPandaFound(++pandaFound, pandaCount);
            vibrator.vibrate(500);
        } else {
            setButtonBackground(button, R.drawable.footprint);
            setTextViewScanCount(++scanCount);
        }
        bushManager.revealBush(x, y);
        // Lock Button Sizes:
        lockButtonSizes();
        updateScan();

        // If all pandas have been found
        if (pandaCount == pandaFound) {
            // save playtime
            timePlayed++;
            OptionActivity.savePlayTime(GameActivity.this, timePlayed);
            // update best-record
            if (scanCount < bestScore) {
                OptionActivity.saveBestRecord(GameActivity.this, bushManager.getRow(), bushManager.getPandaCount(), scanCount);
            }
            // reset game
            bushManager.setBushArray();
            FragmentManager manager = getSupportFragmentManager();
            PopupFragment diag = new PopupFragment();
            diag.show(manager, "MessageDialog");
        }
    }

    private void lockButtonSizes() {
        for (int row = 0; row < bushManager.getRow(); row++) {
            for (int col = 0; col < bushManager.getCol(); col++) {
                Button button = buttons[row][col];
                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);
                int height = button.getWidth();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    private void setButtonBackground(Button button, int picID) {
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        BitmapDrawable bitmapDrawable = scaleKeepingAspect(picID, newWidth, newHeight);
        button.setBackground(bitmapDrawable);
    }

    // Reference: https://stackoverflow.com/questions/13158305/android-keep-aspect-ratio-on-background-image-on-default-button
    // This function help background image keep the original ratio
    private BitmapDrawable scaleKeepingAspect(int id, int dstWidth, int dstHeight) {
        Bitmap b = (new BitmapFactory()).decodeResource(getResources(), id);
        float scaleX = (float) dstWidth / b.getWidth();
        float scaleY = (float) dstHeight / b.getHeight();
        float scale = scaleX < scaleY ? scaleX : scaleY;
        int sclWidth = Math.round(scale * b.getWidth());
        int sclHeight = Math.round(scale * b.getHeight());
        b = Bitmap.createScaledBitmap(b, sclWidth, sclHeight, false);
        int[] pixels = new int[sclWidth * sclHeight];
        b.getPixels(pixels, 0, sclWidth, 0, 0, sclWidth, sclHeight);
        b = Bitmap.createBitmap(dstWidth, dstHeight, b.getConfig());
        b.setPixels(pixels, 0, sclWidth, (dstWidth - sclWidth) / 2, (dstHeight - sclHeight) / 2, sclWidth, sclHeight);
        return new BitmapDrawable(getResources(), Bitmap.createBitmap(b));
    }

    private void setTextViewPandaFound(int count, int total) {
        TextView tvMine = (TextView) findViewById(R.id.tv_mine_found);
        String msg = "Found " + count + " of " + total + " Pandas";
        tvMine.setText(msg);
    }

    private void setTextViewScanCount(int count) {
        TextView tvScan = (TextView) findViewById(R.id.tv_scan_count);
        String msg = "# Scans used: " + count;
        tvScan.setText(msg);
    }

    private void setTextViewTimePlayed(int count) {
        TextView tvTime = (TextView) findViewById(R.id.tv_time_played);
        String msg = "Time played: " + timePlayed;
        tvTime.setText(msg);
    }

    private void setTextViewBestScore(int bestScore) {
        TextView tvBestScore = (TextView) findViewById(R.id.tv_best_score);
        String msg = "Best score in this configuration: " + bestScore;
        tvBestScore.setText(msg);
    }

    private void updateScan() {
        for (int i = 0; i < bushManager.getRow(); i++) {
            for (int j = 0; j < bushManager.getCol(); j++) {
                if (bushManager.hasRevealed(i, j)) {
                    String count = "" + bushManager.doScan(i, j);
                    buttons[i][j].setText(count);
                }
            }
        }
    }


    public static Intent makeLaunchIntent(Context context) {
        Intent intent = new Intent(context, GameActivity.class);
        return intent;
    }
}
