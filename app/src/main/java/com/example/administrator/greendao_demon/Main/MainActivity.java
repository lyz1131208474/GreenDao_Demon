package com.example.administrator.greendao_demon.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.arp.greendemo.DaoMaster;
import com.bw.arp.greendemo.DaoSession;
import com.bw.arp.greendemo.UserDao;
import com.example.administrator.greendao_demon.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_age)
    EditText mEtAge;
    @BindView(R.id.et_sex)
    EditText mEtSex;
    @BindView(R.id.et_salary)
    EditText mEtSalary;
    @BindView(R.id.et_id)
    EditText mEtId;
    @BindView(R.id.bt_insert)
    Button mBtInsert;
    @BindView(R.id.bt_select)
    Button mBtSelect;
    @BindView(R.id.bt_delete)
    Button mBtDelete;
    @BindView(R.id.bt_update)
    Button mBtUpdate;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    private UserDao userDao;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "pwk.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();
    }

    @OnClick({R.id.et_name, R.id.et_age, R.id.et_sex, R.id.et_salary, R.id.et_id, R.id.bt_insert, R.id.bt_select, R.id.bt_delete, R.id.bt_update, R.id.rlv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_insert:
                insert();
                break;
            case R.id.bt_select:
                select();
                break;
            case R.id.bt_delete:
                delete();
                break;
            case R.id.bt_update:
                update();
                break;
        }
    }

    private void update() {
//       List<User> user2 = userDao.queryBuilder()
//               .where(UserDao.Properties.Name.eq("刘阳洲")).build().list();
//       if (user2 == null){
//           Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();
//       }else {
//           for (int i = 0;i<user2.size();i++){
//               user2.get(i).setName("二狗子");
//               userDao.update(user2.get(i));
//           }
//       }


        String id = mEtId.getText().toString().trim();
        long ids = Long.parseLong(id);


        String s = mEtName.getText().toString();
        String age = mEtName.getText().toString();
        String Salar = mEtSalary.getText().toString();
        String sex = mEtSex.getText().toString();
        User user = new User(ids,s,age,Salar,sex);
        userDao.update(user);

        Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
        select();
    }

    private void delete() {
        String id = mEtId.getText().toString().trim();
        userDao.queryBuilder().where(UserDao.Properties.Id.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities();
        Toast.makeText(this,"删除成功~",Toast.LENGTH_SHORT).show();
        select();
    }

    private void select() {
        List<User> users = userDao.loadAll();
        MyAdapter adapter = new MyAdapter(users, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(linearLayoutManager);
        mRlv.setAdapter(adapter);
    }

    private void insert() {
        name = mEtName.getText().toString().trim();
        String age = mEtAge.getText().toString().trim();
        String sex = mEtSex.getText().toString().trim();
        String salary = mEtSalary.getText().toString().trim();
        if (name.isEmpty()&&age.isEmpty()&&sex.isEmpty()&&salary.isEmpty()){
            Toast.makeText(this,"请输入完整内容",Toast.LENGTH_SHORT).show();
        }else {


            User user = new User(null, name, age, sex, salary);
            long insert = userDao.insert(user);
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();

        }
    }
}
