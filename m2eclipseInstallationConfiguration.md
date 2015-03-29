# Introduction #

m2eclipse provides comprehensive Maven integration for Eclipse. You can use m2eclipse to manage both simple and multi-module Maven projects, execute Maven builds via the Eclipse interface, and interact with Maven repositories. m2eclipse makes development easier by integrating data from a project’s Object Model with Eclipse IDE features. With m2eclipse, you can use Maven within Eclipse in a natural and intuitive interface.

http://m2eclipse.sonatype.org/


# Steps #

The course is divided in three main steps. Note that some paths has strings like _x.x.x`_`xx_ which should be replaced for a valid version number.

## Configure installed JRE in Eclipse ##

  1. Open Eclipse.
  1. Go to _Window -> Preferences -> Java -> Installed JREs_
  1. Now, we are going to add a new JRE definition. Click _Add... -> Standard VM_
  1. Search for the JRE home. This path should actually point to the JDK directory. For instance, _C:\Program Files\Java\jdkx.x.x\_xx_
  1. Check the added JRE definition.

## Configure and edit eclipse.ini ##
  1. Close Eclipse.
  1. Go to the eclipse installation directory and open the file _eclipse.ini_.
  1. Add **two lines** above the _-vmargs_ line:
```
-vm
C:/Program Files/Java/jdkx.x.x_xx/bin/javaw
```

## Installing m2eclipse plug-in ##

  1. Open Eclipse.
  1. Go to _Help -> Install New Software..._ and add a new repository.
  1. The repository location must be _http://m2eclipse.sonatype.org/sites/m2e/_
  1. Install _Maven Integration for Eclipse_.

## Add a new Maven2 repository ##

In this step we will configure Maven2 repositories through Eclipse.
  1. Open Eclipse and go to _Window -> Show View -> Other..._.
  1. Look for _Maven -> Maven repositories_. Here you can check your local, global, project and custom repositories.
  1. To add a new **Project repository** open _pom.xml_ of target project and click _Show advanced tabs_ (placed at the top-right corner).
  1. Open _Repositories_ tab and create a new repository with http://download.java.net/maven/2 as URL.
  1. Finally, click on _Refresh_ button (two yellow arrows placed near the _Show advanced tabs_ button) and wait until the index/update operation finishes (this could take a while).

(Source: http://www.sonatype.com/books/m2eclipse-book/reference/eclipse-sect-repo-view.html)