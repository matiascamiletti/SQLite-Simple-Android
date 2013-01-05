**Status - in the development**

<h2>Install</h2>
---------------------------------------

<h2>Usage</h2>
---------------------------------------

Create class extends Application

```java
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteDatabaseSimple sqLiteDatabaseSimple = new SQLiteDatabaseSimple(this);
        // enumerate classes, lib create tables for them
        sqLiteDatabaseSimple.create(new Class[]{Note.class, News.class});
    }

}
```

- Add to AndroidManifest.xml

```java
    <application
        android:name=".MyApplication"
        ...
        ...
            >
```

<h2>Notices</h2>
---------------------------------------

Be careful with attribute «notNull», because at some point you may **delete column** from model with this attribute and got error:
```java
    Error inserting ...
    android.database.sqlite.SQLiteConstraintException: error code 19: constraint failed
```
It means the column, what you delete - null, and SQLite can not add new row