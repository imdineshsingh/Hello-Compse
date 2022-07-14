package com.example.firstlayoutinjp.ui.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.HorizontalAnchorable
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.firstlayoutinjp.R
import com.example.firstlayoutinjp.model.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlin.random.Random


@Composable
fun HomeScreen() {
    val openDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {

        LoginScreen(openDialog = openDialog)
        ShowDialog2(openDialog = openDialog)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun MusicScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        val music = remember { DataProvider.musicList }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                items = music,
                itemContent = {
                    MusicListItem(music = it)
                })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MusicScreenPreview() {
    MusicScreen()
}

@Composable
fun MoviesScreen(viewModel: MainViewModel?) {
    val loading = remember { mutableStateOf(true) }
    val isRefreshing = remember { mutableStateOf(false) }
    val dataList = remember { mutableStateListOf<Movie>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        if (loading.value)
            CircularProgressIndicator()
        viewModel?.movieListResponse?.let {
            loading.value = false
            dataList.addAll(it)
            isRefreshing.value = false

        }
        viewModel?.let {
            MovieList(movieList = dataList, viewModel, isRefreshing)
        }
        viewModel?.getMovieList()
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesScreenPreview() {
    MoviesScreen(null)
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun BooksScreen() {

    Column(
        modifier = Modifier

            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.TopCenter)
    ) {
            HotelImageContent()
            tabsWithSwiping()


    }
}


@Preview(showBackground = true)
@Composable
fun BooksScreenPreview() {
    BooksScreen()

}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        FlowerCard()


    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}


@Composable
fun ShowDialog2(openDialog: MutableState<Boolean>) {
    val dialogWidth = 200.dp
    val dialogHeight = 150.dp

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {

            Column(
                Modifier
                    .size(dialogWidth, dialogHeight)
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "Compose Dialog", fontSize = 17.sp)

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "This is dialog description,", fontSize = 13.sp)

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = {
                    openDialog.value = false
                }) {

                    Text(text = "Ok")

                }

            }
        }
    }
}

@Composable
fun MovieList(
    movieList: List<Movie>,
    viewModel: MainViewModel,
    isRefreshing: MutableState<Boolean>
) {
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
        onRefresh = {
            isRefreshing.value = !isRefreshing.value
            viewModel?.getMovieList()
        }) {
        LazyColumn {
            itemsIndexed(items = movieList) { index, item ->
                MovieItem(movie = item)
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(110.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp
    ) {
        Surface() {

            Row(
                Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {

                Image(
                    painter = rememberImagePainter(
                        data = movie.imageUrl,

                        builder = {
                            scale(coil.size.Scale.FILL)
                            placeholder(R.drawable.placeholder)
                            transformations(CircleCropTransformation())

                        }
                    ),
                    contentDescription = movie.desc,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                )


                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f)
                ) {
                    Text(
                        text = movie.name,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = movie.category,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .background(
                                Color.LightGray
                            )
                            .padding(4.dp)
                    )
                    Text(
                        text = movie.desc,
                        style = MaterialTheme.typography.body1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }
        }
    }

}

@Composable
fun MusicListItem(music: Music) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row {
            MusicImage(music)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = music.title, style = MaterialTheme.typography.h6)
                Text(text = "VIEW DETAIL", style = MaterialTheme.typography.caption)
            }
        }
    }
}

@Composable
private fun MusicImage(music: Music) {
    Image(
        painter = painterResource(id = music.musicImageId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}


@Composable
fun ImageCard(image: ImageCollection) {
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .height(90.dp)
            .width(90.dp),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(15.dp))

    ) {
        Row(
            modifier = Modifier.padding(
                0
                    .dp
            )
        ) {

            Image(
                painter = painterResource(image.ImageId), contentDescription = "Profile Image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .size(110.dp)

            )


        }
    }
}

@Composable
fun HotelImageContent() {

    Image(painterResource(R.drawable.hotelcomplex), contentDescription = "")
    val images = remember { ImageItems.HotelImageList }
    LazyRow(
        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 5.dp)
    ) {
        items(
            images
        ) {
            ImageCard(image = it)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopCenter)
    ) {
        val (backArrow, image, text) = createRefs()

        Box() {

        }
    }
}

