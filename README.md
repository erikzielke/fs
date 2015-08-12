This is my submission to the Android Skills Assessment

It is just two screens, a list of movies and a page with details.

The search is in the toolbar, and searches on every keystroke, this could be optimized by waiting a little
before actually performing the search. I have seen some tutorials making something similar using RxJava, but since I
only have toyed around with it, I would not use it in this submission. Furthermore waiting requests should be cancelled,
but did not release that retrofit did not support that, before I had made most of the app.

The single movie page uses some Android Design Library Components to show the the backdrop image in
the top, but scrolls almost away when scrolling. The endpoint did not contains that much interesting
information, without calling further apis.


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

