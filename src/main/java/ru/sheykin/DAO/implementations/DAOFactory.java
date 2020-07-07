package ru.sheykin.DAO.implementations;

import ru.sheykin.DAO.GoalDataManipulation;
import ru.sheykin.DAO.TaskDataManipulation;
import ru.sheykin.DAO.UserDataManipulation;

import java.util.EnumMap;

/**
 * Factory class that allows you to get the instance of a necessary class
 */

public class DAOFactory {

    private static final DAOFactory daoFactory = new DAOFactory();
    private EnumMap<DAOTypes, TaskDataManipulation> cachedDaoTypesTaskData;
    private EnumMap<DAOTypes, UserDataManipulation> cachedDaoTypesUserData;
    private EnumMap<DAOTypes, GoalDataManipulation> cachedDaoTypesGoalData;

    private DAOFactory() {
        cachedDaoTypesTaskData = new EnumMap(DAOTypes.class);
        cachedDaoTypesTaskData.put(DAOTypes.SQL, new TaskDAO());
        cachedDaoTypesTaskData.put(DAOTypes.NOSQL, new TaskDAO());
        cachedDaoTypesUserData = new EnumMap(DAOTypes.class);
        cachedDaoTypesUserData.put(DAOTypes.SQL, new UserDAO());
        cachedDaoTypesUserData.put(DAOTypes.NOSQL, new UserDAO());
        cachedDaoTypesGoalData = new EnumMap(DAOTypes.class);
        cachedDaoTypesGoalData.put(DAOTypes.SQL, new GoalDAO());
        cachedDaoTypesGoalData.put(DAOTypes.NOSQL, new GoalDAO());
    }

    public static DAOFactory getDaoFactory() {
        return daoFactory;
    }

    public TaskDataManipulation getTaskDataManipulationInstance(DAOTypes daoTypes) {
        return cachedDaoTypesTaskData.get(daoTypes);
    }

    public UserDataManipulation getUserDataManipulationInstance(DAOTypes daoTypes) {
        return cachedDaoTypesUserData.get(daoTypes);
    }

    public GoalDataManipulation getGoalDataManipulationInstance(DAOTypes daoTypes) {
        return cachedDaoTypesGoalData.get(daoTypes);
    }
}