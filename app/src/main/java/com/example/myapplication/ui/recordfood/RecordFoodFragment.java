package com.example.myapplication.ui.recordfood;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.SneakyThrows;

public class RecordFoodFragment extends Fragment {

    private EditText editTextFoodName, editTextCarbohydrate, editTextFat, editTextPr, editTextDietDate, editTextDietTime;
    private Button mBtnFoodRecord, mBtnFoodSubmit;
    private UserDiet userDietInFormation;
    private String[] foodList = {"米饭", "猪肉", "牛肉","蔬菜", "牛奶"};//要填充的数据
    private String[] typeList = {"早饭", "午饭", "晚饭"};
    public RecordFoodFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_record_food, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);

        return root;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextDietDate = view.findViewById(R.id.et_food_date);
        editTextDietTime = view.findViewById(R.id.et_eat_time);
        editTextFoodName = view.findViewById(R.id.et_food_input);
        editTextCarbohydrate = view.findViewById(R.id.et_carbohydrate);
        editTextFat = view.findViewById(R.id.et_fat);
        editTextPr = view.findViewById(R.id.et_pr);
        mBtnFoodRecord = view.findViewById(R.id.btn_food_record);
        mBtnFoodSubmit = view.findViewById(R.id.btn_food_submit);


        editTextFoodName.setInputType(InputType.TYPE_NULL);
        editTextDietDate.setInputType(InputType.TYPE_NULL);
        editTextDietTime.setInputType(InputType.TYPE_NULL);

        editTextFoodName.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (editTextFoodName.getWidth() - editTextFoodName
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        editTextFoodName.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.down_arrow), null);
                        showListWindow(foodList, editTextFoodName);
                        return true;
                    }
                }
                return false;
            }
        });

        editTextDietDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });

        editTextDietTime.setInputType(InputType.TYPE_NULL);    //只能下拉选择，不支持用户输入
        editTextDietTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    showListWindow(typeList, editTextDietTime);
                    return true;
                }
                return false;

            }
        });

        mBtnFoodSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("确认提交吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @SneakyThrows
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        submitDietData();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
        mBtnFoodRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 查询用户饮食记录并显示
            }
        });
    }
    private void submitDietData() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        String dietDate = editTextDietDate.getText().toString();
        String dietTime = editTextDietTime.getText().toString();
        String carbohydrate = editTextCarbohydrate.getText().toString();
        String fat = editTextFat.getText().toString();
        String protein = editTextPr.getText().toString();

        if (!dietDate.isEmpty() && !dietTime.isEmpty() && !carbohydrate.isEmpty() && !fat.isEmpty() && !protein.isEmpty()) {
            userDietInFormation.setDate(format.parse(dietDate));
            userDietInFormation.setType(dietTime);
            userDietInFormation.setCarbohydrate(carbohydrate);
            userDietInFormation.setFat(fat);
            userDietInFormation.setProtein(protein);
            //获得用户id,foodId数据库自动生成dietId

            //Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "请将表单填写完整", Toast.LENGTH_LONG).show();
        }
    }
    private void showListWindow(final String []list, final EditText editText) {
        //todo 目前没有显示图片，后续可以根据用户的选择，增加对应的食物图片
        final ListPopupWindow listPopupWindow;
        listPopupWindow = new ListPopupWindow(getActivity());
        listPopupWindow.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list));//用android内置布局，或设计自己的样式
        listPopupWindow.setAnchorView(editTextFoodName);//以哪个控件为基准，在该处以editTextFoodName为基准
        listPopupWindow.setModal(true);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {//设置项点击监听
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(list[i]);//把选择的选项内容展示在EditText上
                listPopupWindow.dismiss();//如果已经选择了，隐藏起来
            }
        });
        listPopupWindow.show();//把ListPopWindow展示出来
    }
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();

        //THEME_HOLO_LIGHT,
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                editTextDietDate.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        //fixme 弹出软键盘bug修复, 回车不接受
        datePickerDialog.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        // 弹出Dialog的时候会弹出输入法，将弹出的输入法关闭。必须在show()方法之后调用
        datePickerDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}