package dao;


/* The base implementation of our DAO model */
public class DaoImpl implements Dao {
    protected DaoFactory daoFactory;

    public DaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
