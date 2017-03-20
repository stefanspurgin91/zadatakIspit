package com.example.androiddevelopment.zadatakispit.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static com.example.androiddevelopment.zadatakispit.db.model.Kontakt.FIELD_NAME_ID;


@DatabaseTable(tableName = Imenik.TABLE_NAME_IMENIK)
public class Imenik {

    public static final String FIELD_KONTAKT_ID     = "id";
    public static final String TABLE_KONTAKT_NAME = "name";
    public static final String TABLE_KONTAKT_SURNAME = "surname";
    public static final String FIELD_KONTAKT_ADDRESS = "address";
    public static final String FIELD_KONTAKT_NUMBER  = "broj_telefona";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = TABLE_KONTAKT_NAME)
    private String mName;

    @DatabaseField(columnName = TABLE_KONTAKT_SURNAME)
    private String mSurname;

    @DatabaseField(columnName = FIELD_KONTAKT_ADDRESS)
    private String mAddress;

    @DatabaseField(columnName = FIELD_NAME_NUMBER)
    private String getmBroj_telefona;

    public Imenik() {
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

    public String getmSurname() {
        return mSurname;
    }

    public void setmSurname(String mSurname) {
        this.mSurname = mSurname;
    }

    public Kontakt getmAddress() {
        return mAddress;
    }

    public void setmAddress(Kontakt mAddress) {
        this.mAddress = mAddress;
    }

    public String getmBroj_telefona() {
        return getmBroj_telefona;
    }

    public void setmBroj_telefona(String m) {
        this.getmBroj_telefona = getmBroj_telefona;
    }

    @Override
    public String toString() {
        return mName;
    }
}
