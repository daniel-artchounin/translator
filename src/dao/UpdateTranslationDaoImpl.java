package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


public class UpdateTranslationDaoImpl implements UpdateTranslationDao {
    private DaoFactory daoFactory;

    UpdateTranslationDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

}