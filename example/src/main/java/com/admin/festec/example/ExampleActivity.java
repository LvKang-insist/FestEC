package com.admin.festec.example;

import com.diabin.latte.activitys.ProxyActivity;
import com.diabin.latte.deleggate.LatteDelegate;



public class ExampleActivity extends ProxyActivity {
    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
