package com.alu.tat.view;

import com.alu.tat.Main;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.VerticalLayout;

import com.vaadin.ui.*;

/**
 * Created by
 * User: vkhodyre
 * Date: 7/10/2015
 */
public class ErrorView extends VerticalLayout implements View {

    private Navigator navigator;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = getUI().getNavigator();
        VerticalLayout layout = new VerticalLayout();
        Label label = new Label("<b>Sorry! The requested page doesn't exist!</b><br/>", ContentMode.HTML);
        layout.addComponent(label);

        Button backBut = new Button("Get Back");
        layout.addComponent(backBut);
        backBut.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.navigateTo(Main.MAIN_VIEW);
            }
        });


        addComponent(layout);
    }
}