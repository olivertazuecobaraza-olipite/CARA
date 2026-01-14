package oliver.concesionario.pages.postlogin.info

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import oliver.concesionario.R
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oliver.concesionario.model.Car

@Composable
fun InfoCarScreen(car: Car,
                  goBack: () -> Unit){
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AddGarageButton {goBack()}
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            // Image
            CarImage(car.image)

            Image(painter = painterResource(R.drawable.ic_back_icon),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        goBack()
                    }
                    .padding(15.dp))

            // Content
            InfoContent(car)
        }
    }
}

@Composable
fun CarImage(image: Int){
    Image(painter = painterResource(image),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentScale = ContentScale.Crop)
}

@Composable
fun InfoContent(car: Car){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    )
    {
        Spacer(modifier = Modifier.padding(180.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = Color.White

        ) {
            Column(modifier = Modifier.padding(24.dp)) {

                NameTypeText(car)

                // Line spacer

                HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp),
                    thickness = 0.5.dp,
                    color = Color.LightGray.copy(alpha = 0.5f))

                StatsCar(car)

                // Line spacer

                HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp),
                    thickness = 0.5.dp,
                    color = Color.LightGray.copy(alpha = 0.5f))

                // Color Selector
                CarColorSelector()

                // Line spacer

                HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp),
                    thickness = 0.5.dp,
                    color = Color.LightGray.copy(alpha = 0.5f))

                // Description car
                DescriptionCar(car)

            }
        }
    }
}

@Composable
fun NameTypeText(car:Car){
    // Title
    Text(
        text = car.name,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
    Text(
        text = car.type,
        color = Color.Gray
    )
}

@Composable
fun StatsCar(car: Car){
    // Km stats
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.ic_battery),
                contentDescription = null,
                tint = Color.Gray)
            Text(text = "${car.km} KMs")
        }

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.ic_timer),
                contentDescription = null,
                tint = Color.Gray)
            Text(text = "${car.someSpecs} ")
        }

    }
}

@Composable
fun CarColorSelector() {
    // 1. Definimos el estado para saber qué índice está seleccionado (por defecto el 0)
    var selectedIndex by remember { mutableStateOf(0) }

    // Lista de tus recursos de imagen
    val carImages = listOf(
        R.drawable.car_white,
        R.drawable.car_black,
        R.drawable.car_red,
        R.drawable.car_green // Añadido según tus peticiones
    )

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            carImages.forEachIndexed { index, imageRes ->
                CarCircle(
                    imageRes = imageRes,
                    isSelected = selectedIndex == index,
                    onClick = { selectedIndex = index },
                    selectedColor = Color.Black
                )
            }
        }
    }
}

@Composable
fun CarCircle(
    imageRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit, // 3. Añadimos un callback para el clic
    selectedColor: Color = Color.Black
) {
    Box(
        modifier = Modifier
            .size(70.dp)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) selectedColor else Color.LightGray.copy(alpha = 0.4f),
                shape = CircleShape
            )
            // 4. Hacemos que el componente reaccione al toque
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(4.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,

            modifier = Modifier.size(50.dp).clip(CircleShape),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun DescriptionCar(car: Car){
    Text(
        text = car.description,
    )
}

@Composable
fun AddGarageButton(addGarage:() -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White),
    ) {
        Button(
            onClick = { addGarage()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xffb9d9f4))
        ) {
            Text("ADD GARAGE",
                color = Color.DarkGray,
                fontSize = 22.sp)
        }
    }
}

