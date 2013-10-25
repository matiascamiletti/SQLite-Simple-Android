<h3>Version - 2.4</h3>

<h2>Install</h2>
You may import src from project (need delete example folder) or <a href="https://github.com/kvirair/SQLite-Simple-Android/releases">download jar</a> (recommended)

<h2>Quick start</h2>

**Code reference you may see in project**

- Create your model with annotation @Column, for example:

**See all model parameters below in section <a href="https://github.com/kvirair/SQLite-Simple-Android#model">Model</a>**

```java
public class Record {

    @Column
    private String recordText;

}
```

- Create class extends Application.

```java
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // also may use isFirstStartOnAppVersion with your version
        if (SimpleDatabaseUtil.isFirstApplicationStart(this)) {
            SQLiteSimple databaseSimple = new SQLiteSimple(this);
            databaseSimple.create(Record.class);
            // above need enumerate classes, for example:  Class1.class, ...
        }

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

    private RecordsDAO recordsDAO;

      @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            recordsDAO = new RecordsDAO(this);
       }

```
And you may call all needed methods, if you need more, just override or create new in class **RecordsDAO**, look above.

<h2>Model</h2>
Look better this nuance, available annotations:

**@Table** - optional parameter for classes, supports attributes:

    name - optional.

**@Column** - required parameter for variables, supports attributes:

    type - optional (ColumnType.TEXT, ColumnType.NUMERIC, ColumnType.REAL, ColumnType.INTEGER, ColumnType.BLOB).

    name - optional.

    isPrimaryKey - optional (for keys, supports compound key).

    isAutoincrement - optional (for key, aka _id).

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

        // just write below, if you not specify version, library set version 1

        SQLiteSimple databaseSimple = new SQLiteSimple(this, DATABASE_VERSION);
        databaseSimple.create(Record.class);

    }
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

<h2>FTS (Full-Text Search)</h2>

```java

    ...

    private SQLiteSimpleFTS simpleFTS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleFTS = new SQLiteSimpleFTS(this, false); // second parameter is a
                                                      // need to use tables category,
    }                                                 // search between several tables

```

**That's all!** Use methods as described below in appropriate places, like in a example.

**simpleFTS.search(...)**, **simpleFTS.create(...)**.

<h2>Hints</h2>

If you use proguard, don't forget add

```java
-keep public class your_package.models.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
```

to **proguard-project.txt**

<h2>Tools</h2>

Also may be used:

**SimpleConstants**

    ZERO_RESULT - if, for example, object not found, or not created

**SimpleDatabaseUtil** methods:

    getFullDatabasePath - path to internal database.

    isFirstApplicationStart - return true if is first application start.

    isFirstStartOnAppVersion - return true if is first application start in version ...

**SQLiteSimpleDAO** methods:

    updateDatabases - use this, if you need force update for all databases in application

<h2>License</h2>
```
Copyright (C) 2013 Artemiy Garin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```