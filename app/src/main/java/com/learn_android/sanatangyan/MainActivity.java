package com.learn_android.sanatangyan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView AppTitle;
    CardView titleCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         AppTitle= (TextView) findViewById(R.id.title);
         titleCard = (CardView) findViewById(R.id.titleCard);
         String geetaSuperSiteUrl = "https://www.gitasupersite.iitk.ac.in/";
        new GetTitleTask().execute(geetaSuperSiteUrl);
    }

    private class GetTitleTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String title = null;
            HashMap<String,String> firstPage = new HashMap<>();
            try {
                // Connect to the website and retrieve the HTML content
                Document doc = Jsoup.connect(urls[0]).get();
                Log.d("Doc",
                        doc.title() +
                                doc.location() +
                                doc.nodeName() +
                                doc.id() +
                                doc.outerHtml()
                );
                // Get the title element from the HTML
                Element titleElement = doc.select("title").first();

                // Extract the title text from the element
                title = titleElement.text();
                firstPage.put("Title",title);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return title;
        }
        @Override
        protected void onPostExecute(String title) {
            // Get the ActionBar or Toolbar
            actionBarData(title);
            // Print the title
            System.out.println(title);
        }

        private void actionBarData(String title){
            ActionBar actionBar = getSupportActionBar();
        // Set the title of the ActionBar or Toolbar with custom text size and color
            SpannableString Apptitle = new SpannableString(title);
            Apptitle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange_shade1)), 0, Apptitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            Apptitle.setSpan(new AbsoluteSizeSpan(60), 0, Apptitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            actionBar.setTitle(Apptitle);

        // Set the background color of the ActionBar or Toolbar
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.ligh_orange_shade1))); // using setBackgroundDrawable() method
        }
    }
}