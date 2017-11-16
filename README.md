# Android Assignemnt

First of all, when the app is opened by the user, it starts fetching all the four url's at the same time. Thus start times will be shown at the beginning.
When any of the request finsihes, its respective end time is updated on the UI and the fetched data from that url is saved to the sqlite database of the app.
Then the save times are updated. Now user has the option to click on any of the four buttons to start fetching the corresponding url and saving the result in database.
I am using AsyncTask to do all the data fetching on the backgound. Initially, four async tasks are started at the same time for fetching the four urls. Then after that, as many times user clicks on the buttons, that many async tasks will get executed on the backgoround for performing their respective jobs.
Unix time stamp is shown at the bottom and is made to update continuously using a Handler.
I have kept the font size of the textviews (which are showing the timestamps) to be small so that all the timestamps of the four url's can be viewed clearly and separately by the user.