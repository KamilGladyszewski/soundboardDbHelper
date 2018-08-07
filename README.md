# soundboardDbHelper

*Work In Progress*

Spring Boot, MySQL and YouTube API project I created to help manage new sound requests for a soundboard app I co-created.
Users can submit new sounds through their browser, which than sends the data to the server via Ajax and PHP.

When the data is on the server, the Spring Boot Application is used to retrieve it. It than checks each video's channelId to exclude any entities that weren't posted by the expected channel. The data is than presented as a RESTful API and retrieved by a ReactJS app which sorts the sounds by videos with urls pointing to the exact moment of the video a user has specified.

To do:
Front end using React - removing videos from database, choosing a custom Youtube channel
