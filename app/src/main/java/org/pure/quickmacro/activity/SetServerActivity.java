package org.pure.quickmacro.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.helper.StringUtil;

import butterknife.BindView;

import org.pure.quickmacro.R;
import org.pure.quickmacro.util.PreferenceUtil;

/**
 * @author pure
 * @date 18-6-20 上午1:56
 * @description 设置服务器activity
 */
public class SetServerActivity extends BaseActivity {
    @BindView(R.id.tv_current_server)
    TextView tvCurrentServer;
    @BindView(R.id.et_server)
    EditText etServer;
    @BindView(R.id.btn_ensure)
    Button btnEnsure;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_set_server);
    }

    @Override
    protected void initData() {
        tvCurrentServer.setText(PreferenceUtil.getString("API_HOST", "127.0.0.1:10000"));
    }

    @Override
    protected void setListener() {
        btnEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String server = etServer.getText().toString();
                if (!StringUtil.isBlank(server)) {
                    if (PreferenceUtil.putString("API_HOST", server)) {
                        tvCurrentServer.setText(server);
                        toast("设置服务器地址成功");
                    } else {
                        toast("设置服务器地址失败");
                    }
                } else {
                    toast("服务器地址不能为空");
                }
            }
        });
    }
}
