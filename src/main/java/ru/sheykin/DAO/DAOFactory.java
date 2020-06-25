package ru.sheykin.DAO;

import java.util.EnumMap;

public class DAOFactory {

    private static final DAOFactory daoFactory = new DAOFactory();
    private EnumMap<DAOTypes, TaskDataManipulation> cachedDaoTypesTaskData;
    private EnumMap<DAOTypes, UserDataManipulation> cachedDaoTypesUserData;

    private DAOFactory() {
        cachedDaoTypesTaskData = new EnumMap(DAOTypes.class);
        cachedDaoTypesTaskData.put(DAOTypes.SQL, new TaskDAO());
        cachedDaoTypesTaskData.put(DAOTypes.NOSQL, new TaskDAO());
        cachedDaoTypesUserData = new EnumMap(DAOTypes.class);
        cachedDaoTypesUserData.put(DAOTypes.SQL, new AuthenticationDAO());
        cachedDaoTypesUserData.put(DAOTypes.NOSQL, new AuthenticationDAO());
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
}