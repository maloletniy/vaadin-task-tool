package com.alu.tat.view.menu;

import com.alu.tat.entity.Task;
import com.alu.tat.entity.User;
import com.alu.tat.service.TaskService;
import com.alu.tat.service.UserService;
import com.alu.tat.view.UIConstants;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by
 * User: vkhodyre
 * Date: 5/19/2016
 */
public class TaskPopupMenu extends VerticalLayout implements PopupMenuManager.PopupContent {
    private Task item;
    private Window window;

    public TaskPopupMenu(Task item) {
        super();
        this.item = item;
        initButtons(this);
    }

    @Override
    public String getTitle() {
        return "Task";
    }

    @Override
    public void setWindow(Window w) {
        this.window = w;
    }

    private void closeWindow() {
        if (window != null) {
            window.close();
        }
    }

    private void initButtons(VerticalLayout layout) {
        Button createFolder = new Button("Create");
        createFolder.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        createFolder.addStyleName("accordianButton");
        createFolder.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().getCurrent().getNavigator().navigateTo(UIConstants.TASK_CREATE);
                closeWindow();
            }
        });

        Button updateFolder = new Button("Update");
        updateFolder.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        updateFolder.addStyleName("accordianButton");
        updateFolder.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (item != null) {
                    getUI().getCurrent().getNavigator().navigateTo(UIConstants.TASK_UPDATE + item.getId());
                }
                closeWindow();
            }
        });

        Button deleteFolder = new Button("Delete");
        deleteFolder.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        deleteFolder.addStyleName("accordianButton");
        deleteFolder.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                closeWindow();
                if (item != null) {
                    try {
                        TaskService.removeTask(item.getId());
                        Notification.show("Task '" + item.getName() + "' successfully removed.", Notification.Type.TRAY_NOTIFICATION);
                    } catch (Exception e) {
                        Notification.show("Failed to remove Task '" + item.getName() + "' due to " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
                    }
                    getUI().getCurrent().getNavigator().navigateTo(UIConstants.VIEW_MAIN);
                }
            }
        });
        if (item == null) {
            updateFolder.setVisible(false);
            deleteFolder.setVisible(false);
        } else {
            createFolder.setVisible(false);
        }
        layout.addComponents(createFolder, updateFolder, deleteFolder);
        layout.setComponentAlignment(createFolder, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(updateFolder, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(deleteFolder, Alignment.MIDDLE_CENTER);
    }
}