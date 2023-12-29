# notfallchecklisten
#### Video Demo: https://youtu.be/sZKLujcRWXQ
#### Description:
This is a simple yet very useful app for me and my colleagues. I work at a university hospital in germany as a doctor. I also work as one of many emergency physicians in our municipalty of Tübingen. We have a on-call emergency doctor service, where we get called for certain difficult or special pre-hospital emergencies. 
I know from my expirience, that it is very important to use checklists, especially in emergency cases with changing teams (we never know who the paramedics are, we will work with in any case). 
I wanted to build an easy to use and easy to maintain app for having certain checklists always with me on my phone.
The most important goals were:
- easy to use!
- easy maintainance, even for non techy colleagues (for example, you can add and change checklists only by changing the corresponding database table with a GUI-Tool of my web-hosting-service like phpmyadmin)
- offline availiability (can not be just a web-app. there needs to be an offline "container" which is getting the most recent web-app-site version once a week or so)
- simple design, no bugs!

First I created this prototype web app:
- it contains two prototype checklists which are extracted out of a sql file. If I want to add another checklist, I simply add another table with those elements.
- you can find the prototype website (which works prefectly fine on mibile devices) here "http://f28-preview.awardspace.net/checklistenannatue.eu.org/index.php"
- you can find the webapp in the "/webapp" folder of this repository. Keep in mind, that there is no sql server in this repository for serving the database to the php

I then realized having scripts for the creation of the webpage I need the app to cache all the contents and paths you could use. I thought maybe its easier to code a simple app in android studio with the same functionality. Also because I never worked with Android Studio and Kotlin before, this was a very nice possibility to learn something about coding android apps.
So in this App I use a JSON file as Checklist "Database" and read the contents of it in a list I can then use to let the app build the contents dynamically.
The functionality is the same as with the web-app.

The Notfallchecklisten.JSON is a self written prototype with just two checklists for testing. I will discuss the most important checklists and its contents with my colleagues and simply add/change those in my JSON file to update the App. And thats the elegant thing about this app design. I don't need to change anything in the code to add or change a checklist.

The Main Activity then just checks if there is a checklist, that was been klicked/selected or not and shows it. If no checklist is selected it shows the checklist titles and a button to open them. It was a pretty non intuistic way of implementing a navigation for me but it works. I did need a lot of googling and research until I got everything right.
Also I imported a lot of librarys i might use, and deleted the ones I didn't use at the end. Android Studio helps with that and this was very useful. Also if I found a solution for a certain problem or implementation I just copied the code from those websites and the IDE helps with suggesting imports for certain unknown classes or functions. That came very handy writing this.

I hope its a clean enough code and you don't mind I did two projects/implementations for the same simple concept.

In future versions I plan to implement following features:
- Having the JSON file stored in the phones file system for exchange or implementing a sever for updating the file with checklists
- An iOs version of the app
- A checklist editor with login/security (I actually already wrote some code for the login)
- Having a second site per checklist with schematics, flow-charts and so on
  
