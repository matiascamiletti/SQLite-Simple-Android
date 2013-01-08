<h3>Version - 0.9</h3>
**todo: Write documentation for classes.**

<h2>Install</h2>

You may import src from project or download jar (recommended) **sorry, add later**

<h2>Quick start</h2>

**Code reference you may see in project**

- Create your model with annotation @Column(type = ColumnType.TYPE), for example:

**See all model parameters below in section <a href="https://github.com/kvirair/SQLite-Simple-Android#model">Model</a>**

```java
public class Record {

    @Column(type = ColumnType.INTEGER)
    public Long dateOfPublication;

    @Column(type = ColumnType.INTEGER)
    public boolean isPublished;

    // also supports ColumnType.TEXT, ColumnType.NUMERIC, ColumnType.REAL

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
Look better this nuance, for example we create new model, call it **News**. But before write all available annotations:

**@Table** - optional parameter for classes, supports attributes:

    name - optional.

**@Column** - required parameter for variables, also required the type of column, supports attributes:

    type - required (ColumnType.TEXT, ColumnType.NUMERIC, ColumnType.REAL, ColumnType.INTEGER).

    name - optional.

<h2>Notices</h2>
**Database version** - if upgrade database version, for example from 1 to 2, your all tables will be deleted, and created again. **DATA WILL BE LOST.**
If you want only add column, just write it on model and SQLite Simple create it for you.

**_id** - this column need for cursor adapter, if you want add this column for you model, just write ```public Long _id;```