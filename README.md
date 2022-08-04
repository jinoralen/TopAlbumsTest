# TopAlbumsTest

Test application which displays top 100 albums from [Apple's RSS](https://rss.applemarketingtools.com/.
)


## Architecture

I followed Clean Architecture guidelines so there app has next layers:
 - View/UI layer with ComposeUI code
 - ViewModel layer which is responsible for providing UI states to View layer
 - UseCase layer for writing business logic where we combine data from repositories into data that is consumed by ViewModel
 - Domain/Data layer with all repositories and model classes that are used by repositories

I tried to group project by feature so in further project could be split into multiple modules(for now app is relativele small so there is no need to do this).

### Domain Layer
Domain layer consist of `AlbumsRepository`(responsible for executing network requests via Retrofit) and `AlbumsCacheRepository`(contains all methods for work with cache and implemented using Room). Also it contains several model classes that are required for parsing JSON with response and for exposing data provided by repositories

### UseCase Layer
Contains 2 use cases:
 - `GetTopAlbumsUseCase` which converts data provided by `AlbumsRepository` and `AlbumsCacheRepository` into `PagingData`. Using `PagingData` allows to avoid loading all albums at once which could be useful in further in case of implementing infinite scroll
 - `GetAlbumDetailsUseCase` - this use case just gets data with album details from cache since this case is used for showing details and we navigate from list screen where we already parsed and stored all requred data, so there is no need to perform additional network requests;

### ViewModel Layer
 -`AlbumListViewModel` - just caches results of `GetTopAlbumsUseCase`. There is no need to handle states here because everything important is stored inside `PagingData` 
 -`AlbumDetailsViewModel` - handles results of `GetAlbumDetailsUseCase` and emits state which is consumed by UI

 ### UI Layer

 - `TopAlbumsApp` - root of application adn contains navigation logic beetween screens used in application
 - `AlbumListScreen` - consumes `PagingData` from ViewModel and converts it into `LazyVerticalGrid` with `AlbumCard` that represent each album
 - `AlbumDetailsScreen` - consumes `AlbumDetailsState` and shows more detailed information about selected album. Also allows to visit album's web page by clicking on `Visit The Album` button


## Additional features
- Implemented collapsable title in `AlbumListScreen`, so on scroll title becomes smaller
- Implemented pull to refresh to force reload of list of albums
- Used Insets API to implement drawing of artwork below status bar in `AlbumDetailsScreen`. Unfortunately Apple didn't provide additional information to use for selecting color text in status bar, so it's possible that if artwork will be white status bar won't be visible
- Cache/Offline mode is implemented using Paging library which allows to avoid loading all posts at once, but in this case where backend API doesn't provide support for paging this isn't very useful. Also I used Room instead of Realm as cache because I don't have alot of experiense with it and also Room provides implementaton of `PagingSource` and for Realm it should be implemented from scratch. Also I'm not sure that Realm supports selecting only required data from DB (e.g. `Album` which contains all information about album parsed from JSON vs `AlbumInfo` that selects only columns that are required for displaying in `AlbumListScreen`)
- Added scrolling of content between artwork and button in `AlbumDetailsScreen` in case if text doesn't fit on screen