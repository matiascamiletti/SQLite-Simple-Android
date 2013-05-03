<h3>Version - 1.5</h3>

<h2>Install</h2>

You may import src from project or <a href="http://sourceforge.net/projects/sqlite-android/files/sqlite-simple-1.5.jar/download">download jar</a> (recommended)

<h2>Quick start</h2>

**Code reference you may see in project**

- Create your model with annotation @Column(type = ColumnType.TYPE), for example:

**See all model parameters below in section <a href="https://github.com/kvirair/SQLite-Simple-Android#model">Model</a>**

```java
public class Record {

    @Column(type = ColumnType.INTEGER)
    public Long dateOfPublication;

    @Column(type = ColumnType.NUMERIC)
    public boolean isPublished;

    // also supports ColumnType.TEXT, ColumnType.REAL, ColumnType.BLOB

}
```

- Create class extends Application.

```java
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteSimple databaseSimple = new SQLiteSimple(this);
        databaseSimple.create(Record.class); // enumerate classes Class1.class,Class2.class,...
    }

}
```

- Add to **AndroidManifest.xml**

```java
    <application
        android:name=".MainApplication"
        ...
        ...
            >
```

- Create «DAO» class extends SQLiteSimpleDAO\<YourModelName\>

```java
public class RecordsDAO extends SQLiteSimpleDAO<Record> {

    public RecordsDAO(Context context) {
        super(Record.class, context);
    }

 }
```

**That's all!**

In your activity just create operator, for example:
```java

    ...

      @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            RecordsDAO recordsDAO = new RecordsDAO(this);
       }

```
And you may call all needed methods, if you need more, just override or create new in class **RecordsDAO**, look above.

<h2>Model</h2>
Look better this nuance, available annotations:

**@Table** - optional parameter for classes, supports attributes:

    name - optional.

**@Column** - required parameter for variables, also required the type of column, supports attributes:

    type - required (ColumnType.TEXT, ColumnType.NUMERIC, ColumnType.REAL, ColumnType.INTEGER, ColumnType.BLOB).

    name - optional.

<h2>Database version</h2>
**Database version** - if you upgrade database version, for example from 1 to 2, all your tables will be deleted, and created again. **DATA WILL BE LOST.**
If you want only add column, just write it on model and SQLite Simple create it for you.

How use:

```java
public class MainApplication extends Application {

    private final static int DATABASE_VERSION = 2;

    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteSimple databaseSimple = new SQLiteSimple(this, DATABASE_VERSION); // just write here
        databaseSimple.create(Record.class);                                    // if you not specify version
    }                                                                           // library set version 1
}
```

<h2>Read database from assets</h2>
```java
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteSimple databaseSimple = new SQLiteSimple(this, "example.sqlite");
        databaseSimple.create(Example.class);
    }

}
```