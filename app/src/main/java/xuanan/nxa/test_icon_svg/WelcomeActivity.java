package xuanan.nxa.test_icon_svg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mPager;
    private int[] layouts = {R.layout.slide1,R.layout.slide2,R.layout.slide3, R.layout.slide4};
    private  MpagerAdapter mpagerAdapter;

    private LinearLayout Dots_Layout;
    private ImageView[] dots;

    private Button btnNext,btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(new PreferenceManager(this).checkPreference()){
            loadHome();
        }

        if(Build.VERSION.SDK_INT >= 19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_welcome);

        mPager = (ViewPager) findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);

        Dots_Layout = (LinearLayout) findViewById(R.id.dotsLayout);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        btnSkip = (Button) findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(this);
        creatDots(0);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                creatDots(position);
                if(position == layouts.length-1){
                    btnNext.setText("Start");
                    btnSkip.setVisibility(View.INVISIBLE);
                }else {
                    btnNext.setText("Next");
                    btnSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void creatDots(int current_position){
        if(Dots_Layout != null){
            Dots_Layout.removeAllViews();

            dots = new ImageView[layouts.length];

            for(int i=0;i<layouts.length;i++){
                dots[i] = new ImageView(this);
                if(i == current_position){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
                }else {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dots));
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(4,0,4,0);
                Dots_Layout.addView(dots[i],params);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNext:
                loadNextSlide();
                break;
            case R.id.btnSkip:
                loadHome();
                new PreferenceManager(this).writePreference();
                break;
        }
    }

    private void loadHome(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    private void loadNextSlide(){
        int next_slide = mPager.getCurrentItem()+1;
        if(next_slide<layouts.length){
            mPager.setCurrentItem(next_slide);
        }else {
            loadHome();
            new PreferenceManager(this).writePreference();
        }
    }
}