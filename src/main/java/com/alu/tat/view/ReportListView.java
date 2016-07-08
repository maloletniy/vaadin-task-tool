package com.alu.tat.view;

import com.alu.tat.entity.Folder;
import com.alu.tat.entity.Task;
import com.alu.tat.service.FolderService;
import com.alu.tat.service.TaskService;
import com.alu.tat.util.TaskPresenter;
import com.alu.tat.view.menu.PopupMenuManager;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by
 * User: vkhodyre
 * Date: 7/8/2016
 */
public class ReportListView extends VerticalLayout implements View {
    private Navigator navigator;
    private PopupMenuManager popupManager;

    private Panel infoPanel;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = getUI().getNavigator();
        popupManager = new PopupMenuManager(this);

        infoPanel = new Panel();
        infoPanel.setSizeFull();
        //TODO: make scrollable
        Component rightMenuTree = getTasksTreeMenu();
        rightMenuTree.setSizeFull();

        final HorizontalSplitPanel container = new HorizontalSplitPanel(rightMenuTree, infoPanel);
        // Set the position of the splitter as percentage
        container.setSplitPosition(25, Unit.PERCENTAGE);
        container.setSizeFull();

        addComponent(container);
        setExpandRatio(container, 1);
        setSizeFull();
    }

    private Component getTasksTreeMenu() {
        FormLayout layout = new FormLayout();

        Button enterTool = new Button("Enter Analysis Tool");
        enterTool.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.navigateTo(UIConstants.VIEW_MAIN);
            }
        });
        layout.addComponent(enterTool);

        Panel p = new Panel();
        Tree taskTree = new Tree();
        configureTaskTree(taskTree, infoPanel);
        p.setContent(taskTree);

        layout.addComponent(p);
        return layout;
    }

    private void configureTaskTree(Tree tree, Panel infoPanel) {
        final Collection<Task> tasks = TaskService.getTasks();

        String root = "Folder";

        tree.addItem(root);
        tree.expandItem(root);
        tree.setItemIcon(root, UIConstants.FOLDER_ICON);

        Set<Folder> foldersSet = new LinkedHashSet<>(FolderService.getFolders());
        //At first add all sub-nodes
        for (Folder r : foldersSet) {
            Item i = tree.addItem(r);
            tree.setItemIcon(r, UIConstants.FOLDER_ICON);
        }
        //After we can setup the roots
        for (Folder r : foldersSet) {
            if (r.getRoot() != null) {
                Folder rRoot = r.getRoot();
                tree.setParent(r, rRoot);
            } else {
                tree.setParent(r, root);
            }
        }

        for (Task t : tasks) {
            tree.addItem(t);
            tree.setChildrenAllowed(t, false);
            Folder parent = t.getFolder();
            if (parent != null) {
                tree.setParent(t, parent);
            } else {
                tree.setParent(t, root);
            }
        }

        tree.addItemClickListener(new TaskTreeItemClickListener());

    }

    private void showTaskInfo(Task task) {
        String s = TaskPresenter.getHtmlView(task);
        RichTextArea text = new RichTextArea();
        text.setSizeFull();
        text.setValue(s);
        text.setReadOnly(true);
        infoPanel.setContent(text);
    }

    private class TaskTreeItemClickListener implements ItemClickEvent.ItemClickListener {

        @Override
        public void itemClick(ItemClickEvent event) {
            //Task leaf
            if (event.getItemId() instanceof Task) {
                final Task task = (Task) event.getItemId();
                showTaskInfo(task);
            }
        }
    }

}
