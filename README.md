### Application for viewing news from different sources using Jetpack Compose

Loads news using the API https://newsapi.org/.

### Screens

1. All articles screen.
   Contains a list of all news cards and how to filter them.
   Has a search bar and filter function to specify the search.

2. Article screen.
   Contains information about the news and a link to the source.

### Technical highlights

- Uses ViewModels for State Management
- Stores env variables in the `config.properties` file
- Uses custom errors for Error Handling
- Dark and light theme, saved between sessions using SharedPreferences
