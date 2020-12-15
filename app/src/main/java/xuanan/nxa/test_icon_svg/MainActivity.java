package xuanan.nxa.test_icon_svg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void loadSlides(View view) {
        new PreferenceManager(this).clearPrefrence();
        startActivity(new Intent(this,WelcomeActivity.class));
        finish();
    }
}