package org.ea.finance.onlinebankingapp.ui;

import java.util.ArrayList;
import java.util.List;

public enum GUIOptions {
    Login(LoginCentralPanel.class,true),
    Home(HomeCentralPanel.class,true),
    Accounts(AccountsPanel.class,true),
    Transactions(TransactionsPanel.class,true),
    Payments(PaymentsPanel.class,true),
    AddAccount(AddAccountPanel.class,false),
    MakePayment(MakePaymentPanel.class,false),
    AddCustomer(AddCustomerPanel.class,true),
    Logout(LoginCentralPanel.class,true);

    private Class<?> panelClass;
    private boolean isMenuItem;

    GUIOptions(Class<?> panelClass,boolean isMenuItem) {
        this.panelClass = panelClass;
        this.isMenuItem = isMenuItem;
    }

    public Class<?> getPanelClass() {
        return panelClass;
    }

    public boolean getIsMenuItem()
    {
        return isMenuItem;
    }

    public static List<String> getMenuItems() {
        List<String> menuOptions = new ArrayList<>();
        for (GUIOptions option : GUIOptions.values()) {
            if (option.getIsMenuItem()) {
                menuOptions.add(String.valueOf(option));
            }
        }
        return menuOptions;
    }
}
