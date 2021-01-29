Appetiser Coding Challenge
=================

General
-------
A list show the following details from the iTunes API: 
•	Track Name
•	Artwork (there are multiple size formats, you should pick one that you consider appropriate for the task) 
•	Price
•	Genre
In addition, the detail view shows the Long Description and Preview (if available) for the given item

Each row has a placeholder image and are never duplicated when scrolling. 

Persistence
-----------
The app has the ability to save data fetched from the iTunes API and the User Activity logs using Android Jetpack Room. [Tracks, UserActivity]

UserActivity:
•	Stores the timestamp and track id (if any) of the last visited page, shown in page headers.
•	When the app restores, the previous screen will be restored. 

Architecture
------------
MVVM using Android Jetpack components. This project's architecture is very much based on the Google Android Sunflower app.

UI and design
-------------
Almost bare and basic with only minimal custom views and animations

*NOTE min sdk is Android M
