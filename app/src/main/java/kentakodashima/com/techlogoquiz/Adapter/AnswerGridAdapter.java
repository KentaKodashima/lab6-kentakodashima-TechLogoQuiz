package kentakodashima.com.techlogoquiz.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class AnswerGridAdapter extends BaseAdapter {

  private char[] answerChar;
  private Context context;

  public AnswerGridAdapter(char[] answerChar, Context context) {
    this.answerChar = answerChar;
    this.context = context;
  }

  @Override
  public int getCount() {
    return answerChar.length;
  }

  @Override
  public Object getItem(int position) {
    return answerChar[position];
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    Button button;
    if (view == null) {
      button = new Button(context);
      button.setLayoutParams(new GridView.LayoutParams(85, 85));
      button.setPadding(8,8,8,8);
      button.setBackgroundColor(Color.DKGRAY);
      button.setTextColor(Color.YELLOW);
      button.setText(String.valueOf(answerChar[position]));
    } else {
      button = (Button) view;
    }
    return button;
  }
}
