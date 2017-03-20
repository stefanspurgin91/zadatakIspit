package com.example.androiddevelopment.zadatakispit.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.androiddevelopment.zadatakispit.db.model.Imenik;
import com.example.androiddevelopment.zadatakispit.db.model.Kontakt;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import rs.aleph.android.example25.db.model.Product;
import rs.aleph.android.example25.pripremni.db.model.Actor;
import rs.aleph.android.example25.pripremni.db.model.Movie;


public class IspitORMLightHelper extends OrmLiteSqliteOpenHelper{

    private static final String DATABASE_NAME    = "ispit.db";
    private static final int    DATABASE_VERSION = 1;

    private Dao<Imenik, Integer> mImenikDao = null;
    private Dao<Kontakt, Integer> mKontaktDao = null;

    public IspitORMLightHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Imenik.class);
            TableUtils.createTable(connectionSource, Kontakt.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Imenik.class, true);
            TableUtils.dropTable(connectionSource, Kontakt.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Imenik, Integer> getmImenikDao() throws SQLException {
        if (mImenikDaoDao == null) {
            mImenikDaoDao = getDao(Imenik.class);
        }

        return mImenikDao;
    }

    public Dao<Kontak, Integer> getKontaktDao() throws SQLException {
        if (mKontaktDao == null) {
            mKontakDao = getDao(kontak.class);
        }

        return mKontaktDao;
    }


    @Override
    public void close() {
        mKontaktDao = null;
        mKontakDao = null;

        super.close();
    }
}
