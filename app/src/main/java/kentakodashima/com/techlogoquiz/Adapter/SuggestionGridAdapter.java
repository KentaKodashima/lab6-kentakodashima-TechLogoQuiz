package kentakodashima.com.techlogoquiz.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.Collection;
import java.util.List;

import kentakodashima.com.techlogoquiz.Common.Common;
import kentakodashima.com.techlogoquiz.MainActivity;

public class SuggestionGridAdapter extends BaseAdapter {

  private List<String> suggestionSource;
  private Context context;
  private MainActivity mainActivity;

  public SuggestionGridAdapter(List<String> suggestionSource, Context context, MainActivity mainActivity) {
    this.suggestionSource = suggestionSource;
    this.context = context;
    this.mainActivity = mainActivity;
  }

  @Override
  public int getCount() {
    return suggestionSource.size();
  }

  @Override
  public Object getItem(int position) {
    return suggestionSource.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(final int position, View view, ViewGroup viewGroup) {
    Button button;
    if (view == null) {
      if (suggestionSource.get(position).equals("null")) {
        button = new Button(context);
        button.setLayoutParams(new GridView.LayoutParams(85, 85));
        button.setPadding(8,8,8,8);
        button.setBackgroundColor(Color.DKGRAY);
      } else {
        button = new Button(context);
        button.setLayoutParams(new GridView.LayoutParams(85, 85));
        button.setPadding(8,8,8,8);
        button.setBackgroundColor(Color.DKGRAY);
        button.setTextColor(Color.YELLOW);
        button.setText(suggestionSource.get(position));
        button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            // If correct answer contains char the user selects
            if (String.valueOf(mainActivity.answer).contains(suggestionSource.get(position))) {
              // Get char
              char compare = suggestionSource.get(position).charAt(0);

              for (int i = 0; i < mainActivity.answer.length; i++) {
                if (compare == mainActivity.answer[i]) {
                  Common.USER_ANSWER[i] = compare;
                }
              }

              // Update UI
              AnswerGridAdapter answerGridAdapter = new AnswerGridAdapter(Common.USER_ANSWER, context);
              mainActivity.answerGrid.setAdapter(answerGridAdapter);
              answerGridAdapter.notifyDataSetChanged();

              // Remove from suggestion list
              mainActivity.suggestionSource.set(position, "null");
              mainActivity.suggestionAdapter = new SuggestionGridAdapter(mainActivity.suggestionSource, context, mainActivity);
              mainActivity.suggestionGrid.setAdapter(mainActivity.suggestionAdapter);
              mainActivity.suggestionAdapter.notifyDataSetChanged();
            } else {
              // Remove from suggestion list
              mainActivity.suggestionSource.set(position, "null");
              mainActivity.suggestionAdapter = new SuggestionGridAdapter(mainActivity.suggestionSource, context, mainActivity);
              mainActivity.suggestionGrid.setAdapter(mainActivity.suggestionAdapter);
              mainActivity.suggestionAdapter.notifyDataSetChanged();
            }
          }
        });
      }
    } else {
      button = (Button) view;
    }

    return button;
  }
}
