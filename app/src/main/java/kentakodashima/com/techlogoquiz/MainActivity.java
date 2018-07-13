package kentakodashima.com.techlogoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import kentakodashima.com.techlogoquiz.Adapter.AnswerGridAdapter;
import kentakodashima.com.techlogoquiz.Adapter.SuggestionGridAdapter;
import kentakodashima.com.techlogoquiz.Common.Common;

public class MainActivity extends AppCompatActivity {

  public List<String> suggestionSource = new ArrayList<>();
  public AnswerGridAdapter answerAdapter;
  public SuggestionGridAdapter suggestionAdapter;

  public Button submitButton;
  public GridView answerGrid, suggestionGrid;
  public ImageView questionImage;

  int[] IMAGE_LIST = {
          R.drawable.blogger,
          R.drawable.deviantart,
          R.drawable.digg,
          R.drawable.dropbox,
          R.drawable.evernote,
          R.drawable.facebook,
          R.drawable.flickr,
          R.drawable.google,
          R.drawable.googleplus,
          R.drawable.hyves,
          R.drawable.instagram,
          R.drawable.linkedin,
          R.drawable.myspace,
          R.drawable.picasa,
          R.drawable.pinterest,
          R.drawable.reddit,
          R.drawable.rss,
          R.drawable.skype,
          R.drawable.soundcloud,
          R.drawable.stumbleupon,
          R.drawable.twitter,
          R.drawable.wordpress,
          R.drawable.vimeo,
          R.drawable.yahoo,
          R.drawable.youtube
  };

  public char[] answer;

  String CORRECT_ANSWER;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initViews();
  }

  public void initViews() {
    answerGrid = (GridView) findViewById(R.id.answerGrid);
    suggestionGrid = (GridView) findViewById(R.id.suggestionGrid);

    questionImage = (ImageView) findViewById(R.id.logoImage);

    // Add setList
    setList();

    submitButton = (Button) findViewById(R.id.submitButton);
    submitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String result = "";
        for (int i = 0; i < Common.USER_ANSWER.length; i++) {
          result += String.valueOf(Common.USER_ANSWER[i]);
        }

        if (result.equals(CORRECT_ANSWER)) {
          Toast.makeText(getApplicationContext(), "Your Answer is " + result, Toast.LENGTH_SHORT).show();

          // Reset
          Common.COUNT = 0;
          Common.USER_ANSWER = new char[CORRECT_ANSWER.length()];

          // Set Adapter
          AnswerGridAdapter answerGridAdapter = new AnswerGridAdapter(setNullList(), getApplicationContext());
          answerGrid.setAdapter(answerGridAdapter);
          answerGridAdapter.notifyDataSetChanged();

          SuggestionGridAdapter suggestionGridAdapter = new SuggestionGridAdapter(suggestionSource, getApplicationContext(), MainActivity.this);
          suggestionGrid.setAdapter(suggestionGridAdapter);
          suggestionGridAdapter.notifyDataSetChanged();

          setList();
        } else {
          Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private void setList() {
    // Random logo
    Random random = new Random();
    int imageSelected = IMAGE_LIST[random.nextInt(IMAGE_LIST.length)];
    questionImage.setImageResource(imageSelected);

    CORRECT_ANSWER = getResources().getResourceName(imageSelected);
    CORRECT_ANSWER = CORRECT_ANSWER.substring(CORRECT_ANSWER.lastIndexOf("/") + 1);

    answer = CORRECT_ANSWER.toCharArray();

    Common.USER_ANSWER = new char[answer.length];

    // Add answer chars to list
    suggestionSource.clear();
    for (char item: answer) {

      // Add long name to list
      suggestionSource.add(String.valueOf(item));
    }

    // Random add some chars to list
    for (int i = answer.length; i < answer.length * 2; i++) {
      suggestionSource.add(Common.ALPHABET_CHARS[random.nextInt(Common.ALPHABET_CHARS.length)]);
    }

    // Sort random
    Collections.shuffle(suggestionSource);

    // Setting for GridView
    answerAdapter = new AnswerGridAdapter(setNullList(), this);
    suggestionAdapter = new SuggestionGridAdapter(suggestionSource, this, this);

    answerAdapter.notifyDataSetChanged();
    suggestionAdapter.notifyDataSetChanged();

    suggestionGrid.setAdapter(suggestionAdapter);
    answerGrid.setAdapter(answerAdapter);
  }

  private char[] setNullList() {
    char result[] = new char[answer.length];
    for (int i =0; i < answer.length; i++) {
      result[i] = ' ';
    }
    return result;
  }
}
