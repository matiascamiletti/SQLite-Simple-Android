<h3>Version - 0.9</h3>

**todo: Write documentation for classes. Provide disorder on databaseSimple.create(Record.class);**

<h2>Install</h2>

You may import src from project or download jar (recommended) **sorry, add later**

<h2>Quick start</h2>

**Code reference you may see in project**

- Create your model with annotation @Column(type = Column.YOUR_TYPE), for example:

**See all model parameters below in section Model**

```java
public class Record {

    @Column(type = Column.INTEGER)
    public Long dateOfPublication;

    @Column(type = Column.INTEGER)
    public boolean isPublished;

    // also supports Column.TEXT, Column.NUMERIC, Column.REAL

}
```

- Create class extends Application.

```java
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteDatabaseSimple databaseSimple = new SQLiteDatabaseSimple(this);
        databaseSimple.create(Record.class); // create(Class1.class,Class2.class,...)
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

- Create «Operator» class extends SQLiteDatabaseCRUD\<YourModel\>

```java
public class RecordsOperator extends SQLiteDatabaseCRUD<Record> {

    public RecordsOperator(Context context) {
        super(Record.class, context);
    }

 }
```

<h3>That's all!</h3>

In your activity just create operator, for example:
```java

    ...

      @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            RecordsOperator recordsOperator = new RecordsOperator(this);
       }
```
And you may call all needed methods, if you need more, just override or create new in class **RecordsOperator**, look above.

<h2>Model</h2>
Look better this nuance, for example we create new model, call it **News**. But before write all available annotations:

**@Table** - optional parameter, supports attributes:

    name - optional.

**@Column** - this parameter required the type of column, supports attributes:

    type - required (Column.TEXT, Column.NUMERIC, Column.REAL, Column.INTEGER).

    name - optional.

Fully annotated example:

```java
@Table(name = "Super_News")
public class News {

    @Column(name = "Hot", type = Column.INTEGER)
    public boolean isHot;

    ...

}
```

<h2>Notices</h2>
**Database version** - if upgrade database version, for example from 1 to 2, your all tables will be deleted, and created again. **DATA WILL BE LOST.**
If you want only add column, just write it on model and SQLite Simple create it for you.