package dao;

public class DaoImpl implements Dao {
    protected DaoFactory daoFactory;

    public DaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
