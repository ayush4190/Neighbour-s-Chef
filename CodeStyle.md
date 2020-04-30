# Code Style guidelines

### Package structure
* `ui`
    - All UI related Java/Kotlin classes go here
    - No business logic is performed by the activity or fragment. Since MVVM is used, the 
    responsibility is of the `ViewModel`
    - `ui/activity`: Contains only `Activity` classes split by responsibility.
    - `ui/fragment`: Contains `Fragment` classes along with their respective `ViewModel`s, `
    Adapter`s and `ViewHolder`s.
* `util`
    - Utility functions, extensions are placed here
    - All Java utility functions are placed in a class with a private constructor. All public 
    methods are `static` and accessible by `ClassName.method()`.
    - In Kotlin, extension functions may be used only if the required action is a responsibility of 
    the receiver. If not, create a top-level function with the receiver as a parameter.
    - To maintain interoperability with Java, all Kotlin files (except those containing **only** a 
    _class_, _interface_ or _enum_) must be annotated with `@file:JvmName("FileName")`. If a Kotlin 
    class contains "static" methods, they must be annotated with `@JvmStatic`.
    - `util/android`: Contains Android only utility methods.
    - `util/common`: Contains utility methods for JVM components such as `String`, `int`, etc. Must 
    not contain any Android imports.
* `model`
    - Classes representing real-world entities are placed here.
    - If classes are to be displayed in UI, ensure that they have `toString()`, `hashCode()` and 
    `equals()` implemented. In Java, use `Objects.hash()` and `Objects.equals()`. In Kotlin, prefer 
    data classes.
    - If models are to be passed between fragments/activities, ensure that they extend `Parcelable`.

### XML
* Prefer `ConstraintLayout` over `RelativeLayout` or `LinearLayout` for parent layout. If the view 
contains an `AppBar` and a `FloatingActionButton`, prefer `CoordinatorLayout` as parent.
* Prefer Material Design components like `MaterialTextView` over `TextView` and `MaterialButton` 
over `Button`.
* Any layout defined must follow the following norms:
    - `android:id` must be the first tag (if not present, ignore)
    - `android:layout_height` and `android:layout_weight` are **required** fields and must follow 
    `android:id`
    - All _android_ namespace attributes are placed first, followed by all _app_ namespaces. Lastly,
    if any, define all _tools_ namespace attributes
    - Prefer _tools_ namespace for mocking. For instance, if a `TextView` can have dynamic text 
    (like price or name), use `tools:text="Sample text"` over `android:text="Sample text"`. This is 
    because all _android_ and _app_ namespace attributes are rendered, while _tools_ namespace is 
    effectively a mock-up
    - Use string resources defined in _strings.xml_ instead of hard-coded strings as text
    - Component naming (`android:id`) must follow the following pattern: **type_purpose**
        - For `TextView`s, use **text_purpose**. If the view is for price, it must be **text_price**
        - For `ImageView`s, use **image_purpose**. If the image is of a product, it must be 
        **img_product**
        - For `RecyclerView`s, use **recycler_purpose**. If the view is for the cart items, it must 
        be **recycler_cart**
        - For `EditText`s, use **edit_purpose**
        - For nested layouts (`LinearLayout` inside a `ConstraintLayout`), identify the layout with
        **layout_purpose**
        - Similar practices must follow for other resources
* Activity layouts must be named as `activity_<activity_name>.xml`, e.g., `activity_main.xml`
* Fragment layouts must be named as `fragment_<fragment_name>.xml`, e.g., `fragment_main.xml`
* Layouts used within `RecyclerView`s must be named as
    - `card_<item_held>.xml` (if using `CardView` as root), e.g. `card_product.xml`
    - `layout_<item_held>.xml` (if using any other layout as root), e.g. `layout_product.xml`


### Java
* All Android specific classes must end with the component name. e.g., _MainActivity_, 
_MainFragment_, _MainViewModel_, etc.
* Use lambdas wherever possible instead of anonymous classes.
* No static references to Views/Contexts.
* Use **CamelCase** for class names and **lowerCamelCase** for objects/fields. Static final 
constants must use **UPPER_SNAKE_CASE**.

### Kotlin
* Follow same guidelines as Java.
* Use `coroutines` instead of `threads` for background processing.

### Common guidelines
* Use `Timber` for logging instead of `Log.i`, etc.
* Use `ChildFragmentManager` when attaching fragments to existing fragments (instead of parent 
activity's `SupportFragmentManager`).
* Use [`Dexter`](https://github.com/Karumi/Dexter) for requesting permissions from the user.