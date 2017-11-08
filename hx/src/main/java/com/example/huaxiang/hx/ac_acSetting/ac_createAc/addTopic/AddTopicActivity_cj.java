package com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.CreateAcActivity_cj;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic.adapter.AddTopicAdapter;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic.m.TopicEditData;
import com.example.huaxiang.hx.ac_bb.m.HxTopic;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;


@RequiresPresenter(AddTopicPresenter_cj.class)
public class AddTopicActivity_cj extends BaseActivity<AddTopicPresenter_cj> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    String id;
    Switch switch_duoxuan;
    EditText et_question;
    RecyclerView rv_addTopic;
    ArrayList<TopicEditData> data = new ArrayList<>();

    HxTopic hxTopic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_topic_hx;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("新增问卷");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        switch_duoxuan = findView(R.id.switch_duoxuan);
        et_question = findView(R.id.et_question);
        rv_addTopic = findView(R.id.rv_addTopic);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        id = getIntent().getStringExtra("id");
        hxTopic = (HxTopic) getIntent().getSerializableExtra("hxTopic");

        if (hxTopic == null) {
            if (data.size() == 0) {
                data.add(new TopicEditData());
            }
            setRv();
        } else {
            String[] topicOptions = hxTopic.option.split(";");
            String[] topicAnswers = hxTopic.answer.split(",");

            ArrayList<String> topicAnswersstr = new ArrayList<>();
            for (String str : topicAnswers) {
                topicAnswersstr.add(topicOptions[Integer.parseInt(str)-1]);
            }

            for (String str : topicOptions) {
                TopicEditData ted = new TopicEditData();
                ted.isOption = true;
                ted.text = str;
                data.add(ted);
            }
            for (String str : topicAnswersstr) {
                TopicEditData ted = new TopicEditData();
                ted.isOption = false;
                ted.text = str;
                data.add(ted);
            }
            setRv();
            switch_duoxuan.setChecked(hxTopic.type == 1 ? false : true);
            et_question.setText(hxTopic.question);

        }

    }

    void setRv(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        AddTopicAdapter adapter = new AddTopicAdapter(context, data);
        rv_addTopic.setLayoutManager(layoutManager);
        rv_addTopic.setAdapter(adapter);

    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.bt_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = et_question.getText().toString();
                int type = switch_duoxuan.isChecked() ? 2 : 1;
                String option = "";
                String answer = "";

                if (question.isEmpty() || haveEmpty()) {
                    Toast.makeText(context, "请填写完整", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<String> options = new ArrayList<String>();
                    ArrayList<String> answers = new ArrayList<String>();
                    for (TopicEditData ted : data) {
                        if (ted.isOption) {
                            options.add(ted.text);
                        } else {
                            answers.add(ted.text);
                        }
                    }
                    if (options.size() == 0) {
                        Toast.makeText(context, "请添加选项", Toast.LENGTH_SHORT).show();
                    } else if (answers.size() == 0) {
                        Toast.makeText(context, "请添加答案", Toast.LENGTH_SHORT).show();
                    } else if (answers.size() > options.size()) {
                        Toast.makeText(context, "答案数量不应多于选项数量", Toast.LENGTH_SHORT).show();
                    } else {
                        if (type==2) {
                            if (!matchDuoxuanWrong(options, answers)) {
                                option = getOptionStr(options);
                                answer = getAnswerStr(options, answers);
                                saveData(question, type, option, answer);
                            }
                        } else {
                            if (answers.size() > 1) {
                                Toast.makeText(context, "单选只能有一个答案", Toast.LENGTH_SHORT).show();
                            } else if (!matchDanxuanWrong(answers.get(0), options)) {
                                option = getOptionStr(options);
                                answer = getAnswerStr(options, answers);
                                saveData(question, type, option, answer);
                            }
                        }
                    }
                }
            }
        });
    }

    boolean haveEmpty(){
        for (TopicEditData ted : data) {
            if (ted.text.equals("")) {
                return true;
            }
        }
        return false;
    }

    boolean matchDuoxuanWrong(ArrayList<String> strsOption, ArrayList<String> strsAnswer) {
        for (String str : strsAnswer) {
            boolean notMatch = true;
            for (String str2 : strsOption) {
                if (str.equals(str2)) {
                    notMatch = false;
                }
            }
            if (notMatch) {
                Toast.makeText(context, str + " 未匹配到选项", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    boolean matchDanxuanWrong(String answer, ArrayList<String> strsOption) {
        boolean notMatch = true;
        for (String str2 : strsOption) {
            if (answer.equals(str2)) {
                notMatch = false;
            }
        }
        if (notMatch) {
            Toast.makeText(context, answer + " 未匹配到选项", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    String getOptionStr(ArrayList<String> strings) {
        String str = "";
        for (String strr : strings) {
            str += strr + ";";
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }

    String getAnswerStr(ArrayList<String> strsOption, ArrayList<String> strsAnswer) {
        String str = "";
        for (String stra : strsAnswer) {
            for (int i = 0; i < strsOption.size(); i++) {
                if (stra.equals(strsOption.get(i))) {
                    str = str + (i + 1) + ",";
                }
            }
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }


    void saveData(String question, int type, String option, String answer){
        if (hxTopic != null) {
            editAward(question, type, option, answer);
        } else {
            addAward(question, type, option, answer);
        }
    }
    void addAward(String question, int type, String option, String answer) {
        HttpMethods.start(HttpMethods.getInstance().demoService.addTopic(token, question, type, option, answer, id), new Subscriber<Response>() {
            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response arrayListResponse) {
                if (arrayListResponse.code == 0) {
                    Toast.makeText(context, "添加问卷成功", Toast.LENGTH_SHORT).show();
                    AddTopicListActivity_pt.instance.getData();
                    CreateAcActivity_cj.instance.getData();

                    finish();
                }
            }
        });

    }
    void editAward(String question, int type, String option, String answer){
        HttpMethods.start(HttpMethods.getInstance().demoService.editTopic(token, question, type, option, answer, id, hxTopic.id), new Subscriber<Response>() {
            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response arrayListResponse) {
                if (arrayListResponse.code == 0) {
                    Toast.makeText(context, "修改问卷成功", Toast.LENGTH_SHORT).show();
                    AddTopicListActivity_pt.instance.getData();
                    CreateAcActivity_cj.instance.getData();

                    finish();
                }
            }
        });

    }

}