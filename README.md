LinearListView
=================

Android library that allows you to bind a `LinearLayout` with a `ListAdapter`.


Download
--------

Gradle:

```groovy
dependencies {
  compile 'com.github.frankiesardo:linearlistview:1.0.1@aar'
}
```

Usage
=====

A simple example of the xml attributes that you can use

```xml
<com.linearlistview.LinearListView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/list"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    android:showDividers="none|middle|beginning|end"
    android:divider="@drawable/your_divider"
    android:dividerPadding="2dp"
    app:dividerThickness="2dp"
    android:entries="@array/your_array" />
```

Where: `showDividers`, `divider` and `dividerPadding` have the same meaning of a `LinearLayout` (API 11) attributes, `entries` is the same as a `ListView` attribute and `dividerThickness` controls how thick is the divider (namely its height or width depending on its orientation).

In your `Activity` you can call `myLinearListView.setAdapter(myAdapter)` and each view from your `ListAdapter` will be inflated and kept in sync with its data.


Why you want to use a LinearListVew:

* You need an horizontal scrollable list (since `Gallery` is deprecated): Just embed this layout with an `horizontal` orientation in an `HorizontalScrollView`.
* You need two or more lists in a vertical scrollable view.
* You need to inflate several views in a `LinearLayout` from an `Adapter` and you want to keep them synchronized with its data. This is especially useful when working with a `CursorAdapter`.
* You need a list with a fixed height (the total height of its children). Or, you have a complex, scrollable layout and don't want to use a `ListView` with footers and headers.
* You simply want to use a `LinearLayout` with dividers (API 11) with the added benefit of a `dividerThickness` attribute.


Roadmap
-------------------------

* Adding a `drawSelectorOnTop` attribute.


License
=======

    Copyright 2012 Frankie Sardo

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
