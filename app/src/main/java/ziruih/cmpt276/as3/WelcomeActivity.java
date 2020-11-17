/*
    Manage Welcome Page, set up animation
    Reference List
    * Animated Gradient Background: https://www.youtube.com/watch?v=x_DXXGvyfh8&ab_channel=CodinginFlow
    * Blink button: https://www.youtube.com/watch?v=D24UQG364PA&t=448s&ab_channel=AlanRanjoni
    * Item falling animation

 */
package ziruih.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setupButton();
        itemFalling();
    }



    private void setupButton() {
        Button btn = (Button) findViewById(R.id.btn_main_menu);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink);
        btn.startAnimation(animation);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.makeLaunchIntent(WelcomeActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    private void itemFalling() {
        ImageView imageView1, imageView2, imageView3, imageView4, imageView5;
        ObjectAnimator ob1, ob2, ob3, ob4, ob5;
        imageView1 = (ImageView) findViewById(R.id.money1);
        imageView2 = (ImageView) findViewById(R.id.money2);
        imageView3 = (ImageView) findViewById(R.id.money3);
        imageView4 = (ImageView) findViewById(R.id.money4);
        imageView5 = (ImageView) findViewById(R.id.money5);

        ob1 = ObjectAnimator.ofFloat(imageView1, "y", 1000);
        ob2 = ObjectAnimator.ofFloat(imageView2, "y", 1000);
        ob3 = ObjectAnimator.ofFloat(imageView3, "y", 1000);
        ob4 = ObjectAnimator.ofFloat(imageView4, "y", 1000);
        ob5 = ObjectAnimator.ofFloat(imageView5, "y", 1000);

        ob1.setDuration(4000).setRepeatCount(ObjectAnimator.INFINITE);
        ob2.setDuration(3000).setRepeatCount(ObjectAnimator.INFINITE);
        ob3.setDuration(5000).setRepeatCount(ObjectAnimator.INFINITE);
        ob4.setDuration(6000).setRepeatCount(ObjectAnimator.INFINITE);
        ob5.setDuration(3000).setRepeatCount(ObjectAnimator.INFINITE);

        ob1.start();
        ob2.start();
        ob3.start();
        ob4.start();
        ob5.start();
    }
}