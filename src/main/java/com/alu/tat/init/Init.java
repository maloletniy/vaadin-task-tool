package com.alu.tat.init;

import com.alu.tat.entity.Folder;
import com.alu.tat.entity.Task;
import com.alu.tat.entity.User;
import com.alu.tat.entity.dao.BaseDao;
import com.alu.tat.entity.schema.Schema;
import com.alu.tat.entity.schema.SchemaElement;
import com.alu.tat.service.FolderService;
import com.alu.tat.service.UserService;
import com.alu.tat.util.HibernateUtil;
import com.alu.tat.util.PasswordTools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Collection;
import java.util.List;

/**
 * Created by imalolet on 6/19/2015.
 */
public class Init extends HttpServlet {

    //private final static Logger logger =
    //        LoggerFactory.getLogger(Init.class);

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            initData();
        } catch (Exception e) {
            System.err.println("error while initializing data: " +  e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            HibernateUtil.shutdown();
        } catch (Exception e) {
            System.err.println("error while shutting down hibernate: " +  e.getMessage());
            e.printStackTrace();
        }
    }

    private void initData() {
        Collection<User> allUsers = UserService.getUsers();
        User admin = new User();
        admin.setLogin("admin");
        admin.setName("Admin");
        admin.setPasswordHash(PasswordTools.getPwdHash("admin"));
        admin.setIsSystem(true);
        if (!allUsers.contains(admin)) {
            UserService.createUser(admin);
        }
        if (allUsers.isEmpty()) {
            User user = new User();
            user.setLogin("imalolet");
            user.setPasswordHash(PasswordTools.getPwdHash("imalolet"));
            user.setName("Igor Maloletniy");
            UserService.createUser(user);

            Schema defaultSchema = new Schema();
            defaultSchema.setIsSystem(true);
            defaultSchema.setName("Detailed");
            defaultSchema.setDescription("Detailed analysis schema");
            List<SchemaElement> list = defaultSchema.getElementsList();
            list.add(new SchemaElement("Documentation", "All documentation related aspects", SchemaElement.ElemType.DOMAIN, 0));
            list.add(new SchemaElement("SDD", "FSD/FDD/SDD/Testplan needed?", SchemaElement.ElemType.BOOLEAN, 8));

            list.add(new SchemaElement("Models", "All model description related aspects", SchemaElement.ElemType.DOMAIN, 0));
            list.add(new SchemaElement("New models", "Do we need to create new models?", SchemaElement.ElemType.BOOLEAN, 4));
            list.add(new SchemaElement("Model changes", "Do we need to change existing models?", SchemaElement.ElemType.BOOLEAN, 2));
            list.add(new SchemaElement("cmsUser/MyProfile/quickUser affected", "Does pseudo models affected?", SchemaElement.ElemType.BOOLEAN, 0));
            list.add(new SchemaElement("Models generation affected?", "Do we need to change model generation part?", SchemaElement.ElemType.BOOLEAN, 8));

            list.add(new SchemaElement("Instances", "All model instances related aspects", SchemaElement.ElemType.DOMAIN, 0));
            list.add(new SchemaElement("New or changed instances", "Do we need to create new model instances?", SchemaElement.ElemType.BOOLEAN, 2));

            list.add(new SchemaElement("Translations", "All localization aspects", SchemaElement.ElemType.DOMAIN, 0));
            list.add(new SchemaElement("Models localization", "Do we need to add/change model change localization?", SchemaElement.ElemType.BOOLEAN, 2));
            list.add(new SchemaElement("Exceptions localization", "Do we need to add/change exception localization?", SchemaElement.ElemType.BOOLEAN, 2));

            list.add(new SchemaElement("Business logic", "All business logic aspects", SchemaElement.ElemType.DOMAIN, 0));
            list.add(new SchemaElement("Enhancer / CoherenceChecker / Post-pre actions", "Does the new Business logic affects pointed items?", SchemaElement.ElemType.BOOLEAN, 4));
            list.add(new SchemaElement("New or changed behavior?", "Do we need to introduc/change any business logic?", SchemaElement.ElemType.BOOLEAN, 4));
            list.add(new SchemaElement("New/changed alarams", "Do we need to create/update alarms?", SchemaElement.ElemType.BOOLEAN, 2));
            list.add(new SchemaElement("Applicable otSolution", "Define the solution applicable (OTMS/OTBE/OTMC/...)", SchemaElement.ElemType.STRING, 0));
            list.add(new SchemaElement("Describe cases", "Set and describe the number of use cases/scenarios", SchemaElement.ElemType.INTEGER, 4));

            list.add(new SchemaElement("EasyAdmin / Migration / Audit", "All easy admin/migration/audit aspects", SchemaElement.ElemType.DOMAIN, 0));
            list.add(new SchemaElement("EasyAdmin", "Do we need create/update easy admin?", SchemaElement.ElemType.BOOLEAN, 2));
            list.add(new SchemaElement("ICE2ICE migration needed", "Do we need migration from previous OT version?", SchemaElement.ElemType.BOOLEAN, 0));
            list.add(new SchemaElement("*Liquibase migration needed", "Do we need to migration DB schema or data?", SchemaElement.ElemType.BOOLEAN, 4));
            list.add(new SchemaElement("*Broker migration needed", "Do we need to migration something during Broker migration phase?", SchemaElement.ElemType.BOOLEAN, 4));
            list.add(new SchemaElement("*Post ready migration", "Do we need POST-READY migration(for external systems like ACS)?", SchemaElement.ElemType.BOOLEAN, 4));
            list.add(new SchemaElement("Audit", "Do we need to change/implement some audit procedures?", SchemaElement.ElemType.BOOLEAN, 4));
            list.add(new SchemaElement("ICS2ICE migration needed", "Does it impacts migration from ICS6.x?", SchemaElement.ElemType.BOOLEAN, 8));


            list.add(new SchemaElement("Testing", "All the testing aspects", SchemaElement.ElemType.DOMAIN, 0));
            list.add(new SchemaElement("Unit tests needed", "Do we need unit tests?", SchemaElement.ElemType.BOOLEAN, 4));
            list.add(new SchemaElement("Functional tests needed", "Do we need functional tests?", SchemaElement.ElemType.BOOLEAN, 8));
            list.add(new SchemaElement("Integration tests needed", "Do we need integration tests?", SchemaElement.ElemType.BOOLEAN, 4));
            list.add(new SchemaElement("ICE2ICE migration tests needed", "Do we need migration from previous version tests?", SchemaElement.ElemType.BOOLEAN, 8));
            list.add(new SchemaElement("ICS2ICE migration tests needed", "Do we need migration from ICS6.x tests?", SchemaElement.ElemType.BOOLEAN, 8));
            list.add(new SchemaElement("Manual tests needed", "Do we need manual testing?", SchemaElement.ElemType.BOOLEAN, 4));
            list.add(new SchemaElement("Auto GUI WBM/MyProfile tests needed", "Do we need auto GUI tests?", SchemaElement.ElemType.BOOLEAN, 8));


            list.add(new SchemaElement("Environment", "All the environment aspects", SchemaElement.ElemType.DOMAIN, 0));
            list.add(new SchemaElement("Should platforms be prepared?", "Do we need to prepare some environment before dev/test?", SchemaElement.ElemType.BOOLEAN, 8));
            BaseDao.create(defaultSchema);

            Schema secondSchema = new Schema();
            secondSchema.setIsSystem(true);
            secondSchema.setName("High Level");
            secondSchema.setDescription("High Level Analysis Schema");
            List<SchemaElement> secondList = secondSchema.getElementsList();
            secondList.add(new SchemaElement("General", "Do we need SDD?", SchemaElement.ElemType.DOMAIN, 0));
            secondList.add(new SchemaElement("SDD", "Do we need SDD?", SchemaElement.ElemType.BOOLEAN, 5));
            BaseDao.create(secondSchema);

            Schema testSchema = new Schema();
            testSchema.setName("Test schema 1");
            testSchema.setDescription("Test schema");
            List<SchemaElement> testList = testSchema.getElementsList();
            testList.add(new SchemaElement("General", "General", SchemaElement.ElemType.DOMAIN, 0));
            testList.add(new SchemaElement("SDD", "Do we need SDD?", SchemaElement.ElemType.BOOLEAN, 5));
            testList.add(new SchemaElement("Products", "What products we support?", SchemaElement.ElemType.MULTI_ENUM,"value1;value2;value3", 5));
            BaseDao.create(testSchema);

            Schema test2Schema = new Schema();
            test2Schema.setName("Test schema 2");
            test2Schema.setDescription("Test schema");
            List<SchemaElement> test2List = test2Schema.getElementsList();
            test2List.add(new SchemaElement("General", "General", SchemaElement.ElemType.DOMAIN, 0));
            test2List.add(new SchemaElement("SDD", "Do we need SDD?", SchemaElement.ElemType.BOOLEAN, 5));
            test2List.add(new SchemaElement("Use cases", "Describe all cases", SchemaElement.ElemType.MULTI_STRING, 5));
            BaseDao.create(test2Schema);

            Folder f1 = new Folder();
            f1.setName("OT10");
            Folder f2 = new Folder();
            f2.setName("OT11");
            FolderService.createFolder(f1);
            FolderService.createFolder(f2);

            for (int i = 0; i < 10; i++) {
                Task t = new Task();
                t.setIsSystem(true);
                t.setId(System.currentTimeMillis());
                t.setAuthor(user);
                t.setDescription("description of crqms" + i);
                t.setName("crqms" + i);
                t.setSchema(defaultSchema);
                t.setFolder(((int) (Math.round(Math.random()))) % 2 == 1 ? f1 : f2);

                BaseDao.create(t);
            }
        }

    }
}
