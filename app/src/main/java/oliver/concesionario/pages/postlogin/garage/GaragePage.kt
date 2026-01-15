package oliver.concesionario.pages.postlogin.garage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oliver.concesionario.model.Car

private lateinit var carSelected: Car
// [Start, GarageScreen, Set public method GarageScreen, is a global screen]
@Composable
fun GarageScreen(listCars: List<Car> = listOf(),
                 navToDetailCar: (Car) -> Unit = {}){
    if (listCars.isNotEmpty()) {
        carSelected = listCars[0]
        CompleteScreen(listCars, navToDetailCar)
    } else {
        EmptyScreen()
    }
}
// [End, GarageScreen]

// [Start, CompleteScreen, When there are cars in the list this is shown]
@Composable
private fun CompleteScreen(listCars: List<Car>, navToDetailCar: (Car) -> Unit){
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // Image
            CarImage(listCars[0].image)
            // Contents
            ContentCars(listCars, navToDetailCar)
        }
    }
}
// [End, CompletedScreen]

// [Start, EmptyScreen, When there aren't cars in the garage this is shown]
@Composable
private fun EmptyScreen(){
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "No cars in Garage",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold)
    }
}
// [End, EmptyScreen]

// [Start, CarImage, Car image is selected is Shown, by default is position 0 from the list, but if user clic to
// other car to see Info when he back the image would be the last car selected
@Composable
private fun CarImage(image: Int){
    Image(painter = painterResource(image),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentScale = ContentScale.Crop)
}
// [End, CarImage]

// [Start, Content, ListCar scrollable]
@Composable
private fun ContentCars(listCars: List<Car>, navToDetailCar: (Car) -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Spacer
        Spacer(Modifier.padding(180.dp))

        // Content
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = Color.White
        ){

            Column(modifier = Modifier.padding(16.dp)) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp, horizontal = 150.dp),
                    thickness = 3.dp,
                    color = Color.Gray)

                listCars.forEach { car ->
                    CardCar(car = car, navToDetailCar = navToDetailCar)
                    Spacer(Modifier.padding(5.dp))
                }
            }
        }
    }
}
// [End, Content]

// [Start, CardCar, (Image, name, price)]
@Composable
private fun CardCar(car: Car, navToDetailCar: (Car) -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .padding(horizontal = 15.dp)
        .clickable {
            carSelected = car
            navToDetailCar(car) },
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
                Image(
                    painter = painterResource(id = car.image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            // Right texto
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
// [End, CardCar]