/*
Manage Option Page
 */

package ziruih.cmpt276.as3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import ziruih.cmpt276.as3.model.BushManager;

public class OptionActivity extends AppCompatActivity {

    private BushManager bushManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        bushManager = BushManager.getInstance();
        createBoardRadioButtons();
        createMineRadioButtons();
        setupEraseButton();
        setupResetButton();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }

    private void createBoardRadioButtons() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_group_board);
        int [] rowOption = getResources().getIntArray(R.array.board_size_row);
        int [] colOption = getResources().getIntArray(R.array.board_size_col);
        // Create the buttons

        for (int i = 0; i < rowOption.length; i++) {
            final int numRow = rowOption[i];
            final int numCol = colOption[i];
            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.board_option, numRow, numCol));
            button.setTextSize(16);

            // Set on-click callbacks
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bushManager.setRow(numRow);
                    bushManager.setCol(numCol);
                    saveBoardState(numRow, numCol);
                }
            });

            // Add to radio group
            group.addView (button);

            // Select default button;
            if (numRow == getBoardState(this)[0] && numCol == getBoardState(this)[1]) {
                button.setChecked(true);
            }
        }
    }

    private void createMineRadioButtons() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_group_mine);
        int[] mineOption = getResources().getIntArray((R.array.mine_number));
        for (int i = 0; i < mineOption.length; i++) {
            final int numMine = mineOption[i];
            RadioButton button = new RadioButton(this);
            button.setText(""+numMine);
            button.setTextSize(16);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bushManager.setPandaCount(numMine);
                    savePandaCount(numMine);
                }
            });

            group.addView(button);
            if (numMine == getNumPanda(this)) {
                button.setChecked(true);
            }
        }
    }




    public static int[] getBoardState(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        int default_row = context.getResources().getInteger(R.integer.default_row_num);
        int default_col = context.getResources().getInteger(R.integer.default_col_num);

        int row = prefs.getInt(ROW_PREF_NAME, default_row);
        int col = prefs.getInt(COL_PREF_NAME,default_col);
        return new int[] {row, col};
    }

    public static int getNumPanda(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        int default_panda = context.getResources().getInteger(R.integer.default_panda_num);
        int numMine = prefs.getInt(PANDA_PREF_NAME, default_panda);
        return numMine;
    }

    public static Intent makeLaunchIntent(Context context) {
        Intent intent = new Intent(context, OptionActivity.class);
        return intent;
    }

    private void setupEraseButton() {
        Button btn = (Button) findViewById(R.id.btn_erase);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionActivity.savePlayTime(OptionActivity.this, 0);
                Toast.makeText(OptionActivity.this, "play time erased", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupResetButton() {
        Button btn = (Button) findViewById(R.id.btn_reset_record);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllRecord(OptionActivity.this);
                Toast.makeText(OptionActivity.this, "Reset all record", Toast.LENGTH_SHORT).show();
            }

        });
    }




    private static final String ROW_PREF_NAME = "Num Row";
    private static final String COL_PREF_NAME = "Num Col";
    private static final String PANDA_PREF_NAME = "Num panda";
    private static final String PREFS_NAME = "AppPrefs";
    private static final String NUM_PLAY = "Num Play";
    private static final String FOUR_SIX = "FourSix";
    private static final String FOUR_TEN = "FourTen";
    private static final String FOUR_FIFTEEN = "FourFifteen";
    private static final String FOUR_TWENTY = "FourTwenty";
    private static final String FIVE_SIX = "FiveSix";
    private static final String FIVE_TEN = "FiveTen";
    private static final String FIVE_FIFTEEN = "FiveFifteen";
    private static final String FIVE_TWENTY = "FiveTwenty";
    private static final String SIX_SIX = "SixSix";
    private static final String SIX_TEN = "SixTen";
    private static final String SIX_FIFTEEN = "SixFifteen";
    private static final String SIX_TWENTY = "SixTwenty";
    private static final String EIGHT_SIX = "EightSix";
    private static final String EIGHT_TEN = "EightTen";
    private static final String EIGHT_FIFTEEN = "EightFifteen";
    private static final String EIGHT_TWENTY = "EightTwenty";

    private void savePandaCount(int numMine) {
        SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(PANDA_PREF_NAME, numMine);
        editor.apply();
    }

    private void saveBoardState(int numRow, int numCol) {
        SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(ROW_PREF_NAME, numRow);
        editor.putInt(COL_PREF_NAME, numCol);
        editor.apply();
    }

    public static void savePlayTime(Context context, int playTime) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUM_PLAY, playTime);
        editor.apply();
    }

    public static int getPlayTime(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int numPlay = prefs.getInt(NUM_PLAY, 0);
        return numPlay;
    }



    public static void saveBestRecord(Context context, int row, int num, int bestRecord) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (row == 4) {
            if (num == 6) {
                editor.putInt(FOUR_SIX, bestRecord);
            } else if (num == 10) {
                editor.putInt(FOUR_TEN, bestRecord);
            } else if (num == 15) {
                editor.putInt(FOUR_FIFTEEN, bestRecord);
            } else if (num == 20) {
                editor.putInt(FOUR_TWENTY, bestRecord);
            }
        } else if (row == 5) {
            if (num == 6) {
                editor.putInt(FIVE_SIX, bestRecord);
            } else if (num == 10) {
                editor.putInt(FIVE_TEN, bestRecord);
            } else if (num == 15) {
                editor.putInt(FIVE_FIFTEEN, bestRecord);
            } else if (num == 10) {
                editor.putInt(FIVE_TWENTY, bestRecord);
            }
        } else if (row == 6) {
            if (num == 6) {
                editor.putInt(SIX_SIX, bestRecord);
            } else if (num == 10) {
                editor.putInt(SIX_TEN, bestRecord);
            } else if (num == 15) {
                editor.putInt(SIX_FIFTEEN, bestRecord);
            } else if (num == 10) {
                editor.putInt(SIX_TWENTY, bestRecord);
            }
        } else {
            if (num == 8) {
                editor.putInt(EIGHT_SIX, bestRecord);
            } else if (num == 10) {
                editor.putInt(EIGHT_TEN, bestRecord);
            } else if (num == 15) {
                editor.putInt(EIGHT_FIFTEEN, bestRecord);
            } else if (num == 10) {
                editor.putInt(EIGHT_TWENTY, bestRecord);
            }
        }
        editor.apply();
    }


    public static int getBestRecord(Context context, int row, int num) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int bestRecord = 0;
        if (row == 4) {
            if (num == 6) {
                bestRecord =  prefs.getInt(FOUR_SIX, 18);
            } else if (num == 10) {
                bestRecord = prefs.getInt(FOUR_TEN, 14);
            } else if (num == 15) {
                bestRecord = prefs.getInt(FOUR_FIFTEEN, 9);
            } else if (num == 20) {
                bestRecord = prefs.getInt(FOUR_TWENTY, 4);
            }
        } else if (row == 5) {
            if (num == 6) {
                bestRecord = prefs.getInt(FIVE_SIX, 44);
            } else if (num == 10) {
                bestRecord = prefs.getInt(FIVE_TEN, 40);
            } else if (num == 15) {
                bestRecord = prefs.getInt(FIVE_FIFTEEN, 35);
            } else if (num == 20) {
                bestRecord = prefs.getInt(FIVE_TWENTY, 30);
            }
        } else if (row == 6) {
            if (num == 6) {
                bestRecord = prefs.getInt(SIX_SIX, 84);
            } else if (num == 10) {
                bestRecord = prefs.getInt(SIX_TEN, 80);
            } else if (num == 15) {
                bestRecord = prefs.getInt(SIX_FIFTEEN, 75);
            } else if (num == 20) {
                bestRecord = prefs.getInt(SIX_TWENTY, 70);
            }
        } else {
            if (num == 6) {
                bestRecord = prefs.getInt(EIGHT_SIX, 138);
            } else if (num == 10) {
                bestRecord = prefs.getInt(EIGHT_TEN, 134);
            } else if (num == 15) {
                bestRecord = prefs.getInt(EIGHT_FIFTEEN, 129);
            } else if (num == 20) {
                bestRecord = prefs.getInt(EIGHT_TWENTY, 124);
            }
        }
        return bestRecord;
    }

    private void clearAllRecord (Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(FOUR_SIX, 18);
        editor.putInt(FOUR_TEN, 14);
        editor.putInt(FOUR_FIFTEEN, 9);
        editor.putInt(FOUR_TWENTY, 4);
        editor.putInt(FIVE_SIX, 44);
        editor.putInt(FIVE_TEN, 40);
        editor.putInt(FIVE_FIFTEEN, 35);
        editor.putInt(FIVE_TWENTY, 30);
        editor.putInt(SIX_SIX, 84);
        editor.putInt(SIX_TEN, 80);
        editor.putInt(SIX_FIFTEEN, 75);
        editor.putInt(SIX_TWENTY, 70);
        editor.putInt(EIGHT_SIX, 138);
        editor.putInt(EIGHT_TEN, 134);
        editor.putInt(EIGHT_FIFTEEN, 129);
        editor.putInt(EIGHT_TWENTY, 124);
        editor.apply();
    }







}