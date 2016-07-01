package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


public class ExportTranslationDaoImpl implements ExportTranslationDao {
    private DaoFactory daoFactory;

    ExportTranslationDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

}