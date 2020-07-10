package ru.sheykin.DAO.implementations;

import ru.sheykin.DAO.GoalDao;
import ru.sheykin.DAO.TaskDao;
import ru.sheykin.DAO.UserDao;

import java.util.EnumMap;

/**
 * Factory class that allows you to get the instance of a necessary class
 */

public class DAOFactory {

    private static final DAOFactory daoFactory = new DAOFactory();
    private EnumMap<DAOTypes, TaskDao> cachedDaoTypesTaskData;
    private EnumMap<DAOTypes, UserDao> cachedDaoTypesUserData;
    private EnumMap<DAOTypes, GoalDao> cachedDaoTypesGoalData;

    private DAOFactory() {
        cachedDaoTypesTaskData = new EnumMap(DAOTypes.class);
        cachedDaoTypesTaskData.put(DAOTypes.SQL, new TaskRepo());
        cachedDaoTypesTaskData.put(DAOTypes.NOSQL, new TaskRepo());
        cachedDaoTypesUserData = new EnumMap(DAOTypes.class);
        cachedDaoTypesUserData.put(DAOTypes.SQL, new UserRepo());
        cachedDaoTypesUserData.put(DAOTypes.NOSQL, new UserRepo());
        cachedDaoTypesGoalData = new EnumMap(DAOTypes.class);
        cachedDaoTypesGoalData.put(DAOTypes.SQL, new GoalRepo());
        cachedDaoTypesGoalData.put(DAOTypes.NOSQL, new GoalRepo());
    }

    public static DAOFactory getDaoFactory() {
        return daoFactory;
    }

    public TaskDao getTaskDataManipulationInstance(DAOTypes daoTypes) {
        return cachedDaoTypesTaskData.get(daoTypes);
    }

    public UserDao getUserDataManipulationInstance(DAOTypes daoTypes) {
        return cachedDaoTypesUserData.get(daoTypes);
    }

    public GoalDao getGoalDataManipulationInstance(DAOTypes daoTypes) {
        return cachedDaoTypesGoalData.get(daoTypes);
    }
}