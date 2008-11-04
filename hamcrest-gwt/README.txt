To use Hamcrest in the Google Web Toolkit (GWT) client side code, include the
hamcrest-core.jar and (optionally) the hamcrest-library.jar along with the
hamcrest-gwt.jar (this) in your classpath and inherit the org.hamcrest.Hamcrest
module.
Make sure you get the jar files that include the source, as GWT needs the source
to work.
Note that you can use all of Hamcrest without this jar on the server side code
in GWT.
