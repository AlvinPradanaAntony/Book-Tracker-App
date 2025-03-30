# Code Submission

Welcome to the Book Tracker App project!

## Getting Started

To pass the certification exam, you must complete all the tasks below.

1. Define a local database table and DAO (Data Access Object) based on the schema in `app/schemas/BooksSchema.json` and display the app’s initial data using the provided data from `books.json` file in the `raw` folder.
2. Get and display the user's currently read books using `CurrentlyReadBookView(CustomView)` in the Home Screen. (See: Home Screen specification)
    - Implement navigation to the Detail Book Screen when an item is clicked.
3. Attach `ViewPager` with `BookStatusAdapter` in the Status Book Screen.
    - Get the books' data. By default, sort the data by `newest bookAddedInMillis`.
    - Implement navigation to the Detail Book Screen when an item is clicked.
4. Initiate `RecyclerView` with `BookAdapter` in the List Book Screen.
    - Get the books' data sorted by `newest bookAddedInMillis`.
    - Display `tv_empty_list` when there are no books in the list.
    - Implement navigation to the Detail Book Screen when an item is clicked.
    - Implement delete book when an item in the list is swiped.
5. Create `AddBookActivity` layout (`activity_add_book.xml`) to add new book using `action_insert` menu. (See: Add Book Screen specification)
    - Apply the following logic for the book insertion:
        - Require the user to fill in the title, genre, total page, and author fields.
        - Set the book's reading progress to `0` when its status is “Want to Read”.
        - Set the book's reading progress to the book's total number of pages when its status is “Finished Reading”.
        - Show a Toast message using the string resource `invalid_reading_progress_input_message` if the user inputs a reading progress value that is greater than the total page.
6. Show the appropriate Book's data on `DetailBookActivity`.
    - On the Update button click listener:
        - Set the book's reading progress to `0` when its status is "Want to Read" and to the total number of pages when its status is "Finished Reading".
        - Show a Toast message using the string resource `invalid_reading_progress_input_message` if the user inputs a reading progress value that is greater than the total page.
        - Clear the book's personal note when the book's reading status is not set to "Finished Reading".
    - Implement delete book functionality for the `action_delete` menu.
7. Display a daily reminder notification that shows user's currently read books for every 09:00 AM using `AlarmManager`. (See: Notification specification)
    - Schedule and cancel the notification according to the notification preference's value in the App’s Settings.
    - Notification feature must be able to show up when the app runs on Android OS 13 (API 33) and above.
    - When the notification is clicked, direct the user to the Home Screen.
8. Update the dark mode theme based on the value in `ListPreference`.
9. Address the following comment from the QA team:
    - The Sort menu does not show in `ListBookActivity`.
    - `SnackBar` is not showing, hence making the user unable to undo the deleted item in `ListBookActivity`.
10. Write a UI test to validate that when users click the `action_list` menu on the Home Screen, the `fab_addBook` on `ListBookActivity` is displayed.

## Things to note

Your submission must match the required criteria to be accepted by our Proctors.  
Any improvisation, enhancement, or addition of new features that are not asked for is not needed here.  
Moreover, below are some items that you will need to avoid:

- Didn't use `QueryUtil.sortedBookQuery` to create a sortable query.
- Hardcoding the Book Status Type value.
- The displayed `CurrentlyReadBookView` doesn't display the newest data correctly when data is updated.
- The displayed `CurrentlyReadBookView` doesn't display `tv_empty_currently_read_books` when there are no currently read books.
- The displayed `ViewPager` data on `StatusBookActivity` doesn't display the newest data correctly when data is updated.
- Book List in List Book Screen can’t sort displayed data correctly according to its type.
- The `tv_empty_list` is still displayed after the user adds a new book in the List Book Screen.
- Clicking undo on the `SnackBar` does not restore deleted items.
- Doesn’t name the `AddBookActivity` layout to `activity_add_book.xml`.
- The notification feature doesn’t show up when the app runs on Android OS 13 (API 33) and above.
- The displayed Notification did not follow the Notification specification.
  Clicked Notification can’t direct the user to the Home Screen.
- Reminder notification only shows up once, not repeating.
- The reminder was not set for 09:00 AM.
- The notification feature shows up when the notification setting is disabled.
- App theme doesn’t match theme preference when re-opened.
- Have a ZIP file in the ZIP file you submitted (multiple layers of zip files).
- Force-closed application.
- The project cannot be built.
- Submit files other than the Android Studio project.
- Submitting projects that are not your own work.
- Remove or modify any code in the starter project/base code that is not required by the task (e.g., renaming the existing file/variable).

## Prepare your submission for this exam as below

- Use Android Studio.
- We recommend using the dev.cert Program Certification plugin for a seamless experience. Installation instructions are available on the platform.
- If you are using the certification plugin for Android Studio, simply click the **Submit Project** button in the **DevCert Project Assistant Tool** under the **Overview Tab** section.
- If you are not using the plugin or encounter issues with the plugin’s upload process, you can follow the manual upload steps below:
    -  In Android Studio, go to **File** → **Manage IDE Settings** → **Export to ZIP File...**
    -  Choose a storage directory and click **OK**. This method ensures the ZIP file size is optimized compared to manually compressing it via a file explorer.
    -  On the DevCert Platform, you can either drag and drop the exported ZIP file into the upload area or click to select the file manually.
