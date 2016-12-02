package com.example.andras.myapplication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Andras_Nemeth on 2016. 12. 02..
 */

public class AccountActivity extends AppCompatActivity {

    public static final String ACCOUNT_TYPE = "hu.andras.mypocapplication.account";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createAccount();
    }

    private void createAccount() {
        Account account = GenericAccountService.GetAccount(ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(account, null, null)) {
            //account didn't exist until now
        }
    }
}
