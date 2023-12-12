package com.example.aestheticaevent.HomeScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aestheticaevent.HomeScreen.Adapters.SubCateAdapter;
import com.example.aestheticaevent.HomeScreen.Fragment.CompleteFragment;
import com.example.aestheticaevent.HomeScreen.Fragment.FragmentFilter;
import com.example.aestheticaevent.HomeScreen.Fragment.UpComingFragment;
import com.example.aestheticaevent.HomeScreen.HomeResponse.SubCategoryListResponse;
import com.example.aestheticaevent.R;
import com.example.aestheticaevent.Utils.Tools;
import com.example.aestheticaevent.Utils.VariableBag;
import com.example.aestheticaevent.network.RestClient;
import com.example.aestheticaevent.network.Restcall;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ActivitySubEvent extends AppCompatActivity {
    TabLayout tab1;
    ViewPager2 view1;
    String categoryId,categoryName;
    UpComingFragment upcommingFragment;
    CompleteFragment completeFragment;
    ImageView ivProfileBack;
    TextView EventSubId;
    Tools tools;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_event);
        ivProfileBack=findViewById(R.id.ivProfileBack);
        EventSubId=findViewById(R.id.EventSubId);
        tools=new Tools(this);
        tools.ScreenshotBlock(getWindow());
        ivProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        Intent i = getIntent();
        if (i != null) {
            categoryId = i.getStringExtra("categoryId");
           String EventSub=i.getStringExtra("categoryName");

           EventSubId.setText(EventSub);

            tab1 = findViewById(R.id.tab1);
            view1 = findViewById(R.id.view1);
            upcommingFragment = new UpComingFragment();

            view1.setAdapter(new ViewPagerAdapter(ActivitySubEvent.this));

            new TabLayoutMediator(tab1, view1, (tab, position) -> {
                if (position == 0)
                    tab.setText("UpComing");
                else
                    tab.setText("Completed");
            }).attach();

        }
    }

    public static class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0)
                return new UpComingFragment();
            else
                return new CompleteFragment();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}