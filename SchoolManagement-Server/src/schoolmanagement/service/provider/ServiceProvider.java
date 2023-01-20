/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.service.provider;

import java.util.HashMap;
import java.util.Map;
import schoolmanagement.persistence.dao.AdminDao;
import schoolmanagement.persistence.dao.CourseDao;
import schoolmanagement.persistence.dao.LanguageDao;
import schoolmanagement.persistence.dao.StudentDao;
import schoolmanagement.persistence.dao.UserDao;
import schoolmanagement.persistence.dao.impl.AdminDaoImpl;
import schoolmanagement.persistence.dao.impl.CourseDaoImpl;
import schoolmanagement.persistence.dao.impl.LanguageDaoImpl;
import schoolmanagement.persistence.dao.impl.StudentDaoImpl;
import schoolmanagement.persistence.dao.impl.UserDaoImpl;
import schoolmanagement.service.CourseService;
import schoolmanagement.service.LanguageService;
import schoolmanagement.service.StudentService;
import schoolmanagement.service.UserService;
import schoolmanagement.service.impl.CourseServiceImpl;
import schoolmanagement.service.impl.LanguageServiceImpl;
import schoolmanagement.service.impl.StudentServiceImpl;
import schoolmanagement.service.impl.UserServiceImpl;

/**
 *
 * @author ivano
 */
// Thread Safe Singleton Pattern
public class ServiceProvider {

    private final Map<Class, Object> manager;

    // volatile keyword here makes sure that
    // the changes made in one thread are 
    // immediately reflect in other thread
    private static volatile ServiceProvider serviceProvider;

    private ServiceProvider() {

        this.manager = new HashMap<>();

        registerDaos();
        registerServices();
    }

    // optimized thread safe singleton creation
    public static ServiceProvider getInstance() {
        ServiceProvider result = serviceProvider; // to reduce access of volatile field, performance boost
        if (result == null) {
            synchronized (ServiceProvider.class) {
                result = serviceProvider;
                if (result == null) {
                    result = serviceProvider = new ServiceProvider();
                }
            }
        }
        return result;
    }

    public Object getRequiredService(Class interfaceClass) {

        if (manager.containsKey(interfaceClass)) {
            return manager.get(interfaceClass);
        } else {
            return null;
        }
    }

    private void registerDaos() {
        manager.put(StudentDao.class, new StudentDaoImpl());
        manager.put(UserDao.class, new UserDaoImpl());
        manager.put(AdminDao.class, new AdminDaoImpl());
        manager.put(CourseDao.class, new CourseDaoImpl());
        manager.put(LanguageDao.class, new LanguageDaoImpl());

    }

    private void registerServices() {
        manager.put(StudentService.class, new StudentServiceImpl((UserDao) manager.get(UserDao.class), (StudentDao) manager.get(StudentDao.class)));
        manager.put(UserService.class, new UserServiceImpl((UserDao) manager.get(UserDao.class), (StudentDao) manager.get(StudentDao.class), (AdminDao) manager.get(AdminDao.class)));
        manager.put(CourseService.class, new CourseServiceImpl((CourseDao) manager.get(CourseDao.class)));
        manager.put(LanguageService.class, new LanguageServiceImpl((LanguageDao) manager.get(LanguageDao.class)));
    }

}
