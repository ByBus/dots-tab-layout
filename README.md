# DotsTab Layout

DotsTab Layout is a view for displaying viewPager2's pagination in the form of dots.

![](images/demo.gif?raw=true)

### To get a Git project into your build:

#### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```	
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
#### Step 2. Add the dependency

```
dependencies {
    implementation 'com.github.ByBus:dots-tab-layout:0.8.0'
}
```

### How to use

#### 1. Add DotsTabLayout view to your layout
```
<host.capitalquiz.dotstablayout.DotsTabLayout
    android:id="@+id/tab_layout_dots"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/blue"
    android:gravity="center"
    android:paddingVertical="32dp"
    app:baseColor="#FFFFFF"
    app:dotSize="20dp"
    app:dotStrokeWidth="2dp"
    app:dotsHorizontalPadding="2dp"        
    app:indicatorType="growingDotIndicator"
    app:dotsClickable="true"/>
```

#### 2. Attach DotsTabLayout to ViewPager2 and LifecycleOwner

```
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val pager = findViewById<ViewPager2>(R.id.pager)
    pager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)
    // Attachment
    val dotsLayout = findViewById<DotsTabLayout>(R.id.dotstab_layout)
    dotsLayout.attachTo(pager, this)
}
```

### Parameters

- *dotSize* - max diameter/length of the dot/line indicator
- *baseColor* - color of the dots
- *dotStrokeWidth* - stroke width in case of circle indicator and width of line in case of line indicator
- *dotsHorizontalPadding* - padding between dots
- *evenly* - if true, distributes dots evenly along the entire length of DotsTabLayout
- *dotsClickable* - if true, clicking the button will change pager item
- *dotsNumber* - sets dots for preview (for example tools:dotsNumber="6")
- *indicatorType* - sets type of indicator and its animation. See all types below

### Indicator types
#### discreteDotIndicator - default indicator
![](images/discreteDotIndicator.gif?raw=true)

---

#### movingDotIndicator
![](images/movingDotIndicator.gif?raw=true)

---

#### fillingDotIndicator
![](images/fillingDotIndicator.gif?raw=true)

---

#### crawlingDotIndicator
![](images/crawlingDotIndicator.gif?raw=true)

---

#### movingLineIndicator
![](images/movingLineIndicator.gif?raw=true)

here *dotSize* is the length of the line
and  *dotStrokeWidth* is the line width

---

#### growingCircleIndicator
![](images/growingCircleIndicator.gif?raw=true)

here *dotSize* is the size of the circle,
size of a regular dot depends on *dotStrokeWidth*

---

#### expandingLineIndicator
![](images/expandingLineIndicator.gif?raw=true)

here *dotSize* is the length of the line
and *dotStrokeWidth* is the width of the line and size of a regular dot

---

#### jumpingDotIndicator
![](images/jumpingDotIndicator.gif?raw=true)

---

#### compactJumpingDotIndicator
![](images/compactJumpingDotIndicator.gif?raw=true)

---

#### swappingDotIndicator
![](images/swappingDotIndicator.gif?raw=true)

---

#### fadingDotIndicator
![](images/fadingDotIndicator.gif?raw=true)

---

#### growingDotIndicator
![](images/growingDotIndicator.gif?raw=true)

here *dotSize* is the size of selected dot,
size of a regular dot will be smaller by two *dotStrokeWidth*

for example: if dotSize=20dp and dotStrokeWidth=2dp then
size of a regular dot will be 16dp