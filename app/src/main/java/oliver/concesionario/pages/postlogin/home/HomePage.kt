package oliver.concesionario.pages.postlogin.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import oliver.concesionario.R
import oliver.concesionario.viewmodels.postlogin.homeviewmodel.HomeViewModel
import oliver.concesionario.model.Car

@Composable
fun HomeScreen(homeViewModel: HomeViewModel,
                navToProfile: () -> Unit,
                navToDetailCar: (Car) -> Unit)
{
    val search by homeViewModel.Search.observeAsState(initial = "")
    val carList by homeViewModel.CarList.observeAsState(initial = mutableListOf())
    Scaffold(containerColor = Color(0xffE2E3E3),
            modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth())
        {


            LazyColumn(modifier = Modifier.padding(15.dp)) {
                item {
                    TitleRow(navToProfile)
                }

                item {
                    SearchField(search,
                        onSearchChange = { newStr ->
                            homeViewModel.OnSeachValueChange(newStr)
                    })
                }

                item {
                    Spacer(Modifier.padding(5.dp))
                }

                itemsIndexed(carList){index, car ->
                    if (index%2== 0){
                        LeftCard(car, navToDetailCar)
                    } else {
                        RightCard(car, navToDetailCar)
                    }
                }
            }
        }
    }
}


// Title Row
@Composable
private fun TitleRow(navToProfile: () -> Unit){
    Row(modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
       )
    {
        Text(text = "CATALOG",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)
        IconButton (onClick = { navToProfile() })
        {
            Icon(painter = painterResource(R.drawable.account_circle_24px),
                contentDescription = "",
                tint = Color.Black)
        }
    }
}

// Search a car Field
@Composable
private fun SearchField(search: String,
                onSearchChange: (String) -> Unit){
    TextField(
        value = search,
        onValueChange = { onSearchChange(it) },
        leadingIcon = {
            Icon(painter = painterResource(R.drawable.search_24px),
                tint = Color.Gray,
                contentDescription = "")
            },
        placeholder = {Text(text = "Search for cars...")},
        shape = RoundedCornerShape(25),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            disabledContainerColor = Color.White,

            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .border(0.dp,
                Color.Transparent,
                shape = RoundedCornerShape(25))
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(25))

        )
}
// Left car Card
@Composable
private fun LeftCard(car: Car,
             navToDetailCar :(Car) -> Unit
){
    Card(modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 15.dp)
            .clickable { navToDetailCar(car) },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        )
    {
        Row(modifier = Modifier.fillMaxSize())
        {
            // Left text
            Column(
                modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp),
                verticalArrangement = Arrangement.Center)
            {
                Text(
                    text = car.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = car.type,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${car.price}$",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.LightGray
                )
            }

            // Right Image
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFF1F1F1))
            )
            {
                // painter car
                val painter = if (car.imageStr.length > 1){
                    rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data(car.imageStr)
                            .build()
                    )
                } else {
                    painterResource(id = car.image)
                }

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

// RighCard
@Composable
private fun RightCard(car: Car,
             navToDetailCar :(Car) -> Unit
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .padding(horizontal = 15.dp)
        .clickable { navToDetailCar(car) },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    )
    {
        Row(modifier = Modifier.fillMaxSize())
        {
            // Left Image
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFF1F1F1))
            )
            {
                // painter car
                val painter = if (car.imageStr.length > 1){
                    rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data(car.imageStr)
                            .build()
                    )
                } else {
                    painterResource(id = car.image)
                }

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            // Right text
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center)
            {

                Text(
                    text = car.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Text(
                    text = car.type,
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${car.price}$",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}
