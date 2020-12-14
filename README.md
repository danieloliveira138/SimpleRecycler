# [SimpleRecycler](https://github.com/danieloliveira138/SimpleRecycler) [![](https://jitpack.io/v/danieloliveira138/SimpleRecycler.svg)](https://jitpack.io/#danieloliveira138/SimpleRecycler)

A simple way to use a Recycler View in Android Apps.

## How use?
To use this simple lib, add SimpleRecycler like a commom RecyclerView:
```xml
     <com.catra.simple_recycler.SimpleRecycler
        android:id="@+id/simpleRecycler"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
Add a layout for inflate the holder view.

In your Activity, call simpleRecycler listBuilder void with holder layout and list size. That method use a lambda, use that to bind your holder view data.

```Kotlin
  simpleRecycler.listBuilder(
                layoutRes = R.layout.holder_example,
                itemCount = list.size
        ) { view, position ->

            view.name.text = list[position].toString()
            view.setOnClickListener {
                Toast.makeText(
                        this,
                        "click element ${list[position]}",
                        Toast.LENGTH_SHORT
                ).show()
            }

        }
```

To control the SimpleRecycler elements size, use the void submitItems and pass the list size
```
  simpleRecycler.submitItems(list.size)
```




## Add this lib in your project!
To get a Git project into your build:

***Step 1***. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
***Step 2***. Add the dependency

	dependencies {
	        implementation 'com.github.danieloliveira138:SimpleRecycler:0.1.0'
	}
