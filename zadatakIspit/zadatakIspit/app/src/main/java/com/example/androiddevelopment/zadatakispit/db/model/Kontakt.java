package com.example.androiddevelopment.zadatakispit.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;



@DatabaseTable(tableName = Kontakt.TABLE_NAME_KONTAKT)
public class Kontakt {

    public static final String FIELD_NAME_ID     = "id";
    public static final String TABLE_KONTAKT_NAME = "name";
    public static final String TABLE_KONTAKT_SURNAME = "surname";
    public static final String TABLE_KONTAKT_ADDRESS = "address";
    public static final String TABLE_KONTAKT_NUMBER = "broj_telefona";


    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = TABLE_MOVIE_NAME)
    private String mName;

    @DatabaseField(columnName = TABLE_MOVIE_BIOGRAPHY)
    private String mBiography;

    @DatabaseField(columnName = TABLE_MOVIE_SCORE)
    private Float mScore;

    @DatabaseField(columnName = TABLE_MOVIE_BIRTH)
    private String mBirth;

    @ForeignCollectionField(columnName = Kontakt.TABLE_MOVIE_MOVIES, eager = true)
    private ForeignCollection<Imenik> movies;

    public Kontakt() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public ForeignCollection<Imenik> getMovies() {
        return movies;
    }

    public void setMovies(ForeignCollection<Imenik> movies) {
        this.movies = movies;
    }

    public String getmBiography() {
        return mBiography;
    }

    public void setmBiography(String mBiography) {
        this.mBiography = mBiography;
    }

    public Float getmScore() {
        return mScore;
    }

    public void setmScore(Float mScore) {
        this.mScore = mScore;
    }

    public String getmBirth() {
        return mBirth;
    }

    public void setmBirth(String mBirth) {
        this.mBirth = mBirth;
    }

    @Override
    public String toString() {
        return mName;
    }
}