@ExperimentalPagerApi
@Preview
@Composable
fun tabsWithSwiping() {
    var tabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Photo", "Video", "360Video", "Complex", "Views")
    val pagerState = rememberPagerState()


    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()

    ) {
        TabRow(selectedTabIndex = tabIndex,

            indicator = { tabPositions ->
                TabRowDefaults.Indicator(

                    modifier = Modifier
                        .pagerTabIndicatorOffset(
                            pagerState,
                            tabPositions
                        )

                )
            }, backgroundColor = Color.Transparent
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(selected = tabIndex == index,
                    modifier = Modifier.padding(start = 1.dp, end = 1.dp),
                    onClick = { tabIndex = index },
                    text = { Text(text = title) })
            }
        }
        HorizontalPager(
            count = tabTitles.size,
            state = pagerState,
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color.White)
                    .wrapContentSize(Alignment.TopCenter)
                    .fillMaxSize()
            ) {
                GivenInformation()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun GivenInformation() {
    val data = listOf("☕️", "🙂", "🥛", "🎉")



        Row(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
        ) {
            Text(
                "Pavillion Complex",
                maxLines = 1,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .wrapContentSize(Alignment.TopStart)
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
            )
            Spacer(modifier = Modifier.width(70.dp))
            Text(
                "Type9",
                style = TextStyle(
                    color = Color.Black, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .absolutePadding(
                        top = 10.dp,
                        bottom = 10.dp,
                        left = 10.dp,
                        right = 10.dp
                    )
                    .wrapContentSize(Alignment.TopEnd)
                    .fillMaxWidth(2f)
                    .background(color = Color.Yellow)
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                .background(color = Color.White)
                .wrapContentSize(Alignment.TopStart)
        ) {
            val annotatedString = buildAnnotatedString {
                appendInlineContent(id = "imageId")
                append("Noida Sector 15 ")
            }
            val inlineContentMap = mapOf(
                "imageId" to InlineTextContent(
                    Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextTop)
                ) {
                    Image(
                        imageVector = Icons.Default.LocationOn,
                        modifier = Modifier.background(color = Color.White),
                        contentDescription = ""
                    )
                }
            )

            Text(annotatedString, inlineContent = inlineContentMap)

        }
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(start = 10.dp, end = 10.dp, top = 2.dp)
                .wrapContentSize(Alignment.TopStart)
        ) {
            val annotatedString = buildAnnotatedString {
                appendInlineContent(id = "imageId")
                append("D9,D block Sector 15 ")
            }
            val inlineContentMap = mapOf(
                "imageId" to InlineTextContent(
                    Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextTop)
                ) {
                    Image(
                        imageVector = Icons.Default.Home,
                        modifier = Modifier.background(color = Color.White),
                        contentDescription = ""
                    )
                }
            )

            Text(annotatedString, inlineContent = inlineContentMap)

        }
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(top = 10.dp, start = 10.dp, end = 5.dp, bottom = 8.dp)
                .wrapContentSize(Alignment.TopStart)
        ) {
            Text(
                "Over 900,000 Hotels Worldwide. Book Now, Pay When You Stay. Register Online. Sign Up For Deals. Become An Affiliate. List Your Property. Highlights: Safety Resource Center Available, Make Changes Online To Your Booking, Customer Service Available",
                color = Color.Black,
                fontSize = 14.sp, modifier = Modifier.wrapContentSize(Alignment.TopStart)
            )
        }
        Row(
            modifier = Modifier
                .background(color = Color.White)
                .wrapContentSize(Alignment.TopCenter)
        ) {
            Text(
                "Feature",
                maxLines = 1,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .wrapContentSize(Alignment.TopStart)
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
            )
            Spacer(modifier = Modifier.width(70.dp))
            Text(
                "Schedule Guide",
                style = TextStyle(
                    color = Color.Black, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .absolutePadding(
                        top = 10.dp,
                        bottom = 10.dp,
                        left = 10.dp,
                        right = 10.dp
                    )
                    .wrapContentSize(Alignment.TopEnd)
                    .fillMaxWidth()
                    .background(color = Color.Yellow)
            )
        }

        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(data) { item ->
                Card(
                    modifier = Modifier.padding(4.dp),
                    backgroundColor = Color(
                        red = Random.nextInt(0, 255),
                        green = Random.nextInt(0, 255),
                        blue = Random.nextInt(0, 255)
                    )
                ) {
                    Text(
                        text = item,
                        fontSize = 42.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }
        }
    }
@Preview
@Composable
fun FlowerCard() {
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .padding(10.dp)
            .width(180.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(25.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.rose),
                contentDescription = null,
                modifier = Modifier.size(140.dp),
            )
            Row(modifier = Modifier.padding(10.dp)) {


                Column(modifier = Modifier.background(color = Color.LightGray)){

                    Text(
                        text = "Rose", style = TextStyle(color = Color.Black, fontSize = 16.sp, fontStyle = FontStyle.Italic),
                        modifier = Modifier.padding(top = 5.dp, start = 5.dp, end = 5.dp)
                    )
                    Text(
                        text = "$40", style = TextStyle(color = Color.Blue, fontSize = 16.sp),
                        modifier = Modifier.padding(top = 1.dp, start = 5.dp, end = 5.dp)
                    )

                }
                IconButton(
                    onClick = {},
                    modifier = Modifier.background(
                        color = Color.Green,
                        shape = RoundedCornerShape(10.dp)
                    )

                ) {
                    Icon(Icons.Default.Add, tint = Color.Black, contentDescription = null)
                }
            }
        }
    }
}




