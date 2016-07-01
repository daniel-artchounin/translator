package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


public class ImportContentDaoImpl implements ImportContentDao {
    private DaoFactory daoFactory;

    ImportContentDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

}