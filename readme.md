**Status - in the development**

**Usage**

- First step add library to your project, you may download jar or import lib src

- Second, create class extends Application

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
