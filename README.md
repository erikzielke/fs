This is my submission to the Android Skills Assessment



Libraries used:
---------------

Android Support Libraries
-------------------------
I use the AndroidSupport Libraries for various view components e.g. Toolbar and RecyclerView

Dagger
------

Dagger is used for dependency injection, the reason why I choose to use it in the project was for easy
initialization of the Retrofit-service. In this particular case it could just as well have been stored
in a standard singleton.

Retrofit
--------

I use retrofit for very easyly calling the api, by just defining the interface using Java-interfaces
and annotations. I use the callback-version of the api's. If I had more experience with RxJava then
I would maybe have tried that out.

Butterknife
-----------

For not filling up the code with findWithById calls. It have not yet used the DataBinding-framework,
but that could have been another possibility, especially since everything except the search query is
 just presenting data. The DataBinder only supports one-way binding.


Picasso
-------

For fetching images from the service

